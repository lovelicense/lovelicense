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
import com.google.gwt.homepage.lovelicense.client.presenter.CurrentSajuView.CwConstants;
import com.google.gwt.homepage.lovelicense.client.presenter.SajuViewView;
import com.google.gwt.homepage.lovelicense.client.ui.EditorListBox;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableProxy;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
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
public class MobileCurrentSajuView extends Composite implements CurrentSajuView {

	
	private CwConstants constants;
	
	
	/**
	   * Editor driver for this view.
	   */
	  //interface Driver extends RequestFactoryEditorDriver<SajuDataTableProxy, MobileSajuViewView> {
	 // }
	
	
	/**
	   * The UiBinder interface.
	   */
	  interface MobileCurrentSajuViewUiBinder extends UiBinder<Widget, MobileCurrentSajuView> {
	  }

	  /**
	   * The UiBinder used to generate the view.
	   */
	  private static MobileCurrentSajuViewUiBinder uiBinder = GWT.create(MobileCurrentSajuViewUiBinder.class);

	  
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
	  Label mySaju_birthdatetime;
	  
	  /**
	   * 
	   */
	  @UiField(provided = true)
	  Grid mySaju_sajuInfo;
	  //TextBox addr1Editor;
	  
	  @UiField(provided = true)
	  FlexTable mySaju_sajuShortInfo;
	  
	  
	  
	  
	  
	  
	  
	  @UiField(provided = true)
	  Label currentSaju_birthdatetime;
	  
	  /**
	   * 
	   */
	  @UiField(provided = true)
	  Grid currentSaju_sajuInfo;
	  //TextBox addr1Editor;
	  
	  @UiField(provided = true)
	  FlexTable currentSaju_sajuShortInfo;
	  
	  
	  
	  @UiField(provided = true)
	  FlexTable ohhangInfo;
	  
	  @UiField(provided = true)
	  FlexTable habInfo;
	  
	  @UiField(provided = true)
	  FlexTable salInfo;
	  
	  @UiField(provided = true)
	  FlexTable gitaInfo;

	  
	  
	  
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
  public MobileCurrentSajuView(CwConstants constants) {
  
	  this.constants=constants;
	 
	  subTitle = new Label();
	  subTitle.setText(constants.mainMenuNameCurrentSaju());
	  
	  
	  /////////////////내사주/////////////////////////
	  
	  mySaju_birthdatetime = new Label();
	  
	  mySaju_sajuInfo = new Grid(4,3);
	 
	    
	  mySaju_sajuShortInfo = new FlexTable();
	    //sajuShortInfo.addStyleName("sajuShortInfo");
	    
	  mySaju_sajuShortInfo.setHTML(0, 0, "&nbsp");
	  mySaju_sajuShortInfo.getCellFormatter().setStyleName(0, 0, "ohangCnt");
	  mySaju_sajuShortInfo.setHTML(0, 1, "&nbsp");
	  mySaju_sajuShortInfo.getCellFormatter().setStyleName(0, 1, "percent");
	  mySaju_sajuShortInfo.setHTML(1, 0, "&nbsp");
	  mySaju_sajuShortInfo.getCellFormatter().setStyleName(1, 0, "yoyak");
	    FlexCellFormatter cellFormatter = mySaju_sajuShortInfo.getFlexCellFormatter();
	    cellFormatter.setRowSpan(0, 0, 2);
	  
	  
	    //////////////////////////////////////////////////////////////
	    
	    
	    
	    
	    
	    /////////////////현재사주/////////////////////////
		  
	    currentSaju_birthdatetime = new Label();
		  
	    currentSaju_sajuInfo = new Grid(4,3);
		 
		    
	    currentSaju_sajuShortInfo = new FlexTable();
		    //sajuShortInfo.addStyleName("sajuShortInfo");
		    
	    currentSaju_sajuShortInfo.setHTML(0, 0, "&nbsp");
	    currentSaju_sajuShortInfo.getCellFormatter().setStyleName(0, 0, "ohangCnt");
	    currentSaju_sajuShortInfo.setHTML(0, 1, "&nbsp");
	    currentSaju_sajuShortInfo.getCellFormatter().setStyleName(0, 1, "percent");
	    currentSaju_sajuShortInfo.setHTML(1, 0, "&nbsp");
	    currentSaju_sajuShortInfo.getCellFormatter().setStyleName(1, 0, "yoyak");
		cellFormatter = currentSaju_sajuShortInfo.getFlexCellFormatter();
		cellFormatter.setRowSpan(0, 0, 2);

		 //////////////////////////////////////////////////////////////
	    
	    
		
		
		
		/////////////////사주 결과/////////////////////////
		  
		//1. 오행
	    ohhangInfo = new FlexTable();
	    ohhangInfo.addStyleName("ohhangInfo");
	    
	    ohhangInfo.setHTML(0, 0, "&nbsp;");
	    
	    
	    
	   //2. 합
	   habInfo = new FlexTable();
	    habInfo.addStyleName("habInfo");
	    
	    habInfo.setHTML(0, 0, "해묘미");
	    habInfo.setHTML(1, 0, "인오술");
	    habInfo.setHTML(2, 0, "사유축");
	    habInfo.setHTML(3, 0, "신자진");
	    
	    habInfo.setHTML(0, 1, "&nbsp;");
	    habInfo.setHTML(1, 1, "&nbsp;");
	    habInfo.setHTML(2, 1, "&nbsp;");
	    habInfo.setHTML(3, 1, "&nbsp;");

	    
	    
	    
	   //3. 살
	    salInfo = new FlexTable();
	    salInfo.setStyleName("salInfo");
	    
	    salInfo.setHTML(0, 0, "자미");
	    salInfo.setHTML(1, 0, "축오");
	    salInfo.setHTML(2, 0, "인유");
	    salInfo.setHTML(3, 0, "묘신");
	    salInfo.setHTML(4, 0, "진해");
	    salInfo.setHTML(5, 0, "사술");
	    
	    
	    
	    
	    salInfo.setHTML(0, 1, "&nbsp;");
	    salInfo.setHTML(1, 1, "&nbsp;");
	    salInfo.setHTML(2, 1, "&nbsp;");
	    salInfo.setHTML(3, 1, "&nbsp;");
	    salInfo.setHTML(4, 1, "&nbsp;");
	    salInfo.setHTML(5, 1, "&nbsp;");

	    
	    
	  //4. 총점, 음양비율, 일천간 합,충, 불혼개폐살
	    gitaInfo = new FlexTable();
	    gitaInfo.setStyleName("gitaInfo");
	    
	    
	    gitaInfo.setHTML(0, 0, "음양");
	    gitaInfo.setHTML(1, 0, "일천간합");
	    gitaInfo.setHTML(2, 0, "일천간충");
	    gitaInfo.setHTML(3, 0, "불혼개폐살");
	    gitaInfo.setHTML(4, 0, "총점");
	    
	    
	    
	    
	    
	    gitaInfo.setHTML(0, 1, "&nbsp;");
	    gitaInfo.setHTML(1, 1, "&nbsp;");
	    gitaInfo.setHTML(2, 1, "&nbsp;");
	    gitaInfo.setHTML(3, 1, "&nbsp;");
	    gitaInfo.setHTML(4, 1, "&nbsp;");
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
  
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
  
  public void setLocked(boolean locked) {
	    setGlassPanelVisible(locked);
	  }
  

  
  
  public void setSajuData(List<SajuDataTableProxy> sajuDatas){
	  
	  
	  //
	  if(sajuDatas.size()!=3){
		  Window.alert("값이 부족합니다.");
		  presenter.goTo(new TaskMainPlace());//메인으로 이동시킴
	  }
	  
	  SajuDataTableProxy sajuData;
	  for(int i=0;i<sajuDatas.size();i++){
		  sajuData=sajuDatas.get(i);
		  
		  if(i==0){//내사주
			  mySaju_birthdatetime.setText("내사주 생년월일시 : "+sajuData.getSolar_year()+"년 "+sajuData.getSolar_month()+"월 "+sajuData.getSolar_date()+"일 "+sajuData.getTransTimeValue());
			  //사주팔자 
			  mySaju_sajuInfo.setText(0, 0, sajuData.getYearSkyVal()+sajuData.getYearLandVal());
			  mySaju_sajuInfo.setText(1, 0, sajuData.getMonthSkyVal()+sajuData.getMonthLandVal());
			  mySaju_sajuInfo.setText(2, 0, sajuData.getDateSkyVal()+sajuData.getDateLandVal());
			  mySaju_sajuInfo.setText(3, 0, sajuData.getTimeSkyVal()+sajuData.getTimeLandVal());
			  
			  
			  //오행
			  mySaju_sajuInfo.setText(0, 1, sajuData.getYearSky5hang()+sajuData.getYearLand5hang());
			  mySaju_sajuInfo.setText(1, 1, sajuData.getMonthSky5hang()+sajuData.getMonthLand5hang());
			  mySaju_sajuInfo.setText(2, 1, sajuData.getDateSky5hang()+sajuData.getDateLand5hang());
			  mySaju_sajuInfo.setText(3, 1, sajuData.getTimeSky5hang()+sajuData.getTimeLand5hang());
			  
			  // +, -
			  mySaju_sajuInfo.setText(0, 2, sajuData.getYearSkyPM()+sajuData.getYearLandPM());
			  mySaju_sajuInfo.setText(1, 2, sajuData.getMonthSkyPM()+sajuData.getMonthLandPM());
			  mySaju_sajuInfo.setText(2, 2, sajuData.getDateSkyPM()+sajuData.getDateLandPM());
			  mySaju_sajuInfo.setText(3, 2, sajuData.getTimeSkyPM()+sajuData.getTimeLandPM());
		 	  
			  //오행 갯수
			  //int[] onhangCnt = sajuData.getOhhangCnt();
			  //System.out.println("##11"+sajuData.getOhhangCnt0()+sajuData.getOhhangCnt1()+onhangCnt[2]+onhangCnt[3]+onhangCnt[4]);	 	  
			  mySaju_sajuShortInfo.setHTML(0, 0, "木 "+sajuData.getOhhangCnt0()+"<br>"+"火 "+sajuData.getOhhangCnt1()+"<br>"+"土 "+sajuData.getOhhangCnt2()+"<br>"+"金 "+sajuData.getOhhangCnt3()+"<br>"+"水 "+sajuData.getOhhangCnt4());
			  
			  //음양 비율
			  mySaju_sajuShortInfo.setText(0, 1, "음 "+sajuData.getMinusPercent()+" 양 "+sajuData.getPlusPercent());
		  
			  //간단한 설명
			  mySaju_sajuShortInfo.setText(1, 0, sajuData.getYoyakDesc());
		  }else if(i==1){//현재사주
			  currentSaju_birthdatetime.setText("현재사주 생년월일시 : "+sajuData.getSolar_year()+"년 "+sajuData.getSolar_month()+"월 "+sajuData.getSolar_date()+"일 "+sajuData.getTransTimeValue());
			  //사주팔자 
			  currentSaju_sajuInfo.setText(0, 0, sajuData.getYearSkyVal()+sajuData.getYearLandVal());
			  currentSaju_sajuInfo.setText(1, 0, sajuData.getMonthSkyVal()+sajuData.getMonthLandVal());
			  currentSaju_sajuInfo.setText(2, 0, sajuData.getDateSkyVal()+sajuData.getDateLandVal());
			  currentSaju_sajuInfo.setText(3, 0, sajuData.getTimeSkyVal()+sajuData.getTimeLandVal());
			  
			  
			  //오행
			  currentSaju_sajuInfo.setText(0, 1, sajuData.getYearSky5hang()+sajuData.getYearLand5hang());
			  currentSaju_sajuInfo.setText(1, 1, sajuData.getMonthSky5hang()+sajuData.getMonthLand5hang());
			  currentSaju_sajuInfo.setText(2, 1, sajuData.getDateSky5hang()+sajuData.getDateLand5hang());
			  currentSaju_sajuInfo.setText(3, 1, sajuData.getTimeSky5hang()+sajuData.getTimeLand5hang());
			  
			  // +, -
			  currentSaju_sajuInfo.setText(0, 2, sajuData.getYearSkyPM()+sajuData.getYearLandPM());
			  currentSaju_sajuInfo.setText(1, 2, sajuData.getMonthSkyPM()+sajuData.getMonthLandPM());
			  currentSaju_sajuInfo.setText(2, 2, sajuData.getDateSkyPM()+sajuData.getDateLandPM());
			  currentSaju_sajuInfo.setText(3, 2, sajuData.getTimeSkyPM()+sajuData.getTimeLandPM());
		 	  
			  //오행 갯수
			  //int[] onhangCnt = sajuData.getOhhangCnt();
			  //System.out.println("##11"+sajuData.getOhhangCnt0()+sajuData.getOhhangCnt1()+onhangCnt[2]+onhangCnt[3]+onhangCnt[4]);	 	  
			  currentSaju_sajuShortInfo.setHTML(0, 0, "木 "+sajuData.getOhhangCnt0()+"<br>"+"火 "+sajuData.getOhhangCnt1()+"<br>"+"土 "+sajuData.getOhhangCnt2()+"<br>"+"金 "+sajuData.getOhhangCnt3()+"<br>"+"水 "+sajuData.getOhhangCnt4());
			  
			  //음양 비율
			  currentSaju_sajuShortInfo.setText(0, 1, "음 "+sajuData.getMinusPercent()+" 양 "+sajuData.getPlusPercent());
		  
			  //간단한 설명
			  currentSaju_sajuShortInfo.setText(1, 0, sajuData.getYoyakDesc());
			  
		  }else if(i==2){
			//오행 갯수
	    	  
	    	  ohhangInfo.setHTML(0, 0, "木 "+sajuData.getOhhangCntAvg0()+"<br>"+"火 "+sajuData.getOhhangCntAvg1()+"<br>"+"土 "+sajuData.getOhhangCntAvg2()+"<br>"+"金 "+sajuData.getOhhangCntAvg3()+"<br>"+"水 "+sajuData.getOhhangCntAvg4());
	    	  
	    	  //합 갯수
	    	  habInfo.setHTML(0, 1, Integer.toString(sajuData.getMokPlus()));
	    	  habInfo.setHTML(1, 1, Integer.toString(sajuData.getWhaPlus()));
	    	  habInfo.setHTML(2, 1, Integer.toString(sajuData.getGeumPlus()));
	    	  habInfo.setHTML(3, 1, Integer.toString(sajuData.getSuPlus()));
	    	  
	    	  //살 갯수
	    	  salInfo.setHTML(0, 1, Integer.toString(sajuData.getJamiMinus()));
	    	  salInfo.setHTML(1, 1, Integer.toString(sajuData.getChukohMinus()));
	    	  salInfo.setHTML(2, 1, Integer.toString(sajuData.getInyuMinus()));
	    	  salInfo.setHTML(3, 1, Integer.toString(sajuData.getMyosinMinus()));
	    	  salInfo.setHTML(4, 1, Integer.toString(sajuData.getJinhaeMinus()));
	    	  salInfo.setHTML(5, 1, Integer.toString(sajuData.getSasulMinus()));
	    	  
	    	//기타 정보
	    	  gitaInfo.setHTML(0, 1, "음  "+Integer.toString(sajuData.getMinusPercent())+"양  "+Integer.toString(sajuData.getPlusPercent()));
	    	  String skyHab="";
	    	  if(sajuData.getSkyHab().length()==2){
	    		  skyHab=sajuData.getSkyHab();
	    	  }else{
	    		  skyHab="X";
	    	  }
	    	  gitaInfo.setHTML(1, 1, skyHab);
	    	  String skyChung="";
	    	  if(sajuData.getSkyChung().length()==2){
	    		  skyChung=sajuData.getSkyChung();
	    	  }else{
	    		  skyChung="X";
	    	  }
	    	  gitaInfo.setHTML(2, 1, skyChung);
	    	  String neverMarriage="";
	    	  if(sajuData.isNeverMarriage()){
	    		  neverMarriage="O";
	    	  }else{
	    		  neverMarriage="X";
	    	  }
	    	  gitaInfo.setHTML(3, 1, neverMarriage);
	    	  gitaInfo.setHTML(4, 1, Integer.toString(sajuData.getTotalScore()));
		  }
		  
		  
	  }
	  
	 

	  
  }

  public void init(){
	  mySaju_birthdatetime.setText("");
	  mySaju_sajuInfo.setText(0, 1, "");
	  mySaju_sajuInfo.setText(1, 1, "");
	  mySaju_sajuInfo.setText(2, 1, "");
	  mySaju_sajuInfo.setText(3, 1, "");
	  
	  mySaju_sajuShortInfo.setHTML(0, 0, "&nbsp");
	  mySaju_sajuShortInfo.setHTML(0, 1, "&nbsp");
	  mySaju_sajuShortInfo.setHTML(1, 0, "&nbsp");
	    
	    
	    
	    
	  currentSaju_birthdatetime.setText("");
	  currentSaju_sajuInfo.setText(0, 1, "");
	  currentSaju_sajuInfo.setText(1, 1, "");
	  currentSaju_sajuInfo.setText(2, 1, "");
	  currentSaju_sajuInfo.setText(3, 1, "");
		  
	  currentSaju_sajuShortInfo.setHTML(0, 0, "&nbsp");
	  currentSaju_sajuShortInfo.setHTML(0, 1, "&nbsp");
	  currentSaju_sajuShortInfo.setHTML(1, 0, "&nbsp");
	    
	    
	    ohhangInfo.setHTML(0, 0, "&nbsp;");

	    
	    habInfo.setHTML(0, 1, "&nbsp;");
	    habInfo.setHTML(1, 1, "&nbsp;");
	    habInfo.setHTML(2, 1, "&nbsp;");
	    habInfo.setHTML(3, 1, "&nbsp;");

	    
	    
	    salInfo.setHTML(0, 1, "&nbsp;");
	    salInfo.setHTML(1, 1, "&nbsp;");
	    salInfo.setHTML(2, 1, "&nbsp;");
	    salInfo.setHTML(3, 1, "&nbsp;");
	    salInfo.setHTML(4, 1, "&nbsp;");
	    salInfo.setHTML(5, 1, "&nbsp;");  
	    
	  
	    
	    
	    
	    gitaInfo.setHTML(0, 1, "&nbsp;");
	    gitaInfo.setHTML(1, 1, "&nbsp;");
	    gitaInfo.setHTML(2, 1, "&nbsp;");
	    gitaInfo.setHTML(3, 1, "&nbsp;");
	    gitaInfo.setHTML(4, 1, "&nbsp;");
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
