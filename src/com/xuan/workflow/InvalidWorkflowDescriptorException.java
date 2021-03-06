/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow;


/**
 * Indicates that a Workflow Descriptor was invalid.
 * Usually this indicates a semantically incorrect XML workflow definition.
 *
 * @author <a href="mailto:vorburger@users.sourceforge.net">Michael Vorburger</a>
 * @version $Revision: 1.1.1.1 $
 */
public class InvalidWorkflowDescriptorException extends FactoryException {
    //~ Constructors ///////////////////////////////////////////////////////////

    public InvalidWorkflowDescriptorException(String message) {
        super(message);
    }
}
