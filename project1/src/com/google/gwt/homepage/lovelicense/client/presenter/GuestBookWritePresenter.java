/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.homepage.lovelicense.client.presenter;

import com.google.gwt.homepage.lovelicense.client.ClientFactory;
import com.google.gwt.homepage.lovelicense.client.event.ActionEvent;
import com.google.gwt.homepage.lovelicense.client.event.ActionNames;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableRequest;
//import com.google.gwt.homepage.lovelicense.client.ui.SoundEffects;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

/**
 * Drives a {@link TaskEditView} to fetch and edit a given task, or to create a
 * new one.
 */
public class GuestBookWritePresenter implements GuestBookWriteView.Presenter {

  private static final Logger log = Logger.getLogger(GuestBookWritePresenter.class.getName());
  private final ClientFactory clientFactory;


  /**
   * The current task being edited, provided by RequestFactory.
   */
  private GuestBookTableProxy createTask;

  

  /**
   * The request used to persist the modified task.
   */
  private Request<Void> taskPersistRequest;
  private EventBus eventBus;

  /**
   * For creating a new task.
   */
  public GuestBookWritePresenter(ClientFactory clientFactory) {
    //this.taskId = null;
    this.clientFactory = clientFactory;
    clientFactory.getGuestBookWriteView().setPresenter(this);
  }

  

  @Override
  public Widget asWidget() {
    return getView().asWidget();
  }

  public void cancelWrite() {
   
      doCancelTask();
   
  }

  @Override
  public String mayStop() {
    
    return null;
  }

  public void saveGuestBook() {
    // Flush the changes into the editable task.
	  GuestBookTableRequest context = (GuestBookTableRequest) clientFactory.getGuestBookWriteView().getEditorDriver().flush();

    /*
     * Create a persist request the first time we try to save this task. If a
     * request already exists, reuse it.
     */
    if (taskPersistRequest == null) {
      taskPersistRequest = context.persistCreate().using(createTask);
    }

    // Fire the request.
    taskPersistRequest.fire(new Receiver<Void>() {
      @Override
      public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
        handleConstraintViolations(violations);
      }

      @Override
      public void onSuccess(Void response) {
    	  createTask = null;

        // Notify the user that the task was updated.
        GuestBookWritePresenter.this.notify("Task Saved");
        
        // Return to the task list.
        ActionEvent.fire(eventBus, ActionNames.GUESTBOOK_SAVED);
      }
    });
  }

  public void start(EventBus eventBus) {
    this.eventBus = eventBus;
   // getView().setNameViolation(null);

    // Prefetch the sounds used in this activity.
    //SoundEffects.get().prefetchError();

    // Hide the 'add' button in the shell.
    // TODO(rjrjr) Ick!
    //clientFactory.getShell().setAddButtonVisible(false);

    //if (taskId == null) {
      startCreate();
    //} else {
     // startEdit();
    //}
  }

  
  private void startCreate() {

	  
	  GuestBookTableRequest request = clientFactory.getRequestFactory().guestBookTableRequest();
    createTask = request.create(GuestBookTableProxy.class);
    getView().getEditorDriver().edit(createTask, request);
    System.out.println("#########create"); 
  }
  
  
  @Override
  public void stop() {
    eventBus = null;
    //clientFactory.getGuestBookWriteView().setLocked(false);
  }

  /**
   * Cancel the current task.
   */
  private void doCancelTask() {
    ActionEvent.fire(eventBus, ActionNames.GUESTBOOK_WRITTING_CANCELED);
  }

 

  private GuestBookWriteView getView() {
    return clientFactory.getGuestBookWriteView();
  }

  /**
   * Handle constraint violations.
   */
  private void handleConstraintViolations(Set<ConstraintViolation<?>> violations) {
    // Display the violations.
    getView().getEditorDriver().setConstraintViolations(violations);

    // Play a sound.
   // SoundEffects.get().playError();
  }

  /**
   * Notify the user of a message.
   * 
   * @param message the message to display
   */
  private void notify(String message) {
    // TODO Add notification pop-up
    log.fine("Tell the user: " + message);
  }

  

  
}
