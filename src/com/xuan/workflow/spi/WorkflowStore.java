/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.xuan.workflow.spi;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.module.propertyset.PropertySet;
import com.xuan.workflow.StoreException;
import com.xuan.workflow.query.WorkflowQuery;


/**
 * Interface for pluggable workflow stores configured in osworkflow.xml.
 * Only one instance of a workflow store is ever created, meaning that
 * if your persistence connections (such as java.sql.Connection) time out,
 * it would be un-wise to use just one Connection for the entire object.
 *
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 */
public interface WorkflowStore {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Returns a PropertySet that is associated with this workflow instance ID.
     *
     * @param entryId the ID of the workflow instance
     * @return a property set unique to this entry ID
     */
    public PropertySet getPropertySet(long entryId) throws StoreException;

    /**
     * Persists a step with the given parameters.
     *
     * @param entryId the ID of the workflow instance
     * @param stepId the ID of the workflow step associated with this new
     *               Step (not to be confused with the step primary key)
     * @param owner the owner of the step
     * @param startDate the start date of the step
     * @param status the status of the step
     * @param previousIds the previous step IDs
     * @return a representation of the workflow step persisted
     */
    public Step createCurrentStep(long entryId, int stepId, String owner, Date startDate, Date dueDate, String status, long[] previousIds) throws StoreException;

    /**
     * Persists a new workflow entry that has <b>not been initialized</b>.
     *
     * @param workflowName the workflow name that this entry is an instance of
     * @return a representation of the workflow instance persisted
     */
    public WorkflowEntry createEntry(String workflowName) throws StoreException;

    /**
     * Returns a list of all current steps for the given workflow instance ID.
     *
     * @param entryId the ID of the workflow instance
     * @return a List of Steps
     * @see com.opensymphony.workflow.spi.Step
     */
    public List findCurrentSteps(long entryId) throws StoreException;

    /**
     * Pulls up the workflow entry data for the entry ID given.
     *
     * @param entryId the ID of the workflow instance
     * @return a representation of the workflow instance persisted
     */
    public WorkflowEntry findEntry(long entryId) throws StoreException;

    /**
     * Returns a list of all steps that are finished for the given workflow instance ID.
     *
     * @param entryId the ID of the workflow instance
     * @return a List of Steps
     * @see com.opensymphony.workflow.spi.Step
     */
    public List findHistorySteps(long entryId) throws StoreException;

    /**
     * Called once when the store is first created.
     *
     * @param props properties set in osworkflow.xml
     */
    public void init(Map props) throws StoreException;

    public Step markFinished(Step step, int actionId, Date finishDate, String status, String caller) throws StoreException;

    /**
     * Called when a step is finished and can be moved to workflow history.
     *
     * @param step the step to be moved to workflow history
     */
    public void moveToHistory(Step step) throws StoreException;

    public List query(WorkflowQuery query) throws StoreException;
}
