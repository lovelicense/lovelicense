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
package com.google.gwt.homepage.lovelicense.client.mobile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookDetailView;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookListView.CwConstants;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookWriteView;
import com.google.gwt.homepage.lovelicense.client.widgets.GuestBookDetailEditor;
import com.google.gwt.homepage.lovelicense.client.widgets.GuestBookWriteEditor;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;

/**
 * View used to edit a task.
 */
public class MobileGuestBookWriteView extends Composite implements GuestBookWriteView {

	
	private CwConstants constants;
	
  /**
   * Editor driver for this view.
   */
  interface Driver extends RequestFactoryEditorDriver<GuestBookTableProxy, GuestBookWriteEditor> {
  }

  /**
   * The UiBinder interface.
   */
  interface MobileGuestBookWriteViewUiBinder extends UiBinder<Widget, MobileGuestBookWriteView> {
  }

  /**
   * The UiBinder used to generate the view.
   */
  private static MobileGuestBookWriteViewUiBinder uiBinder = GWT.create(MobileGuestBookWriteViewUiBinder.class);


  /**
   * The title.
   */
  @UiField
    @Ignore
  Label subTitle;
  
  @UiField
  GuestBookWriteEditor guestbookWrite;
 
  @UiField
  @Ignore
  TextButton saveButton;
  
  @UiField
  @Ignore
  TextButton cancelButton;

  private final Driver driver = GWT.create(Driver.class);

  /**
   * The {@link TaskReadView.Presenter} for this view.
   */
  private Presenter presenter;

  /**
   * Construct a new {@link MobileTaskEditView}.
   */
  public MobileGuestBookWriteView(CwConstants constants) {
	  this.constants=constants;
	 
	  
	  
    initWidget(uiBinder.createAndBindUi(this));
    
    subTitle.setText(constants.mainMenuNameVisitBbs());
	  
    System.out.println("#########create2"); 
    
    driver.initialize(guestbookWrite);

    // Create a new task or modify the current task when done is pressed.
    saveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (presenter != null) {
          presenter.saveGuestBook();
        }
      }
    });
    
    // Create a new task or modify the current task when done is pressed.
    cancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (presenter != null) {
          presenter.cancelWrite();
        }
      }
    });
  }

  public RequestFactoryEditorDriver<GuestBookTableProxy, ?> getEditorDriver() {
    return driver;
  }
 
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
}
