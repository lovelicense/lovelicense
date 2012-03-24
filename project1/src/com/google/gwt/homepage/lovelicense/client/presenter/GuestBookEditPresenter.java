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
//import com.google.gwt.sample.mobilewebapp.client.ui.SoundEffects;

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
public class GuestBookEditPresenter implements GuestBookEditView.Presenter {

  private static final Logger log = Logger.getLogger(GuestBookEditPresenter.class.getName());
  private final ClientFactory clientFactory;

  

  /**
   * The current task being edited, provided by RequestFactory.
   */
  private GuestBookTableProxy editTask;

  /**
   * The ID of the current task being edited.
   */
  private final String taskId;

  /**
   * The request used to persist the modified task.
   */
  private Request<Void> taskPersistRequest;
  private EventBus eventBus;

  

  /**
   * For editing an existing task.
   */
  public GuestBookEditPresenter(ClientFactory clientFactory, GuestBookTableProxy readOnlyTask) {
    /*
     * TODO surely we can find a way to show the read-only values while waiting
     * for the async fetch
     */
    this.taskId = readOnlyTask.getId();
    this.clientFactory = clientFactory;
    clientFactory.getGuestBookEditView().setPresenter(this);
  }

  @Override
  public Widget asWidget() {
    return getView().asWidget();
  }

 
  @Override
  public String mayStop() {
    if ((eventBus != null && editTask != null) && getView().getEditorDriver().isDirty()) {
      return "Are you sure you want to discard these changes?";
    }
    return null;
  }

  public void saveGuestBook() {
    // Flush the changes into the editable task.
    GuestBookTableRequest context = (GuestBookTableRequest) clientFactory.getGuestBookEditView().getEditorDriver().flush();

    /*
     * Create a persist request the first time we try to save this task. If a
     * request already exists, reuse it.
     */
    if (taskPersistRequest == null) {
      taskPersistRequest = context.persistEdit().using(editTask);
    }

    // Fire the request.
    taskPersistRequest.fire(new Receiver<Void>() {
      @Override
      public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
        handleConstraintViolations(violations);
      }

      @Override
      public void onSuccess(Void response) {
        editTask = null;

        // Notify the user that the task was updated.
        GuestBookEditPresenter.this.notify("Task Saved");
        
        // Return to the task list.
        ActionEvent.fire(eventBus, ActionNames.GUESTBOOK_SAVED);
      }
    });
  }

  public void cancelEdit(){
	  doCancelTask();
  }
  
  public void start(EventBus eventBus) {
    this.eventBus = eventBus;
    //getView().setNameViolation(null);

    // Prefetch the sounds used in this activity.
    //SoundEffects.get().prefetchError();

    // Hide the 'add' button in the shell.
    // TODO(rjrjr) Ick!
   
    System.out.println("#########edit2"); 
    
      startEdit();
   
  }

  @Override
  public void stop() {
    eventBus = null;
    clientFactory.getGuestBookEditView().setLocked(false);
  }

  /**
   * Cancel the current task.
   */
  private void doCancelTask() {
    ActionEvent.fire(eventBus, ActionNames.GUESTBOOK_EDITING_CANCELED);
  }



  private GuestBookEditView getView() {
    return clientFactory.getGuestBookEditView();
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

 


  private void startEdit() {

   
    // Lock the display until the task is loaded.
    getView().setLocked(true);
    clientFactory.getRequestFactory().guestBookTableRequest().findGuestBookTable(this.taskId).fire(
        new Receiver<GuestBookTableProxy>() {
          @Override
          public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
            getView().setLocked(false);
            getView().getEditorDriver().setConstraintViolations(violations);
          }

          @Override
          public void onFailure(ServerFailure error) {
            getView().setLocked(false);
            doCancelTask();
            super.onFailure(error);
          }

          @Override
          public void onSuccess(GuestBookTableProxy response) {
            // Early exit if we have already stopped.
            if (eventBus == null) {
              return;
            }

            // Task not found.
            if (response == null) {
              Window.alert("The task with id '" + taskId + "' could not be found."
                  + " Please select a different task from the task list.");
              doCancelTask();
              return;
            }

            // Show the task.
            editTask = response;
System.out.println("#########edit"+editTask.getSubject()+response.getSubject());            
            getView().getEditorDriver().edit(response,
                clientFactory.getRequestFactory().guestBookTableRequest());
            getView().setLocked(false);
          }
        });
  }
}
