/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.util;

import com.xuan.workflow.WorkflowException;
import com.xuan.workflow.spi.WorkflowEntry;


/*
 * @author Hani Suleiman
 * @version $Revision: 1.1.1.1 $
 * Date: Apr 6, 2002
 * Time: 11:48:14 PM
 */
public interface WorkflowLocalListener {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void stateChanged(WorkflowEntry entry) throws WorkflowException;
}
