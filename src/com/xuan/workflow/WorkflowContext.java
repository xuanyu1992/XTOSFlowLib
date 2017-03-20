/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow;


/**
 * Interface to be implemented if a new OSWorkflow interaction is to be created (SOAP, EJB, Ofbiz, etc).
 *
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 */
public interface WorkflowContext extends java.io.Serializable {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Returns the caller's String representation that is to be looked up using OSUser.
     *
     * @return the caller's String representation that is to be looked up using OSUser
     */
    public String getCaller();

    /**
     * Sets the transaction to be rolled back.
     */
    public void setRollbackOnly();
}
