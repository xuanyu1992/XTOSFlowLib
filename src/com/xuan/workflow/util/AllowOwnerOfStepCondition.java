/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.module.propertyset.PropertySet;
import com.xuan.workflow.Condition;
import com.xuan.workflow.WorkflowContext;
import com.xuan.workflow.WorkflowException;
import com.xuan.workflow.spi.Step;
import com.xuan.workflow.spi.WorkflowEntry;
import com.xuan.workflow.spi.WorkflowStore;


/**
 * Checks owner of "stepId" in args and compares to current user
 *
 * @author Travis reeder
 * Date: Feb 18, 2003
 * Time: 4:47:00 PM
 * @version 0.1
 */
public class AllowOwnerOfStepCondition implements Condition {
    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean passesCondition(Map transientVars, Map args, PropertySet ps) throws WorkflowException {
        String stepIdString = (String) args.get("stepId");
        WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
        WorkflowContext context = (WorkflowContext) transientVars.get("context");

        if (stepIdString == null) {
            throw new WorkflowException("This condition expects a stepId!");
        }

        int stepId = 0;

        if (stepIdString != null) {
            try {
                stepId = Integer.parseInt(stepIdString);
            } catch (Exception ex) {
                throw new WorkflowException("This condition expects a stepId > 0!");
            }
        }

        WorkflowStore store = (WorkflowStore) transientVars.get("store");
        List historySteps = store.findHistorySteps(entry.getId());

        for (Iterator iterator = historySteps.iterator(); iterator.hasNext();) {
            Step step = (Step) iterator.next();

            if (stepId == (step.getStepId())) {
                // check to see if owner same as caller
                if (((step.getOwner() != null) && (context.getCaller() != null)) && context.getCaller().equals(step.getOwner())) {
                    return true;
                }
            }
        }

        return false;
    }
}
