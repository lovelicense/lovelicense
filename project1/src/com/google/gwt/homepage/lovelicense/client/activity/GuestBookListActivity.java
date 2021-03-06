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
package com.google.gwt.homepage.lovelicense.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.homepage.lovelicense.client.ClientFactory;
import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookListPlace;
import com.google.gwt.homepage.lovelicense.client.presenter.MainPresenter;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookListPresenter;

import com.google.gwt.homepage.ui.client.PresentsWidgets;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.ResettableEventBus;

/**
 * An activity that shows details on a particular task, and allows the user to
 * edit it.
 */
public class GuestBookListActivity extends AbstractActivity {
  private PresentsWidgets presenter;


  private final ClientFactory clientFactory;

  private  TaskGuestBookListPlace place;
  
  //private ResettableEventBus childEventBus;

  /**
   * Construct a new {@link TaskActivity}.
   * 
   * @param clientFactory the {@link ClientFactory} of shared resources
   * @param place configuration for this activity
   */
  public GuestBookListActivity(ClientFactory clientFactory, TaskGuestBookListPlace place) {
  
    this.clientFactory = clientFactory;
    this.place=place;
  }

  @Override
  public String mayStop() {
    return presenter.mayStop();
  }

  @Override
  public void onCancel() {
    presenter.stop();
  }

  @Override
  public void onStop() {
    presenter.stop();
  }

  public void start(final AcceptsOneWidget container, com.google.gwt.event.shared.EventBus eventBus) {
	  
//System.out.println("00000000000000000000000");
	  presenter = new GuestBookListPresenter(clientFactory, place.isTaskListStale());
      presenter.start(eventBus);
      container.setWidget(presenter);
  }

}
