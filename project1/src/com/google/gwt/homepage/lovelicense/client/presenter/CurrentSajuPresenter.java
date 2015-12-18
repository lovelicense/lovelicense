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

import com.google.gwt.core.client.GWT;
import com.google.gwt.homepage.gaerequest.client.GaeAuthRequestTransport;
import com.google.gwt.homepage.lovelicense.client.ClientFactory;
import com.google.gwt.homepage.lovelicense.client.place.TaskMainPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskMyInfoPlace;
import com.google.gwt.homepage.lovelicense.shared.LoveLicenseRequestFactory;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableProxy;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableRequest;

import com.google.gwt.place.shared.Place;





import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;

import com.google.web.bindery.requestfactory.shared.ServerFailure;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



/**
 * Activity that presents a list of tasks.
 */
public class CurrentSajuPresenter implements CurrentSajuView.Presenter {

  
	 private static final Logger log = Logger.getLogger(CurrentSajuPresenter.class.getName());


  private final ClientFactory clientFactory;

  private EventBus eventBus;
  
  /**
   * The current task being edited, provided by RequestFactory.
   */
  private SajuDataTableProxy editTask;
  
  private Request<Void> taskPersistRequest;
  

  public CurrentSajuPresenter(ClientFactory clientFactory ) {

    this.clientFactory = clientFactory;
    clientFactory.getCurrentSajuView().setPresenter(this);
    getMyCurrentDetailSajuResult();
  }

  
  /**
   * Refresh the task list.
   */
  private void getMyCurrentDetailSajuResult() { 
	  //System.out.println("############################getSajuDataTable()");
	getView().setLocked(true);
	
    clientFactory.getRequestFactory().sajuDataTableRequest().getMyCurrentDetailSajuResult().fire(
            new Receiver<List<SajuDataTableProxy>>() {
              @Override
              public void onFailure(ServerFailure error) {
            	  getView().setLocked(false);
              }

              @Override
              public void onSuccess(List<SajuDataTableProxy> response) {
                // Early exit if this activity has already been canceled.
                if (eventBus == null) {
                  return;
                }

                
                // 내정보 입력으로 이동
                if (response == null) {
                	getView().setLocked(false);
                	//Window.alert("내정보입력 페이지로 이동합니다.");
                	clientFactory.getPlaceController().goTo(new TaskMyInfoPlace());
                }else{
                	setSajuView(response);
                	getView().setLocked(false);
                }
              }
            });
  }
 
  @Override
  public Widget asWidget() {
    return getView().asWidget();
  }

  @Override
  public String mayStop() {
    return null; // always happy to stop
  }

 

  @Override
  public void start(EventBus eventBus) {
    this.eventBus = eventBus;
System.out.println("start");	

	getView().init();

  
  }

  
  
  
  @Override
  public void stop() {
	 
	  getView().init();
    //eventBus = null;
  }

  
  
 
  
  
  /**
   * 사주정보를 설정한다.
   */
  private void setSajuView(List<SajuDataTableProxy> sajuDatas) {
	  getView().setSajuData(sajuDatas);
  }
  
 
  
  
  private CurrentSajuView getView() {
    return clientFactory.getCurrentSajuView();
  }

  
  public void goTo(Place place){
	  clientFactory.getPlaceController().goTo(place);
  }
  
  
  public void confirm(){
	  clientFactory.getPlaceController().goTo(new TaskMainPlace());
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