/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.loader;

import java.io.PrintWriter;

import org.w3c.dom.Element;


/**
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 * @version $Revision: 1.1.1.1 $
 */
public class PermissionDescriptor extends AbstractDescriptor {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected RestrictionDescriptor restriction;
    protected String name;

    //~ Constructors ///////////////////////////////////////////////////////////

    public PermissionDescriptor() {
    }

    public PermissionDescriptor(Element permission) {
        init(permission);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRestriction(RestrictionDescriptor restriction) {
        this.restriction = restriction;
    }

    public RestrictionDescriptor getRestriction() {
        return restriction;
    }

    public void writeXML(PrintWriter out, int indent) {
        XMLUtil.printIndent(out, indent++);
        out.print("<permission ");

        if (hasId()) {
            out.print("id=\"" + getId() + "\" ");
        }

        out.println("name=\"" + name + "\">");
        restriction.writeXML(out, indent);
        XMLUtil.printIndent(out, --indent);
        out.println("</permission>");
    }

    protected void init(Element permission) {
        name = permission.getAttribute("name");

        try {
            setId(Integer.parseInt(permission.getAttribute("id")));
        } catch (NumberFormatException e) {
        }

        restriction = new RestrictionDescriptor(XMLUtil.getChildElement(permission, "restrict-to"));
    }
}
