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

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.homepage.lovelicense.client.ClientFactory;
import com.google.gwt.homepage.lovelicense.client.event.ActionEvent;
import com.google.gwt.homepage.lovelicense.client.event.ActionNames;
import com.google.gwt.homepage.lovelicense.client.event.GuestBookDetailEditEvent;
import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookViewEditPlace;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;




import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Makes a TaskReadView display a task.
 */
public class GuestBookDetailPresenter implements GuestBookDetailView.Presenter {

  private final ClientFactory clientFactory;

  private static final Logger log = Logger.getLogger(GuestBookDetailPresenter.class.getName());
  /**
   * A boolean indicating whether or not this activity is still active. The user
   * might move to another activity while this one is loading, in which case we
   * do not want to do any more work.
   */
  private boolean isDead = false;

  /**
   * The current task being displayed, might not be possible to edit it.
   */
  private GuestBookTableProxy task;

  /**
   * The ID of the current task being edited.
   */
  private final String taskId;
  private EventBus eventBus;

  /**
   * Construct a new {@link TaskReadPresenter}.
   * 
   * @param clientFactory the {@link ClientFactory} of shared resources
   * @param place configuration for this activity
   */
  public GuestBookDetailPresenter(ClientFactory clientFactory, TaskGuestBookViewEditPlace place) {
    this.taskId = place.getTaskId();
   // this.task = place.getTask();
    this.clientFactory = clientFactory;
    clientFactory.getGuestBookDetailView().setPresenter(this);
  }

  @Override
  public Widget asWidget() {
    return getView().asWidget();
  }

  @Override
  public void editGuestBookDetail() {
    eventBus.fireEvent(new GuestBookDetailEditEvent(task));
  }
  
  @Override
  public void cancelGuestBook() {
	  ActionEvent.fire(eventBus, ActionNames.GUESTBOOK_LIST);
  }
  
  
  
  public void deleteGuestBook(){
	    if (task == null) {
	        return;
	      }

	      // Delete the task in the data store.
	      final GuestBookTableProxy toDelete = this.task;
	      System.out.println("delete"+toDelete.getId());
	      clientFactory.getRequestFactory().
	      guestBookTableRequest().
	      remove().
	      using(toDelete).
	      fire(
	          new Receiver<Void>() {
	            @Override
	            public void onFailure(ServerFailure error) {
	              Window.alert("An error occurred on the server while deleting this task: \"."
	                  + error.getMessage() + "\".");
	            }

	            @Override
	            public void onSuccess(Void response) {
	              onGuestBookDeleted();
	            }
	          });
  }

  @Override
  public String mayStop() {
    return null;
  }

  public void start(EventBus newEventBus) {
    this.eventBus = newEventBus;

    // Hide the 'add' button in the shell.
    // TODO(rjrjr) Ick!
    //clientFactory.getShell().setAddButtonVisible(false);

    // Try to load the task from local storage.
    //if (task == null) {
      //task = clientFactory.getTaskProxyLocalStorage().getTask(taskId);
    //}
    
 //   if (task == null) {
      // Load the existing task.
      clientFactory.getRequestFactory().guestBookTableRequest().findGuestBookTable(this.taskId).fire(
          new Receiver<GuestBookTableProxy>() {
            @Override
            public void onSuccess(GuestBookTableProxy response) {
              // Early exit if this activity has already been cancelled.
             // if (isDead) {
            	//  System.out.println("#######isdead");
                //return;
              //}

              // Task not found.
              if (response == null) {
                Window.alert("방명록 id '" + taskId + "'가 존재하지 않습니다."
                    + " 다른 글을 선택해주세요.");
                ActionEvent.fire(eventBus, ActionNames.GUESTBOOK_EDITING_CANCELED);
                return;
              }

            
              // Show the task.
              task = response;
              
           // Use the task that was passed with the place.
          	//본인 글이면 수정 버튼 활성화
              if(response.getIsSelf()){
            	  getView().setModifyBtn();
              }
              
              if(response.getAnswer()==null || response.getAnswer().getEmail()==null  || response.getAnswer().getEmail().equals("")) {
              	getView().setAnswerVisible(false);
              }else{
              	getView().setAnswerVisible(true);
              }
              
              getView().getEditorDriver().edit(response);
              
              
              //조회수 +1
              updateCnt(taskId);
              
              
              
            }
          });
  
  }

  @Override
  public void stop() {
    eventBus = null;
    // Ignore all incoming responses to the requests from this activity.
    getView().init();
    isDead = true;
  }

  private GuestBookDetailView getView() {
    return clientFactory.getGuestBookDetailView();
  }
  
  private void onGuestBookDeleted() {
	    // Notify the user that the task was deleted.
	    notify("GuestBook Deleted");

	    // Return to the task list.
	    ActionEvent.fire(eventBus, ActionNames.GUESTBOOK_DELETED);
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
  
  
  //조회수 +1
  private void updateCnt(String id){
  
      clientFactory.getRequestFactory().guestBookTableRequest().updateCnt(id).fire(
    	        new Receiver<Void>() {
    	          @Override
    	          public void onFailure(ServerFailure error) {
    	            // ignore
    	          }

    	          @Override
    	          public void onSuccess(Void arg0) {
    	        	  ;
    	          }
    	       });
  }
}
