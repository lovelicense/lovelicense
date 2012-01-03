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


import com.google.gwt.editor.client.Editor;
import com.google.gwt.homepage.ui.client.PresentsWidgets;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.place.shared.Place;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableProxy;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;

import java.util.List;

	

/**
 * Implemented by views that display a list of tasks.
 */
public interface MyInfoView  extends Editor<SajuDataTableProxy>,IsWidget {

	
	/**
	 * The constants used in this Widget.
	 */
	
	public static interface CwConstants extends Constants {
		
		
		String mainMenuNameMyInfo();
		
		String myInfoBirthInputTitle();
		String myInfoEtcInputTitle();
		String myInfoDesc();
		
		String saveBtn();
		String cancelBtn();
		
		String[] sajuTimeDatas();

		String[] jobs();
		String[] addrSido();
		String[] sexDatas();
		
		
		
		
		
	}

	
  /**
   * The presenter for this view.
   */
  public interface Presenter extends PresentsWidgets {
    /**
     * Select a task.
     * 
     * @param selected the select task
     */
    void goTo(Place place);
    
    void saveMyInfo();
    
    void cancelMyInfo();
    
  }

  /**
   * Get the driver used to edit tasks in the view.
   */
  RequestFactoryEditorDriver<SajuDataTableProxy, ?> getEditorDriver();

  /**
   * Sets the new presenter, and calls {@link Presenter#stop()} on the previous
   * one.
   */
  void setPresenter(Presenter presenter);

  //loading 이미지
  void setLocked(boolean locked);
  
  
//필드 초기화
  void init();
  
 
}
