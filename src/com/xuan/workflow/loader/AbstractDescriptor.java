/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.loader;

import java.io.Serializable;

import com.xuan.workflow.util.XMLizable;


/**
 * User: hani
 * Date: May 28, 2003
 * Time: 12:44:54 AM
 */
public abstract class AbstractDescriptor implements XMLizable, Serializable {
    //~ Instance fields ////////////////////////////////////////////////////////

    private AbstractDescriptor parent;
    private boolean hasId = false;
    private int id;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setId(int id) {
        this.id = id;
        hasId = true;
    }

    public int getId() {
        return id;
    }

    public void setParent(AbstractDescriptor parent) {
        this.parent = parent;
    }

    public AbstractDescriptor getParent() {
        return parent;
    }

    public boolean hasId() {
        return hasId;
    }
}
