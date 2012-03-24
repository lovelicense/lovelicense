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
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;
import com.google.gwt.homepage.ui.client.PresentsWidgets;
import com.google.gwt.i18n.client.Constants;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * A readonly view of a task.
 */
public interface GuestBookDetailView extends Editor<GuestBookTableProxy>, IsWidget {

	
	/**
	   * The constants used in this Widget.
	   */
	  
	  public static interface CwConstants extends Constants {
		    String mainMenuNameVisitBbs();
			
			String guestBookDetailMenuSubject();
			String guestBookDetailMenuDate();
			String guestBookDetailMenuCnt();
			String guestBookDetailMenuWriter();
			String guestBookDetailMenuContents();
			
			
			
		
	  }
	  
  /**
   * The presenter for this view.
   */
  public interface Presenter extends PresentsWidgets {
    /**
     * Switch to an edit view of this task.
     */
    void editGuestBookDetail();
    void deleteGuestBook();
    void cancelGuestBook();
    
  }

  /**
   * Get the driver used to edit tasks in the view.
   */
  SimpleBeanEditorDriver<GuestBookTableProxy, ?> getEditorDriver();
  
  /**
   * Set the {@link Presenter} for this view.
   * @param presenter the presenter
   */
  void setPresenter(Presenter presenter);
  
  //수정버튼 활성화
  void setModifyBtn();
  
  //답변이 있을때 답변이 보이게 없을 때 없다는 메시지
  void setAnswerVisible(boolean answerExist);
}
