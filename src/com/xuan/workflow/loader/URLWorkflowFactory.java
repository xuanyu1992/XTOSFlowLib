/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.loader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xuan.workflow.FactoryException;


/**
 * @author Hani Suleiman
 * Date: May 10, 2002
 * Time: 11:59:47 AM
 */
public class URLWorkflowFactory extends AbstractWorkflowFactory {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(URLWorkflowFactory.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    private Map cache = new HashMap();

    //~ Methods ////////////////////////////////////////////////////////////////

    public WorkflowDescriptor getWorkflow(String name) throws FactoryException {
        boolean useCache = getProperties().getProperty("cache", "false").equals("true");

        if (useCache) {
            WorkflowDescriptor descriptor = (WorkflowDescriptor) cache.get(name);

            if (descriptor != null) {
                return descriptor;
            }
        }

        try {
            URL url = new URL(name);
            WorkflowDescriptor descriptor = WorkflowLoader.load(url);

            if (useCache) {
                cache.put(name, descriptor);
            }

            return descriptor;
        } catch (Exception e) {
            throw new FactoryException("Unable to find workflow " + name, e);
        }
    }

    public String[] getWorkflowNames() throws FactoryException {
        throw new FactoryException("URLWorkflowFactory does not contain a list of workflow names");
    }

    public boolean saveWorkflow(String name, WorkflowDescriptor descriptor, boolean replace) throws FactoryException {
        WorkflowDescriptor c = (WorkflowDescriptor) cache.get(name);
        URL url = null;

        try {
            url = new URL(name);
        } catch (MalformedURLException ex) {
            throw new FactoryException("workflow '" + name + "' is an invalid url:" + ex);
        }

        boolean useCache = getProperties().getProperty("cache", "false").equals("true");

        if (useCache && (c != null) && !replace) {
            return false;
        }

        if (new File(url.getFile()).exists() && !replace) {
            return false;
        }

        Writer out = null;

        try {
            out = new OutputStreamWriter(new FileOutputStream(url.getFile() + ".new"), "utf-8");
        } catch (FileNotFoundException ex) {
            throw new FactoryException("Could not create new file to save workflow " + url.getFile());
        } catch (UnsupportedEncodingException ex) {
            throw new FactoryException("utf-8 encoding not supported, contact your JVM vendor!");
        }

        //write it out to a new file, to ensure we don't end up with a messed up file if we're interrupted halfway for some reason
        PrintWriter writer = new PrintWriter(new BufferedWriter(out));
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        //lets not write the dtd out, since this won't work without our own resolver if behind a firewall or disconnected
        //writer.println("<!DOCTYPE workflow PUBLIC \"-//OpenSymphony Group//DTD OSWorkflow 2.5//EN\" \"http://www.opensymphony.com/osworkflow/workflow_2_5.dtd\">");
        descriptor.writeXML(writer, 0);
        writer.flush();
        writer.close();

        //now lets rename
        File original = new File(url.getFile());
        File backup = new File(url.getFile() + ".bak");
        File updated = new File(url.getFile() + ".new");
        boolean isOK = original.renameTo(backup);

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

        if (useCache) {
            cache.put(name, descriptor);
        }

        return true;
    }
}
