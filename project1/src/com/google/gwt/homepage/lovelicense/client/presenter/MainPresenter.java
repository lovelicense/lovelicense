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
public class MainPresenter implements MainView.Presenter {

  



  private final ClientFactory clientFactory;

  private EventBus eventBus;
  
 

  public MainPresenter(ClientFactory clientFactory ) {
    this.clientFactory = clientFactory;
    clientFactory.getMainView().setPresenter(this);
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
    //this.eventBus = eventBus;
	  //System.out.println("##########################start");
  }

  @Override
  public void stop() {
	  //System.out.println("##########################stop");
    //eventBus = null;
  }

  private MainView getView() {
    return clientFactory.getMainView();
  }

  

  
}
