/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.xuan.workflow.spi.Step;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1.1.1 $
 */
public class JoinNodes {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Collection steps;
    private DummyStep dummy = new DummyStep();

    //~ Constructors ///////////////////////////////////////////////////////////

    public JoinNodes(Collection steps) {
        this.steps = steps;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Step getStep(int stepId) {
        for (Iterator iterator = steps.iterator(); iterator.hasNext();) {
            Step step = (Step) iterator.next();

            if (step.getStepId() == stepId) {
                return step;
            }
        }

        // no match, not ready to join ...
        // ... so return a dummy step that is always false
        return dummy;
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    public class DummyStep implements Step {
        public int getActionId() {
            return -1;
        }

        public String getCaller() {
            return null;
        }

        public Date getDueDate() {
            return null;
        }

        public long getEntryId() {
            return -1;
        }

        public Date getFinishDate() {
            return null;
        }

        public long getId() {
            return -1;
        }

        public String getOwner() {
            return null;
        }

        public long[] getPreviousStepIds() {
            return new long[0];
        }

        public Date getStartDate() {
            return null;
        }

        public String getStatus() {
            return null;
        }

        public int getStepId() {
            return -1;
        }
    }
}
