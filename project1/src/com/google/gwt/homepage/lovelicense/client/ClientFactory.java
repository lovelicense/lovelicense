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
package com.google.gwt.homepage.lovelicense.client;

import com.google.gwt.place.shared.PlaceController;
//import com.google.gwt.sample.mobilewebapp.presenter.task.TaskEditView;
//import com.google.gwt.sample.mobilewebapp.presenter.task.TaskReadView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.homepage.lovelicense.client.presenter.CurrentSajuView;
import com.google.gwt.homepage.lovelicense.client.presenter.MainView;
import com.google.gwt.homepage.lovelicense.client.presenter.MyInfoView;
import com.google.gwt.homepage.lovelicense.client.presenter.SajuViewView;
import com.google.gwt.homepage.lovelicense.client.presenter.SearchFriendView;
import com.google.gwt.homepage.lovelicense.shared.LoveLicenseRequestFactory;
//import com.google.gwt.sample.mobilewebapp.shared.MobileWebAppRequestFactory;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The factory responsible for instantiating everything interesting in this
 * application. Typically this might be a <a href=
 * "http://google-gin.googlecode.com/svn/trunk/javadoc/com/google/gwt/inject/client/Ginjector.html"
 * >Ginjector</a> code generated by <a
 * href="http://code.google.com/p/google-gin/">GIN</a>.
 */
public interface ClientFactory {
  /**
   * Create the App.
   * 
   * @return a new instance of the {@link App}
   */
	
	//Create the constants
	  LoveLicenseConstants constants = (LoveLicenseConstants) GWT.create(LoveLicenseConstants.class);
	   
	
  App getApp();

  /**
   * Get the {@link EventBus}
   * 
   * @return the event bus used through the app
   */
  EventBus getEventBus();

  /**
   * Get the {@link PlaceController}.
   * 
   * @return the place controller
   */
  PlaceController getPlaceController();

  /**
   * Get the RequestFactory used to query the server.
   * 
   * @return the request factory
   */
  LoveLicenseRequestFactory getRequestFactory();

  /**
   * Get the UI shell.
   * 
   * @return the shell
   */
  LoveLicenseShell getShell();

  /**
   * Get an implementation of {@link TaskEditView}.
   */
 // TaskEditView getTaskEditView();

  /**
   * Get an implementation of {@link TaskListView}.
   */
  MainView getMainView();

  /**
   * Get the {@link TaskProxyLocalStorage} that stores tasks.
   */
  //TaskProxyLocalStorage getTaskProxyLocalStorage();

  /**
   * Get an implementation of {@link TaskEditView}.
   */
  MyInfoView getMyInfoView();
  
  
  SajuViewView getSajuViewView();
  
  CurrentSajuView getCurrentSajuView();
  
  SearchFriendView getSearchFriendView();
  
}
