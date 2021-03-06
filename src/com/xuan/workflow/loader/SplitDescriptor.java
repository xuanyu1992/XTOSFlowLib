/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.loader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.xuan.workflow.InvalidWorkflowDescriptorException;
import com.xuan.workflow.util.Validatable;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1.1.1 $
 */
public class SplitDescriptor extends AbstractDescriptor implements Validatable {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected List results = new ArrayList();

    //~ Constructors ///////////////////////////////////////////////////////////

    public SplitDescriptor() {
    }

    public SplitDescriptor(Element split) {
        init(split);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public List getResults() {
        return results;
    }

    public void validate() throws InvalidWorkflowDescriptorException {
        ValidationHelper.validate(results);
    }

    public void writeXML(PrintWriter out, int indent) {
        XMLUtil.printIndent(out, indent++);
        out.println("<split id=\"" + getId() + "\">");

        for (int i = 0; i < results.size(); i++) {
            ResultDescriptor result = (ResultDescriptor) results.get(i);
            result.writeXML(out, indent);
        }

        XMLUtil.printIndent(out, --indent);
        out.println("</split>");
    }

    private void init(Element split) {
        try {
            setId(Integer.parseInt(split.getAttribute("id")));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid split id value " + split.getAttribute("id"));
        }

        NodeList uResults = split.getElementsByTagName("unconditional-result");

        for (int i = 0; i < uResults.getLength(); i++) {
            Element result = (Element) uResults.item(i);
            results.add(new ResultDescriptor(result));
        }
    }
}
