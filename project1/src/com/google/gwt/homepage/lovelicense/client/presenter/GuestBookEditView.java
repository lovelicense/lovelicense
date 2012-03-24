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
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;
import com.google.gwt.homepage.ui.client.PresentsWidgets;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;

/**
 * Implemented by widgets that edit tasks.
 */
public interface GuestBookEditView extends Editor<GuestBookTableProxy>, IsWidget {
	
	 public static interface CwConstants extends Constants {
		    String mainMenuNameVisitBbs();
			
			String guestBookEditMenuSubject();
			String guestBookEditMenuDate();
			String guestBookEditMenuCnt();
			String guestBookEditMenuWriter();
			String guestBookEditMenuContents();
			
			
			
		
	  }

  /**
   * The presenter for this view.
   */
  public interface Presenter extends PresentsWidgets {
	  /**
	     * 취소
	     */
	    void cancelEdit();

    /**
     * Create a new task or save the current task based on the values in the
     * inputs.
     */
	    void saveGuestBook();
  }

  /**
   * Get the driver used to edit tasks in the view.
   */
  RequestFactoryEditorDriver<GuestBookTableProxy, ?> getEditorDriver();

 

  /**
   * Lock or unlock the UI so the user cannot enter data. The UI is locked until
   * the task is loaded.
   * 
   * @param locked true to lock, false to unlock
   */
  void setLocked(boolean locked);

  /**
   * The the violation associated with the name.
   * 
   * @param message the message to show, or null if no violation
   */
  //void setNameViolation(String message);

  /**
   * Set the {@link Presenter} for this view.
   * 
   * @param presenter the presenter
   */
  void setPresenter(Presenter presenter);
}
