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
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import com.google.gwt.homepage.lovelicense.client.activity.AppPlaceHistoryMapper;
import com.google.gwt.homepage.lovelicense.client.event.ActionEvent;
import com.google.gwt.homepage.lovelicense.client.event.ActionNames;
import com.google.gwt.homepage.lovelicense.client.event.GuestBookDetailEditEvent;
import com.google.gwt.homepage.lovelicense.client.event.ShowTaskEvent;
import com.google.gwt.homepage.lovelicense.client.event.WriteGuestBookEvent;
import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookListPlace;

import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookEditPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookViewEditPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookListPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookWritePlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskMainPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskMyInfoPlace;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;

import com.google.gwt.homepage.gaerequest.client.ReloadOnAuthenticationFailure;
/*
import com.google.gwt.sample.mobilewebapp.client.activity.AppPlaceHistoryMapper;
import com.google.gwt.sample.mobilewebapp.client.event.ActionEvent;
import com.google.gwt.sample.mobilewebapp.client.event.ActionNames;
import com.google.gwt.sample.mobilewebapp.client.event.ShowTaskEvent;
import com.google.gwt.sample.mobilewebapp.presenter.task.TaskPlace;
import com.google.gwt.sample.mobilewebapp.presenter.tasklist.TaskListPlace;
import com.google.gwt.sample.mobilewebapp.shared.TaskProxy;
*/
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.UmbrellaException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The heart of the applicaiton, mainly concerned with bootstrapping.
 */
public class App {
  private static final String HISTORY_SAVE_KEY = "SAVEPLACE";

  private static final Logger log = Logger.getLogger(App.class.getName());

  private final Storage storage;

  /**
   * Where components of the app converse by posting and monitoring events.
   */
  private final EventBus eventBus;

  /**
   * Owns the current {@link Place} in the app. A Place is the embodiment of any
   * bookmarkable state.
   */
  private final PlaceController placeController;

  /**
   * The top of our UI.
   */
  private final LoveLicenseShell shell;

  private final ActivityManager activityManager;

  private final AppPlaceHistoryMapper historyMapper;

  private final PlaceHistoryHandler historyHandler;

  private final ReloadOnAuthenticationFailure reloadOnAuthenticationFailure;

  public App(Storage storage, EventBus eventBus, PlaceController placeController,
      ActivityManager activityManager, AppPlaceHistoryMapper historyMapper,
      PlaceHistoryHandler historyHandler,
      ReloadOnAuthenticationFailure reloadOnAuthenticationFailure, LoveLicenseShell shell) {

    this.storage = storage;
    this.eventBus = eventBus;
    this.placeController = placeController;
    this.activityManager = activityManager;
    this.historyMapper = historyMapper;
    this.historyHandler = historyHandler;
    this.reloadOnAuthenticationFailure = reloadOnAuthenticationFailure;
    this.shell = shell;
  }

  /**
   * Given a parent view to show itself in, start this App.
   * 
   * @param parentView where to show the app's widget
   */
  public void run(HasWidgets.ForIsWidget parentView) {
    activityManager.setDisplay(shell);

    parentView.add(shell);

    /*방명록 작성*/
    eventBus.addHandler(WriteGuestBookEvent.TYPE, new WriteGuestBookEvent.Handler() {
      @Override
      public void onWriteGuestBook(WriteGuestBookEvent event) {
        placeController.goTo(new TaskGuestBookWritePlace());
      }
    });
    
    /*방명록 수정*/
    eventBus.addHandler(GuestBookDetailEditEvent.TYPE, new GuestBookDetailEditEvent.Handler() {
      @Override
      public void onGuestBookDetailEdit(GuestBookDetailEditEvent event) {
        placeController.goTo(new TaskGuestBookEditPlace(event.getGuestBook().getId(),event.getGuestBook()));
      }
    });

    /*방명록 리스트에서 1개 클릭시 */
    eventBus.addHandler(ShowTaskEvent.TYPE, new ShowTaskEvent.Handler() {
      @Override
      public void onShowTask(ShowTaskEvent event) {
    	  GuestBookTableProxy task = event.getTask();
        placeController.goTo(TaskGuestBookViewEditPlace.createTaskEditPlace(task.getId(), task));
      }
    });

    //방명록 삭제
    ActionEvent.register(eventBus, ActionNames.GUESTBOOK_DELETED, new ActionEvent.Handler() {
      @Override
      public void onAction(ActionEvent event) {
        placeController.goTo( new TaskGuestBookListPlace(true));
      }
    });

    //방명록 등록 및 수정 후
    ActionEvent.register(eventBus, ActionNames.GUESTBOOK_SAVED, new ActionEvent.Handler() {
        @Override
        public void onAction(ActionEvent event) {
          placeController.goTo(new TaskGuestBookListPlace(true));
        }
      });
    
    //방명록 등록페이지에서 취소
    ActionEvent.register(eventBus, ActionNames.GUESTBOOK_WRITTING_CANCELED, new ActionEvent.Handler() {
        @Override
        public void onAction(ActionEvent event) {
          placeController.goTo(new TaskGuestBookListPlace(false));//refresh될께 없으므로
        }
      });
    
    //방명록 수정페이지에서 취소
    ActionEvent.register(eventBus, ActionNames.GUESTBOOK_EDITING_CANCELED, new ActionEvent.Handler() {
        @Override
        public void onAction(ActionEvent event) {
          placeController.goTo(new TaskGuestBookListPlace(false));//refresh될께 없으므로
        }
      });
    
    //방명록 세부페이지에서 목록
    ActionEvent.register(eventBus, ActionNames.GUESTBOOK_LIST, new ActionEvent.Handler() {
        @Override
        public void onAction(ActionEvent event) {
          placeController.goTo(new TaskGuestBookListPlace(false));//refresh될께 없으므로
        }
      });
    
    
    
    ActionEvent.register(eventBus, ActionNames.GO_HOME, new ActionEvent.Handler() {
        @Override
        public void onAction(ActionEvent event) {
          placeController.goTo(new TaskMainPlace());
        }
      });
    
    ActionEvent.register(eventBus, ActionNames.MYINFO_SAVED, new ActionEvent.Handler() {
        @Override
        public void onAction(ActionEvent event) {
          placeController.goTo(new TaskMainPlace());
        }
      });
    
    ActionEvent.register(eventBus, ActionNames.MYINFO_CANCELED, new ActionEvent.Handler() {
        @Override
        public void onAction(ActionEvent event) {
          placeController.goTo(new TaskMainPlace());
        }
      });
    
   
    
    
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable e) {
        while (e instanceof UmbrellaException) {
          e = ((UmbrellaException) e).getCauses().iterator().next();
        }

        String message = e.getMessage();
        if (message == null) {
          message = e.toString();
        }
        log.log(Level.SEVERE, "Uncaught exception", e);
        Window.alert("An unexpected error occurred: " + message);
      }
    });

    // Check for authentication failures or mismatches
    reloadOnAuthenticationFailure.register(eventBus);

    initBrowserHistory(historyMapper, historyHandler, new TaskMainPlace());
  }

  /**
   * Initialize browser history / bookmarking. If LocalStorage is available, use
   * it to make the user's default location in the app the last one seen.
   */
  private void initBrowserHistory(final AppPlaceHistoryMapper historyMapper,
      PlaceHistoryHandler historyHandler, Place defaultPlace) {

    Place savedPlace = null;
    if (storage != null) {
      try {
        // wrap in try-catch in case stored value is invalid
        savedPlace = historyMapper.getPlace(storage.getItem(HISTORY_SAVE_KEY));
      } catch (Throwable t) {
        // ignore error and use the default-default
      }
    }
    if (savedPlace == null) {
      savedPlace = defaultPlace;
    }
    historyHandler.register(placeController, eventBus, savedPlace);

    /*
     * Go to the place represented in the URL. This is what makes bookmarks
     * work.
     */
    historyHandler.handleCurrentHistory();

    /*
     * Monitor the eventbus for place changes and note them in LocalStorage for
     * the next launch.
     */
    /*if (storage != null) {
      eventBus.addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
        public void onPlaceChange(PlaceChangeEvent event) {
          storage.setItem(HISTORY_SAVE_KEY, historyMapper.getToken(event.getNewPlace()));
        }
      });
    }*/
  }
}
