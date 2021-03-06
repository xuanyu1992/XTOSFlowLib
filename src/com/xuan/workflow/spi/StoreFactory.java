/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.spi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xuan.workflow.StoreException;
import com.xuan.workflow.WorkflowContext;
import com.xuan.workflow.config.ConfigLoader;


/**
 * Class that is used to intialize a workflow store and
 * save the single instance of it for future use.
 *
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 */
public class StoreFactory {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(StoreFactory.class);
    private static WorkflowStore store;

    //~ Methods ////////////////////////////////////////////////////////////////

    public static WorkflowStore getPersistence(WorkflowContext context) throws StoreException {
        if (store == null) {
            String clazz = ConfigLoader.persistence;

            log.info("Initializing WorkflowStore: " + clazz);

            try {
                store = (WorkflowStore) Class.forName(clazz).newInstance();
            } catch (Exception ex) {
                throw new StoreException("Error creating store", ex);
            }

            store.init(ConfigLoader.persistenceArgs);
        }

        return store;
    }
}
