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


import com.google.gwt.homepage.lovelicense.client.mobile.MobileCurrentSajuView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileGuestBookDetailView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileGuestBookEditView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileGuestBookListView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileGuestBookWriteView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileMainView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileMyInfoView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileSajuViewView;
import com.google.gwt.homepage.lovelicense.client.mobile.MobileSearchFriendView;
import com.google.gwt.homepage.lovelicense.client.presenter.CurrentSajuView;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookDetailView;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookEditView;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookListView;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookWriteView;
import com.google.gwt.homepage.lovelicense.client.presenter.MainView;
import com.google.gwt.homepage.lovelicense.client.presenter.MyInfoView;
import com.google.gwt.homepage.lovelicense.client.presenter.SajuViewView;
import com.google.gwt.homepage.lovelicense.client.presenter.SearchFriendView;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookDetailView;

import com.google.gwt.homepage.lovelicense.client.mobile.LoveLicenseShellMobile;

import com.google.gwt.homepage.ui.client.OrientationHelper;
import com.google.gwt.homepage.ui.client.WindowBasedOrientationHelper;

/**
 * Mobile version of {@link ClientFactory}.
 */
public class ClientFactoryImplMobile extends ClientFactoryImpl {
  private final OrientationHelper orientationHelper = new WindowBasedOrientationHelper();

  @Override
  protected LoveLicenseShell createShell() {
    return new LoveLicenseShellMobile(getEventBus(), orientationHelper, createMainView(),
    		createMyInfoView(),createSajuViewView(),createCurrentSajuView(),createSearchFriendView(),createGuestBookListView(),createGuestBookDetailView(),createGuestBookWriteView(),createGuestBookEditView(), constants);
  }

  @Override
  protected MainView createMainView() {
	  
    return new MobileMainView(eventBus,placeController,constants);
  }

  @Override
  protected MyInfoView createMyInfoView() {
    return new MobileMyInfoView(constants);
  }
  
  @Override
  protected SajuViewView createSajuViewView() {
    return new MobileSajuViewView(constants);
  }
  
  @Override
  protected CurrentSajuView createCurrentSajuView() {
    return new MobileCurrentSajuView(constants);
  }
  
  @Override
  protected SearchFriendView createSearchFriendView() {
    return new MobileSearchFriendView(constants);
  }
  
  @Override
  protected GuestBookListView createGuestBookListView() {
    return new MobileGuestBookListView(constants);
  }
  
  @Override
  protected GuestBookDetailView createGuestBookDetailView() {
    return new MobileGuestBookDetailView(constants);
  }
  
  @Override
  protected GuestBookWriteView createGuestBookWriteView() {
    return new MobileGuestBookWriteView(constants);
  }
  
  @Override
  protected GuestBookEditView createGuestBookEditView() {
    return new MobileGuestBookEditView(constants);
  }
}
