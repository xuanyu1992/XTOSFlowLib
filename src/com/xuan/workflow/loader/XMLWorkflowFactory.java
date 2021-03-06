/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.loader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.xuan.workflow.FactoryException;
import com.xuan.workflow.InvalidWorkflowDescriptorException;


/**
 * @author Hani Suleiman
 * Date: May 10, 2002
 * Time: 11:30:41 AM
 */
public class XMLWorkflowFactory extends AbstractWorkflowFactory {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(XMLWorkflowFactory.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected Map workflows;
    protected boolean reload;

    //~ Methods ////////////////////////////////////////////////////////////////

    public WorkflowDescriptor getWorkflow(String name) throws FactoryException {
        WorkflowConfig c = (WorkflowConfig) workflows.get(name);

        if (c == null) {
            throw new FactoryException("Unknown workflow name \"" + name + "\"");
        }

        if (log.isDebugEnabled()) {
            log.debug("getWorkflow " + name + " descriptor=" + c.descriptor);
        }

        if (c.descriptor != null) {
            if (reload) {
                File file = new File(c.url.getFile());

                if (file.exists() && (file.lastModified() > c.lastModified)) {
                    c.lastModified = file.lastModified();
                    log.debug("Reloading workflow " + name);
                    loadWorkflow(c);
                }
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Loading workflow " + name);
            }

            loadWorkflow(c);
        }

        return c.descriptor;
    }

    public String[] getWorkflowNames() {
        int i = 0;
        String[] res = new String[workflows.keySet().size()];
        Iterator it = workflows.keySet().iterator();

        while (it.hasNext()) {
            res[i++] = (String) it.next();
        }

        return res;
    }

    public void initDone() throws FactoryException {
        reload = getProperties().getProperty("reload", "false").equals("true");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = null;
        String name = getProperties().getProperty("resource", "workflows.xml");

        if ((name != null) && (name.indexOf(":/") > -1)) {
            try {
                is = new URL(name).openStream();
            } catch (Exception e) {
            }
        }

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream(name);
            } catch (Exception e) {
            }
        }

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream("/" + name);
            } catch (Exception e) {
            }
        }

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream("META-INF/" + name);
            } catch (Exception e) {
            }
        }

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream("/META-INF/" + name);
            } catch (Exception e) {
            }
        }

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);

            DocumentBuilder db = null;

            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                log.fatal("Error creating document builder", e);
            }

            Document doc = db.parse(is);

            Element root = (Element) doc.getElementsByTagName("workflows").item(0);
            workflows = new HashMap();

            NodeList list = root.getElementsByTagName("workflow");

            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                WorkflowConfig config = new WorkflowConfig(e.getAttribute("type"), e.getAttribute("location"));
                workflows.put(e.getAttribute("name"), config);
            }
        } catch (Exception e) {
            log.fatal("Error reading xml workflow", e);
            throw new InvalidWorkflowDescriptorException("Error in workflow config: " + e.getMessage());
        }
    }

    public boolean saveWorkflow(String name, WorkflowDescriptor descriptor, boolean replace) throws FactoryException {
        WorkflowConfig c = (WorkflowConfig) workflows.get(name);

        if ((c != null) && !replace) {
            return false;
        }

        if (c == null) {
            throw new UnsupportedOperationException("Saving of new workflow is not currently supported");
        }

        Writer out = null;

        try {
            out = new OutputStreamWriter(new FileOutputStream(c.url.getFile() + ".new"), "utf-8");
        } catch (FileNotFoundException ex) {
            throw new FactoryException("Could not create new file to save workflow " + c.url.getFile());
        } catch (UnsupportedEncodingException ex) {
            throw new FactoryException("utf-8 encoding not supported, contact your JVM vendor!");
        }

        writeXML(descriptor, out);

        //write it out to a new file, to ensure we don't end up with a messed up file if we're interrupted halfway for some reason
        //now lets rename
        File original = new File(c.url.getFile());
        File backup = new File(c.url.getFile() + ".bak");
        File updated = new File(c.url.getFile() + ".new");
        boolean isOK = !original.exists() || original.renameTo(backup);

        if (!isOK) {
            log.warn("Unable to backup original workflow file " + original + ", aborting save");

            return false;
        }

        isOK = updated.renameTo(original);

        if (!isOK) {
            log.warn("Unable to rename new file " + updated + " to " + original + ", aborting save");

            return false;
        }

        backup.delete();

        return true;
    }

    protected void writeXML(WorkflowDescriptor descriptor, Writer out) {
        PrintWriter writer = new PrintWriter(new BufferedWriter(out));
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        writer.println("<!DOCTYPE workflow PUBLIC \"-//OpenSymphony Group//DTD OSWorkflow 2.5//EN\" \"http://www.opensymphony.com/osworkflow/workflow_2_5.dtd\">");
        descriptor.writeXML(writer, 0);
        writer.flush();
        writer.close();
    }

    private void loadWorkflow(WorkflowConfig c) throws FactoryException {
        try {
            c.descriptor = WorkflowLoader.load(c.url);
        } catch (Exception e) {
            throw new FactoryException("Error in workflow descriptor: " + c.url, e);
        }
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class WorkflowConfig {
        String location;
        String type;
        URL url;
        WorkflowDescriptor descriptor;
        long lastModified;

        public WorkflowConfig(String type, String location) {
            if ("URL".equals(type)) {
                try {
                    url = new URL(location);

                    File file = new File(url.getFile());

                    if (file.exists()) {
                        lastModified = file.lastModified();
                    }
                } catch (Exception ex) {
                }
            } else if ("file".equals(type)) {
                try {
                    File file = new File(location);
                    url = file.toURL();
                    lastModified = file.lastModified();
                } catch (Exception ex) {
                }
            } else {
                url = Thread.currentThread().getContextClassLoader().getResource(location);
            }

            this.type = type;
            this.location = location;
        }
    }
}
