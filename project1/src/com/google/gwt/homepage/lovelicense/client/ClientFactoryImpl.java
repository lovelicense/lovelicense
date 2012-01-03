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

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.homepage.gaerequest.client.GaeAuthRequestTransport;
import com.google.gwt.homepage.gaerequest.client.ReloadOnAuthenticationFailure;

import com.google.gwt.homepage.lovelicense.client.activity.AppActivityMapper;
import com.google.gwt.homepage.lovelicense.client.activity.AppPlaceHistoryMapper;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileMainView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileMyInfoView;

import com.google.gwt.homepage.lovelicense.client.mobile.LoveLicenseShellMobile;

import com.google.gwt.homepage.lovelicense.client.presenter.CurrentSajuView;
import com.google.gwt.homepage.lovelicense.client.presenter.MainView;
import com.google.gwt.homepage.lovelicense.client.presenter.MyInfoView;
import com.google.gwt.homepage.lovelicense.client.presenter.SajuViewView;
import com.google.gwt.homepage.lovelicense.client.presenter.SearchFriendView;

import com.google.gwt.homepage.lovelicense.shared.LoveLicenseRequestFactory;




import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.RequestTransport;

import com.google.gwt.homepage.ui.client.OrientationHelper;
import com.google.gwt.homepage.ui.client.WindowBasedOrientationHelper;

/**
 * Default implementation of {@link ClientFactory}. Used by desktop version.
 */
class ClientFactoryImpl implements ClientFactory {

  /**
   * The URL argument used to enable or disable local storage.
   */
  private static final String STORAGE_URL_ARG = "storage";

  private final OrientationHelper orientationHelper = new WindowBasedOrientationHelper();
  
  protected final EventBus eventBus = new SimpleEventBus();
  protected final PlaceController placeController = new PlaceController(eventBus);
  private final LoveLicenseRequestFactory requestFactory;
  private LoveLicenseShell shell;
  private final Storage localStorage;
  //private final TaskProxyLocalStorage taskProxyLocalStorage;
  private MainView mainView; 
  private MyInfoView myInfoView; 
  private SajuViewView sajuViewView; 
  private CurrentSajuView currentSajuView;
  private SearchFriendView searchFriendView;
  
  private ActivityManager activityManager;

  private final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);

  /**
   * The stock GWT class that ties the PlaceController to browser history,
   * configured by our custom {@link #historyMapper}.
   */
  private final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);


  

  

  public ClientFactoryImpl() {
    RequestTransport requestTransport = new GaeAuthRequestTransport(eventBus);
    requestFactory = GWT.create(LoveLicenseRequestFactory.class);
    requestFactory.initialize(eventBus, requestTransport);

    // Initialize local storage.
    
    String storageUrlValue = Window.Location.getParameter(STORAGE_URL_ARG);
    if (storageUrlValue == null || storageUrlValue.startsWith("t")) {
      localStorage = Storage.getLocalStorageIfSupported();
    } else {
      localStorage = null;
    }
    //taskProxyLocalStorage = new TaskProxyLocalStorage(localStorage);
   
  }

  public App getApp() {
    return new App(getLocalStorageIfSupported(), eventBus, getPlaceController(),getActivityManager(),  historyMapper, historyHandler, new ReloadOnAuthenticationFailure(), getShell());
  }

  @Override
  public EventBus getEventBus() {
    return eventBus;
  }
  
  public PlaceController getPlaceController() {
    return placeController;
  }

  public LoveLicenseRequestFactory getRequestFactory() {
    return requestFactory;
  }

  public LoveLicenseShell getShell() {
    if (shell == null) {
      shell = createShell();
    }
    return shell;
  }

  public MainView getMainView() {
    if (mainView == null) {
    	mainView = createMainView();
    }
    return mainView;
  }
  
  public MyInfoView getMyInfoView() {
    if (myInfoView == null) {
    	myInfoView = createMyInfoView();
    }
    return myInfoView;
  }

  
  public SajuViewView getSajuViewView() {
	    if (sajuViewView == null) {
	    	sajuViewView = createSajuViewView();
	    }
	    return sajuViewView;
	  }
//  public TaskProxyLocalStorage getTaskProxyLocalStorage() {
  //  return taskProxyLocalStorage;
 // }

  
  public CurrentSajuView getCurrentSajuView() {
	    if (currentSajuView == null) {
	    	currentSajuView = createCurrentSajuView();
	    }
	    return currentSajuView;
	  }

  
  public SearchFriendView getSearchFriendView() {
	    if (searchFriendView == null) {
	    	searchFriendView = createSearchFriendView();
	    }
	    return searchFriendView;
	  }

  /**
   * ActivityMapper determines an Activity to run for a particular place,
   * configures the {@link #getActivityManager()}
   */
  protected ActivityMapper createActivityMapper() {
    return new AppActivityMapper(this);
  }

  /**
   * Create the application UI shell.
   * 
   * @return the UI shell
   */
  protected LoveLicenseShell createShell() {
    return null;
	  //return new LoveLicenseShellMobile(eventBus, orientationHelper, 
      //  getMainView(), getMyInfoView());
  }

  /**
   * Create a {@link TaskEditView}.
   * 
   * @return a new {@link TaskEditView}
   */
  //
  protected MainView createMainView() {
	  return null;
   // return new MobileMainView(eventBus,placeController);
  }

  /**
   * Create a {@link TaskListView}.
   * 
   * @return a new {@link TaskListView}
   */
//
  protected MyInfoView createMyInfoView() {
      return null;
	  //return new MobileMyInfoView();
  }


  protected SajuViewView createSajuViewView() {
      return null;
	  //return new MobileMyInfoView();
  }
  
  protected CurrentSajuView createCurrentSajuView() {
      return null;
	  //return new MobileMyInfoView();
  }
  
  protected SearchFriendView createSearchFriendView() {
      return null;
	  //return new MobileMyInfoView();
  }
  
  
  
  
  

  /**
   * Owns a panel in the window, in this case the entire {@link #shell}.
   * Monitors the {@link #eventBus} for
   * {@link com.google.gwt.place.shared.PlaceChangeEvent PlaceChangeEvent}s posted by the
   * {@link #placeController}, and chooses what
   * {@link com.google.gwt.activity.shared.Activity Activity} gets to take
   * over the panel at the current place. Configured by the
   * {@link #createActivityMapper()}.
   */
  protected ActivityManager getActivityManager() {
    if (activityManager == null) {
      activityManager = new ActivityManager(createActivityMapper(), eventBus);
    }
    return activityManager;
  }

  private Storage getLocalStorageIfSupported() {
    return localStorage;
  }
}
