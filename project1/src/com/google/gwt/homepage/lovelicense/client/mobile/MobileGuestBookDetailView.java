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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookDetailView;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookListView.CwConstants;
import com.google.gwt.homepage.lovelicense.client.widgets.GuestBookDetailEditor;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;

/**
 * View used to edit a task.
 */
public class MobileGuestBookDetailView extends Composite implements GuestBookDetailView {

	
	private CwConstants constants;
	
  /**
   * Editor driver for this view.
   */
  interface Driver extends SimpleBeanEditorDriver<GuestBookTableProxy, GuestBookDetailEditor> {
  }

  /**
   * The UiBinder interface.
   */
  interface MobileGuestBookDetailViewUiBinder extends UiBinder<Widget, MobileGuestBookDetailView> {
  }

  /**
   * The UiBinder used to generate the view.
   */
  private static MobileGuestBookDetailViewUiBinder uiBinder = GWT.create(MobileGuestBookDetailViewUiBinder.class);


  /**
   * The title.
   */
  @UiField(provided = true)
    @Ignore
  Label subTitle;
  
  @UiField
  GuestBookDetailEditor guestbookDetail;
 
  @UiField
  @Ignore
  TextButton editButton;
  
  @UiField
  @Ignore
  TextButton deleteButton;
  
  @UiField
  @Ignore
  TextButton listButton;

  private final Driver driver = GWT.create(Driver.class);

  /**
   * The {@link TaskReadView.Presenter} for this view.
   */
  private Presenter presenter;

  /**
   * Construct a new {@link MobileTaskEditView}.
   */
  public MobileGuestBookDetailView(CwConstants constants) {
	  this.constants=constants;
	  
	  subTitle = new Label();
	  subTitle.setText(constants.mainMenuNameVisitBbs());
	  
    initWidget(uiBinder.createAndBindUi(this));
    
    editButton.setVisible(false);//수정버튼은 default로 않보이게
    deleteButton.setVisible(false);//삭제버튼은 default로 않보이게
    
    driver.initialize(guestbookDetail);

    // Create a new task or modify the current task when done is pressed.
    editButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (presenter != null) {
          presenter.editGuestBookDetail();
        }
      }
    });
    
    // Create a new task or modify the current task when done is pressed.
    deleteButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (presenter != null) {
          presenter.deleteGuestBook();
        }
      }
    });
    
    // Create a new task or modify the current task when done is pressed.
    listButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (presenter != null) {
          presenter.cancelGuestBook();
        }
      }
    });
  }

  public SimpleBeanEditorDriver<GuestBookTableProxy, ?> getEditorDriver() {
    return driver;
  }
 
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
  
  public void setModifyBtn(){
	  editButton.setVisible(true);
	  deleteButton.setVisible(true);
	  
  }
  
  //답변이 있는지 없는지에 따라 설정
  public void setAnswerVisible(boolean answerExist){
	  guestbookDetail.setAnswerVisible(answerExist);
  }
}
