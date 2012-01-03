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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.homepage.lovelicense.client.mobile.MobileMainView.MobileMainViewUiBinder;
import com.google.gwt.homepage.lovelicense.client.place.TaskMainPlace;
import com.google.gwt.homepage.lovelicense.client.presenter.CurrentSajuView;
import com.google.gwt.homepage.lovelicense.client.presenter.CurrentSajuView.Presenter;
import com.google.gwt.homepage.lovelicense.client.presenter.MyInfoView;
import com.google.gwt.homepage.lovelicense.client.presenter.SearchFriendView;
import com.google.gwt.homepage.lovelicense.client.presenter.SearchFriendView.CwConstants;
import com.google.gwt.homepage.lovelicense.client.presenter.SajuViewView;
import com.google.gwt.homepage.lovelicense.client.ui.EditorListBox;

import com.google.gwt.homepage.lovelicense.shared.FriendTableProxy;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;


/**
 * View used to edit a task.
 */
public class MobileSearchFriendView extends Composite implements SearchFriendView {

	
	private CwConstants constants;
	
	
	/**
	   * Editor driver for this view.
	   */
	  //interface Driver extends RequestFactoryEditorDriver<SajuDataTableProxy, MobileSajuViewView> {
	 // }
	
	
	/**
	   * The UiBinder interface.
	   */
	  interface MobileSearchFriendViewUiBinder extends UiBinder<Widget, MobileSearchFriendView> {
	  }

	  /**
	   * The UiBinder used to generate the view.
	   */
	  private static MobileSearchFriendViewUiBinder uiBinder = GWT.create(MobileSearchFriendViewUiBinder.class);

	  
	  /**
	   * The glass panel used to lock the UI.
	   */
	  private static PopupPanel glassPanel;
	  
	  
	  /**
	   * The title.
	   */
	  @UiField(provided = true)
	  Label subTitle;
	  

	
	
	  
	  @UiField(provided = true)
	  FlexTable searchFriendTable;
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  /**
	   * The saveButton.
	   */
	  @UiField(provided = true)
	  TextButton confirmButton;
	  
	  
	  
	  
  //private final Driver driver = GWT.create(Driver.class);
	  
  private Presenter presenter;

  /**
   * Construct a new {@link MobileTaskEditView}.
   */
  public MobileSearchFriendView(CwConstants constants) {
  
	  this.constants=constants;
	 
	  subTitle = new Label();
	  subTitle.setText(constants.mainMenuNameSearchFriend());
	  
	 
	  /////////////////내사주/////////////////////////
	 
	     searchFriendTable = new FlexTable();
	    searchFriendTable.setVisible(false);
	    searchFriendTable.setWidget(0, 0, new HTML("순위"));
	    searchFriendTable.setWidget(0, 1, new HTML("점수"));
	    searchFriendTable.setWidget(0, 2, new HTML("성별"));
	    searchFriendTable.setWidget(0, 3, new HTML("채팅"));
	    searchFriendTable.setWidget(0, 4, new HTML("이메일"));
	    searchFriendTable.setWidget(0, 5, new HTML("사는곳"));
	    searchFriendTable.setWidget(0, 6, new HTML("직업"));
	    
	    searchFriendTable.addStyleName("searchFriendTable");
	    searchFriendTable.getRowFormatter().addStyleName(0, "title");
	    
	 
	    
	    //////////////////////////////////////////////////////////////
	    
	    
	    
	    
	    //driver.initialize(this);
	    
	    
	    confirmButton = new TextButton();
	    confirmButton.setText(constants.confirmBtn());  
	 // Create a new task or modify the current task when done is pressed.
	    confirmButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        if (presenter != null) {
	          presenter.goTo(new TaskMainPlace());
	        }
	      }
	    });
	    
	    
	    
	// Initialize the widget.
	initWidget(uiBinder.createAndBindUi(this));
  }
  
 // public RequestFactoryEditorDriver<SajuDataTableProxy, ?> getEditorDriver() {
	//    return driver;
	  //}
  
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
  
  @Override
  public void setLocked(boolean locked) {
	    setGlassPanelVisible(locked);
	  }
  

  
  @Override
  public void setSearchFriend(List<FriendTableProxy> friends) {
	     String sex="";
	     System.out.println("aaaaaaaaa"+friends.size());
	    if(friends.size()>0){
		    for (int i=0; i < friends.size() && i<10 ;i++) {
		    	FriendTableProxy friend=friends.get(i);
		    	searchFriendTable.setWidget(i+1, 0, new HTML(Integer.toString(i+1)));
		    	searchFriendTable.setWidget(i+1, 1, new HTML(Integer.toString(friend.getScore())));
		    	//if(friend.getSex().equals("1")){
		    		//sex="남";
		    	//}else if(friend.getSex().equals("2")){
		    		//sex="여";
		    	//}
		    	
		    	searchFriendTable.setWidget(i+1, 2, new HTML(friend.getSex()));
		    	searchFriendTable.setWidget(i+1, 3, new Button("채팅하기"));
		    	searchFriendTable.setWidget(i+1, 4, new Button("이메일 보내기"));
		    	searchFriendTable.setWidget(i+1, 5, new HTML(friend.getAddr1()));
		    	searchFriendTable.setWidget(i+1, 6, new HTML(friend.getJob()));
		    }
		    
		   
	    }else{
	    	FlexCellFormatter cellFormatter = searchFriendTable.getFlexCellFormatter();
	    	cellFormatter.setColSpan(1, 0, 7);
	    	searchFriendTable.setWidget(1, 0, new HTML("친구를 찾지 못했습니다."));
	    }
	   

		  
		  //결과가 보이게 설정
		  searchFriendTable.setVisible(true);

  }

  public void init(){
	  
	  //처리해야함.
	    
	  
	    searchFriendTable.setVisible(false);
	    
	    
	    

	  for(int i=1;i<searchFriendTable.getRowCount();i++){
			 searchFriendTable.removeRow(i);
	  }
  }
  
  /**
   * Show or hide the glass panel used to lock the UI will the task loads.
   * 
   * @param visible true to show, false to hide
   */
  private static void setGlassPanelVisible(boolean visible) {
    // Initialize the panel.
    if (glassPanel == null) {
      glassPanel = new DecoratedPopupPanel(false, true);
      glassPanel.setWidget(new Label("Loading..."));
    }

    if (visible) {
      // Show the loading panel.
      glassPanel.center();
    } else {
      // Hide the loading panel.
      glassPanel.hide();
    }
  }



  
}
