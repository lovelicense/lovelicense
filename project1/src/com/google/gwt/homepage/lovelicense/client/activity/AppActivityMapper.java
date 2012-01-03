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
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.homepage.lovelicense.client.ClientFactory;
import com.google.gwt.homepage.lovelicense.client.place.TaskCurrentSajuPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskMainPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskMyInfoPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskSajuViewPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskSearchFriendPlace;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * A mapping of places to activities used by this application.
 */
public class AppActivityMapper implements ActivityMapper {

  private final ClientFactory clientFactory;

  public AppActivityMapper(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
  }

  public Activity getActivity(final Place place) {
    /*if (place instanceof TaskMainPlace) {
      // The list of tasks.
      return new AbstractActivity() {
        @Override
        public void start(AcceptsOneWidget panel, EventBus eventBus) {
          TaskListPresenter presenter = new TaskListPresenter(clientFactory, (TaskListPlace) place);
          presenter.start(eventBus);
          panel.setWidget(presenter);
        }

        
         // Note no call to presenter.stop(). The TaskListViews do that
        // * themselves as a side effect of setPresenter.
         
      };
    }*/
	  
    if (place instanceof TaskMainPlace) {//메인페이지
      // Editable view of a task.
    	
      return new MainActivity(clientFactory);
    }
    
    if (place instanceof TaskMyInfoPlace) {//내정보입력
    	
       return new MyInfoActivity(clientFactory);
      }
    
    if (place instanceof TaskSajuViewPlace) {//사주보기
    	
        return new SajuViewActivity(clientFactory);
       }
    if (place instanceof TaskCurrentSajuPlace) {//현재사주보기
    	
        return new CurrentSajuActivity(clientFactory);
       }
    
     if (place instanceof TaskSearchFriendPlace) {//친구찾기
    	
        return new SearchFriendActivity(clientFactory);
       }
    

    return null;
  }
}
