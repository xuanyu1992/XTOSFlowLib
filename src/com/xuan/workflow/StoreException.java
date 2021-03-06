/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow;


/**
 * @author Hani Suleiman (hani@formicary.net)
 * Date: May 10, 2003
 * Time: 11:29:45 AM
 */
public class StoreException extends WorkflowException {
    //~ Constructors ///////////////////////////////////////////////////////////

    public StoreException(String s) {
        super(s);
    }

    public StoreException(String s, Exception ex) {
        super(s, ex);
    }

    public StoreException(Exception ex) {
        super(ex);
    }
}
