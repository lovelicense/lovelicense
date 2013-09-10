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
import com.google.gwt.homepage.lovelicense.client.event.ShowTaskEvent;
import com.google.gwt.homepage.lovelicense.client.event.WriteGuestBookEvent;
import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookListPlace;
//import com.google.gwt.homepage.lovelicense.client.event.TaskListUpdateEvent;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import java.util.Collections;
import java.util.List;

/**
 * Activity that presents a list of tasks.
 */
public class GuestBookListPresenter implements GuestBookListView.Presenter {

  /**
   * The delay in milliseconds between calls to refresh the task list.
   */
  private static final int REFRESH_DELAY = 50000;//50초

  /**
   * A boolean indicating that we should clear the task list when started.
   */
  private final boolean clearTaskList;

  private final ClientFactory clientFactory;

  private EventBus eventBus;
  
  /**
   * The refresh timer used to periodically refresh the task list.
   */
  private Timer refreshTimer;

  public GuestBookListPresenter(ClientFactory clientFactory, boolean clearTaskList) {
    this.clientFactory = clientFactory;
    this.clearTaskList = clearTaskList;
    clientFactory.getGuestBookListView().setPresenter(this);
  }
  
  public GuestBookListPresenter(ClientFactory clientFactory, TaskGuestBookListPlace place) {
	    this(clientFactory, place.isTaskListStale());
	  }


 

  @Override
  public Widget asWidget() {
    return getView().asWidget();
  }

  @Override
  public String mayStop() {
    return null; // always happy to stop
  }

  public void selectTask(GuestBookTableProxy selected) {
    // Go into edit mode when a task is selected.
    eventBus.fireEvent(new ShowTaskEvent(selected));
  }

  public void writeGuestBook(){
	eventBus.fireEvent(new WriteGuestBookEvent()); 
  }
  
  @Override
  public void start(EventBus eventBus) {
    this.eventBus = eventBus;
    // Add a handler to the 'add' button in the shell.
    //clientFactory.getShell().setAddButtonVisible(true);

    // Clear the task list and display it.
    if (clearTaskList) {
      getView().clearList();
    }

    // Create a timer to periodically refresh the task list.
    refreshTimer = new Timer() {
      @Override
      public void run() {
        refreshTaskList();
      }
    };

    // Load the saved task list from storage
    //List<GuestBookTableProxy> list = clientFactory.getTaskProxyLocalStorage().getTasks();
    List<GuestBookTableProxy> list = null;
    //setTasks(list);

    // Request the task list now.
    refreshTaskList();
  }

  @Override
  public void stop() {
    eventBus = null;

    // Kill the refresh timer.
    if (refreshTimer != null) {
      refreshTimer.cancel();
    }
  }

  private GuestBookListView getView() {
    return clientFactory.getGuestBookListView();
  }

  /**
   * Refresh the task list.
   */
  private void refreshTaskList() {
    clientFactory.getRequestFactory().guestBookTableRequest().findAllGuestBooks().with("answer").fire(
        new Receiver<List<GuestBookTableProxy>>() {
          @Override
          public void onFailure(ServerFailure error) {
            // ignore
          }

          @Override
          public void onSuccess(List<GuestBookTableProxy> response) {
            // Early exit if this activity has already been canceled.
            if (eventBus == null) {
              return;
            }
//System.out.println("GuestBookListSize"+response.size()+response.get(1).getAnswer().getContents());
            // Display the tasks in the view.
            if (response == null) {
              response = Collections.<GuestBookTableProxy> emptyList();
            }
            
            setTasks(response);
            
            //for(int x=0;x<response.size();x++)
            	//System.out.println("result"+response.get(x).getSubject());

            // save the response to storage
           // clientFactory.getTaskProxyLocalStorage().setTasks(response);

            // Restart the timer.
            refreshTimer.schedule(REFRESH_DELAY);
          }
       });
  }

  /**
   * Set the list of tasks.
   */
  private void setTasks(List<GuestBookTableProxy> tasks) {
    getView().setGuestBook(tasks);
    //eventBus.fireEventFromSource(new TaskListUpdateEvent(tasks), this);
  }
  
  
}
