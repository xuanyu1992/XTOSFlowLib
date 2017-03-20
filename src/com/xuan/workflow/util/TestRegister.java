/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: May 13, 2002
 * Time: 12:22:56 PM
 */
package com.xuan.workflow.util;

import java.util.Map;

import com.xuan.workflow.Register;
import com.xuan.workflow.WorkflowContext;
import com.xuan.workflow.spi.WorkflowEntry;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1.1.1 $
 */
public class TestRegister implements Register {
    //~ Methods ////////////////////////////////////////////////////////////////

    public Object registerVariable(WorkflowContext context, WorkflowEntry entry, Map args) {
        return "****************************************";
    }
}
