/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: Apr 29, 2002
 * Time: 11:12:05 PM
 */
package com.xuan.workflow.basic;

import com.xuan.workflow.AbstractWorkflow;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1.1.1 $
 */
public class BasicWorkflow extends AbstractWorkflow {
    //~ Constructors ///////////////////////////////////////////////////////////

    public BasicWorkflow(String caller) {
        super.context = new BasicWorkflowContext(caller);
    }
}
