/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.loader;

import java.util.Collection;
import java.util.Iterator;

import com.xuan.workflow.InvalidWorkflowDescriptorException;
import com.xuan.workflow.util.Validatable;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1.1.1 $
 */
public class ValidationHelper {
    //~ Methods ////////////////////////////////////////////////////////////////

    public static void validate(Collection c) throws InvalidWorkflowDescriptorException {
        Iterator iter = c.iterator();

        while (iter.hasNext()) {
            Object o = iter.next();

            if (o instanceof Validatable) {
                ((Validatable) o).validate();
            }
        }
    }
}
