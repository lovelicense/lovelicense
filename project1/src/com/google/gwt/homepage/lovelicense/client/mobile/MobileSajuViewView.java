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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.homepage.lovelicense.client.mobile.MobileMainView.MobileMainViewUiBinder;
import com.google.gwt.homepage.lovelicense.client.place.TaskMainPlace;
import com.google.gwt.homepage.lovelicense.client.presenter.MyInfoView;
import com.google.gwt.homepage.lovelicense.client.presenter.MainView.CwConstants;
import com.google.gwt.homepage.lovelicense.client.presenter.SajuViewView;
import com.google.gwt.homepage.lovelicense.client.ui.EditorListBox;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableProxy;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
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
public class MobileSajuViewView extends Composite implements SajuViewView {

	
	private CwConstants constants;
	
	
	/**
	   * Editor driver for this view.
	   */
	  //interface Driver extends RequestFactoryEditorDriver<SajuDataTableProxy, MobileSajuViewView> {
	 // }
	
	
	/**
	   * The UiBinder interface.
	   */
	  interface MobileSajuViewViewUiBinder extends UiBinder<Widget, MobileSajuViewView> {
	  }

	  /**
	   * The UiBinder used to generate the view.
	   */
	  private static MobileSajuViewViewUiBinder uiBinder = GWT.create(MobileSajuViewViewUiBinder.class);

	  
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
	  Label birthdatetime;
	  
	  /**
	   * 
	   */
	  @UiField(provided = true)
	  Grid sajuInfo;
	  //TextBox addr1Editor;
	  
	  @UiField(provided = true)
	  FlexTable sajuShortInfo;
	  
	  
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
  public MobileSajuViewView(CwConstants constants) {
  
	  this.constants=constants;
	 
	  subTitle = new Label();
	  subTitle.setText(constants.mainMenuNameInspectSaju());
	  
	  birthdatetime = new Label();
	  
	    sajuInfo = new Grid(4,3);
	 
	    
	    sajuShortInfo = new FlexTable();
	    //sajuShortInfo.addStyleName("sajuShortInfo");
	    
	    sajuShortInfo.setHTML(0, 0, "&nbsp");
	    sajuShortInfo.getCellFormatter().setStyleName(0, 0, "ohangCnt");
	    sajuShortInfo.setHTML(0, 1, "&nbsp");
	    sajuShortInfo.getCellFormatter().setStyleName(0, 1, "percent");
	    sajuShortInfo.setHTML(1, 0, "&nbsp");
	    sajuShortInfo.getCellFormatter().setStyleName(1, 0, "yoyak");
	    FlexCellFormatter cellFormatter = sajuShortInfo.getFlexCellFormatter();
	    cellFormatter.setRowSpan(0, 0, 2);
	  
	  
	  
	  	  
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
  
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
  
  public void setLocked(boolean locked) {
	    setGlassPanelVisible(locked);
	  }
  

  
  //사주 설정
  public void setSajuData(SajuDataTableProxy sajuData){
	  
	  
	  birthdatetime.setText("생년월일시 : "+sajuData.getSolar_year()+"년 "+sajuData.getSolar_month()+"월 "+sajuData.getSolar_date()+"일 "+sajuData.getTransTimeValue());
	  //사주팔자 
	  sajuInfo.setText(0, 0, sajuData.getYearSkyVal()+sajuData.getYearLandVal());
	  sajuInfo.setText(1, 0, sajuData.getMonthSkyVal()+sajuData.getMonthLandVal());
	  sajuInfo.setText(2, 0, sajuData.getDateSkyVal()+sajuData.getDateLandVal());
	  sajuInfo.setText(3, 0, sajuData.getTimeSkyVal()+sajuData.getTimeLandVal());
	  
	  
	  //오행
	  sajuInfo.setText(0, 1, sajuData.getYearSky5hang()+sajuData.getYearLand5hang());
	  sajuInfo.setText(1, 1, sajuData.getMonthSky5hang()+sajuData.getMonthLand5hang());
	  sajuInfo.setText(2, 1, sajuData.getDateSky5hang()+sajuData.getDateLand5hang());
	  sajuInfo.setText(3, 1, sajuData.getTimeSky5hang()+sajuData.getTimeLand5hang());
	  
	  // +, -
	  sajuInfo.setText(0, 2, sajuData.getYearSkyPM()+sajuData.getYearLandPM());
	  sajuInfo.setText(1, 2, sajuData.getMonthSkyPM()+sajuData.getMonthLandPM());
	  sajuInfo.setText(2, 2, sajuData.getDateSkyPM()+sajuData.getDateLandPM());
	  sajuInfo.setText(3, 2, sajuData.getTimeSkyPM()+sajuData.getTimeLandPM());
System.out.println("##11");	  
	  //오행 갯수
	  //int[] onhangCnt = sajuData.getOhhangCnt();
	  //System.out.println("##11"+sajuData.getOhhangCnt0()+sajuData.getOhhangCnt1()+onhangCnt[2]+onhangCnt[3]+onhangCnt[4]);	 	  
	  sajuShortInfo.setHTML(0, 0, "木 "+sajuData.getOhhangCnt0()+"<br>"+"火 "+sajuData.getOhhangCnt1()+"<br>"+"土 "+sajuData.getOhhangCnt2()+"<br>"+"金 "+sajuData.getOhhangCnt3()+"<br>"+"水 "+sajuData.getOhhangCnt4());
	  System.out.println("##22");	  
	  //음양 비율
	  sajuShortInfo.setText(0, 1, "음 "+sajuData.getMinusPercent()+" 양 "+sajuData.getPlusPercent());
	  System.out.println("##33");  
	  //간단한 설명
	  sajuShortInfo.setText(1, 0, sajuData.getYoyakDesc());

	  
  }

  public void init(){
	  birthdatetime.setText("");
	  sajuInfo.setText(0, 1, "");
	  sajuInfo.setText(1, 1, "");
	  sajuInfo.setText(2, 1, "");
	  sajuInfo.setText(3, 1, "");
	  
	    sajuShortInfo.setHTML(0, 0, "&nbsp");
	    sajuShortInfo.setHTML(0, 1, "&nbsp");
	    sajuShortInfo.setHTML(1, 0, "&nbsp");
	  
	  
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
