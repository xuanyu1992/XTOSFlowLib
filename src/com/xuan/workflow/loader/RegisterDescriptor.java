/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.loader;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 * @version $Revision: 1.1.1.1 $
 */
public class RegisterDescriptor extends AbstractDescriptor {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Map args = new HashMap();
    protected String type;
    protected String variableName;

    //~ Constructors ///////////////////////////////////////////////////////////

    public RegisterDescriptor() {
    }

    public RegisterDescriptor(Element register) {
        init(register);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Map getArgs() {
        return args;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    public void writeXML(PrintWriter out, int indent) {
        XMLUtil.printIndent(out, indent++);
        out.println("<register " + (hasId() ? ("id=\"" + getId() + "\" ") : "") + "type=\"" + type + "\" variable-name=\"" + variableName + "\">");

        Iterator iter = args.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            XMLUtil.printIndent(out, indent);
            out.print("<arg name=\"");
            out.print(entry.getKey());
            out.print("\">");

            if ("beanshell".equals(type) || "bsf".equals(type)) {
                out.print("<![CDATA[");
                out.print(entry.getValue());
                out.print("]]>");
            } else {
                out.print(XMLUtil.encode(entry.getValue()));
            }

            out.println("</arg>");
        }

        XMLUtil.printIndent(out, --indent);
        out.println("</register>");
    }

    protected void init(Element register) {
        this.type = register.getAttribute("type");
        this.variableName = register.getAttribute("variable-name");

        try {
            setId(Integer.parseInt(register.getAttribute("id")));
        } catch (NumberFormatException e) {
        }

        NodeList args = register.getElementsByTagName("arg");

        for (int l = 0; l < args.getLength(); l++) {
            Element arg = (Element) args.item(l);
            this.args.put(arg.getAttribute("name"), arg.getChildNodes().item(0).getNodeValue());
        }
    }
}
