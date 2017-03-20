/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.util;

import java.util.Map;

import com.opensymphony.module.propertyset.PropertySet;
import com.xuan.workflow.FunctionProvider;
import com.xuan.workflow.WorkflowContext;


/**
 * Sets the persistent variable "caller" to the current user executing an action.
 *
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 * @version $Revision: 1.1.1.1 $
 */
public class Caller implements FunctionProvider {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void execute(Map transientVars, Map args, PropertySet ps) {
        WorkflowContext context = (WorkflowContext) transientVars.get("context");
        transientVars.put("caller", context.getCaller());
    }
}
