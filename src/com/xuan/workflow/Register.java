/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow;

import java.util.Map;

import com.xuan.workflow.spi.WorkflowEntry;


/**
 * Interface that must be implemented for workflow registers to behave properly.
 *
 * @author <a href="mailto:plightbo@hotmail.com">Patrick Lightbody</a>
 * @version $Revision: 1.1.1.1 $
 */
public interface Register {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Returns the object to bind to the variable map for this workflow instance.
     *
     * @param context The current workflow context
     * @param entry The workflow entry. Note that this might be null, for example in a pre function
     * before the workflow has been initialised
     * @param args Map of arguments as set in the workflow descriptor

     * @return the object to bind to the variable map for this workflow instance
     */
    public Object registerVariable(WorkflowContext context, WorkflowEntry entry, Map args) throws WorkflowException;
}
