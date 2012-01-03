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
import com.google.gwt.homepage.lovelicense.client.presenter.CurrentSajuPresenter;
import com.google.gwt.homepage.lovelicense.client.presenter.SearchFriendPresenter;


import com.google.gwt.homepage.ui.client.PresentsWidgets;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.ResettableEventBus;

/**
 * An activity that shows details on a particular task, and allows the user to
 * edit it.
 */
public class SearchFriendActivity extends AbstractActivity {
  private PresentsWidgets presenter;


  private final ClientFactory clientFactory;

  private ResettableEventBus childEventBus;

  /**
   * Construct a new {@link TaskActivity}.
   * 
   * @param clientFactory the {@link ClientFactory} of shared resources
   * @param place configuration for this activity
   */
  public SearchFriendActivity(ClientFactory clientFactory) {
  
    this.clientFactory = clientFactory;
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
	  

	  presenter = new SearchFriendPresenter(clientFactory);
      presenter.start(eventBus);
      container.setWidget(presenter);
  }

}
