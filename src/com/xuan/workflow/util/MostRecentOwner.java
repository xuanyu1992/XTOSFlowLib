/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.util.TextUtils;
import com.xuan.workflow.FunctionProvider;
import com.xuan.workflow.WorkflowException;
import com.xuan.workflow.spi.Step;
import com.xuan.workflow.spi.WorkflowEntry;
import com.xuan.workflow.spi.WorkflowStore;


/**
 * Sets the persistent variable "mostRecentOwner" to the owner of the most
 * recent step that had an id equal to one of the values in the stepId list. If there is
 * none found, the variable is unset. This function accepts the following
 * arguments:
 *
 * <ul>
 *  <li>stepId - a comma-seperated list of the most recent steps to look for (required)</li>
 * </ul>
 *
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 * @author <a href="mailto:mischwar@cisco.com">Mike Schwartz</a>
 * @version $Revision: 1.1.1.1 $
 */
public class MostRecentOwner implements FunctionProvider {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void execute(Map transientVars, Map args, PropertySet ps) throws WorkflowException {
        // expects a stepId name/value pair
        String stepIdString = (String) args.get("stepId");
        WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");

        if (stepIdString == null) {
            throw new WorkflowException("This function expects a stepId!");
        }

        StringTokenizer st = new StringTokenizer(stepIdString, ",");
        List stepIds = new LinkedList();

        while (st.hasMoreTokens()) {
            stepIds.add(st.nextToken().trim());
        }

        WorkflowStore store = (WorkflowStore) transientVars.get("store");
        List historySteps = store.findHistorySteps(entry.getId());

        for (Iterator iterator = historySteps.iterator(); iterator.hasNext();) {
            Step step = (Step) iterator.next();

            if (stepIds.contains(String.valueOf(step.getStepId())) && TextUtils.stringSet(step.getOwner())) {
                transientVars.put("mostRecentOwner", step.getOwner());

                break;
            }
        }
    }
}
