/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow;


/**
 * Indicates that the caller did not have enough permissions to perform some action.
 *
 * @author <a href="mailto:plightbo@hotmail.com">Patrick Lightbody</a>
 * @version $Revision: 1.1.1.1 $
 */
public class InvalidRoleException extends WorkflowException {
    //~ Constructors ///////////////////////////////////////////////////////////

    public InvalidRoleException(String message) {
        super(message);
    }
}
