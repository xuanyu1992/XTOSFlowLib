/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.util.TextUtils;
import com.xuan.workflow.Register;
import com.xuan.workflow.WorkflowContext;
import com.xuan.workflow.spi.WorkflowEntry;


/**
 * This is a register, which helps logging using commons-logging.
 * It wraps a Logger instance which is linked to the "OSWorkflow/<workflow_name>/<id>" category
 *
 * Following optional arguments available:
 * <ul>
 *  <li>addInstanceId=true/false - if the instance id of the workflow should be added to the category. Defaults to false</li>
 *  <li>Category="OSWorkflow" - change the name of the log category other than "OSWorkflow"</li>
 * </ul>
 *
 *
 * If you register this class as "Logger", then you may use it from a Beanshell script like:
 * <pre>
 * logger = variables.get("logger");
 * logger.debug("hello logger!");
 * </pre>
 *
 * @author Zoltan Luspai
 */
public class LogRegister implements Register {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * @see com.opensymphony.workflow.Register#registerVariable(WorkflowContext, WorkflowEntry, Map)
     */
    public Object registerVariable(WorkflowContext context, WorkflowEntry entry, Map args) {
        String workflowname = "unknown";
        long workflow_id = -1;

        if (entry != null) {
            workflowname = entry.getWorkflowName();
            workflow_id = entry.getId();
        }

        boolean groupByInstance = false;
        String useInstance = (String) args.get("addInstanceId");

        if (useInstance != null) {
            groupByInstance = TextUtils.parseBoolean(useInstance);
        }

        String categoryName = "OSWorkflow";

        if (args.get("Category") != null) {
            categoryName = (String) args.get("Category");
        }

        String category = categoryName + "." + workflowname;

        if (groupByInstance) {
            category += ("." + (Long.toString(workflow_id)));
        }

        Log log = LogFactory.getLog(category);

        return log;
    }
}
