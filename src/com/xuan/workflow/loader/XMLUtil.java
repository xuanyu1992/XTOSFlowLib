/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.loader;

import java.io.PrintWriter;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xuan.workflow.util.XMLizable;


/**
 *
 *
 * @author <a href="mailto:plightbo@.com">Pat Lightbody</a>
 */
public class XMLUtil {
    //~ Methods ////////////////////////////////////////////////////////////////

    public static Element getChildElement(Element parent, String childName) {
        NodeList children = parent.getChildNodes();
        int size = children.getLength();

        for (int i = 0; i < size; i++) {
            Node node = children.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (childName.equals(element.getNodeName())) {
                    return element;
                }
            }
        }

        return null;
    }

    public static String encode(Object string) {
        if (string == null) {
            return "";
        }

        char[] chars = string.toString().toCharArray();
        StringBuffer out = new StringBuffer();

        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
            case '&':
                out.append("&amp;");

                break;

            case '<':
                out.append("&lt;");

                break;

            case '>':
                out.append("&gt;");

                break;

            case '\"':
                out.append("&quot;");

                break;

            default:
                out.append(chars[i]);
            }
        }

        return out.toString();
    }

    public static void printIndent(PrintWriter out, int indent) {
        for (int i = 0; i < indent; i++) {
            out.print(XMLizable.INDENT);
        }
    }
}
