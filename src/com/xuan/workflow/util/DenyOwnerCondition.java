/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.util.TextUtils;
import com.xuan.workflow.Condition;
import com.xuan.workflow.StoreException;
import com.xuan.workflow.WorkflowContext;
import com.xuan.workflow.spi.Step;
import com.xuan.workflow.spi.WorkflowEntry;
import com.xuan.workflow.spi.WorkflowStore;


/**
 * Simple utility condition that returns false if the owner is the caller. Looks at
 * ALL current steps unless a stepId is given in the optional argument "stepId".
 *
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 */
public class DenyOwnerCondition implements Condition {
    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean passesCondition(Map transientVars, Map args, PropertySet ps) throws StoreException {
        int stepId = TextUtils.parseInt((String) args.get("stepId"));
        WorkflowContext context = (WorkflowContext) transientVars.get("context");
        WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
        WorkflowStore store = (WorkflowStore) transientVars.get("store");
        List currentSteps = store.findCurrentSteps(entry.getId());

        if (stepId == 0) {
            for (Iterator iterator = currentSteps.iterator();
                    iterator.hasNext();) {
                Step step = (Step) iterator.next();

                if (context.getCaller().equals(step.getOwner())) {
                    return false;
                }
            }
        } else {
            for (Iterator iterator = currentSteps.iterator();
                    iterator.hasNext();) {
                Step step = (Step) iterator.next();

                if (stepId == step.getStepId()) {
                    if (context.getCaller().equals(step.getOwner())) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }

        return true;
    }
}
