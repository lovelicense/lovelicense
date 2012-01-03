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
import com.google.gwt.homepage.lovelicense.client.ui.EditorListBox;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableProxy;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
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
public class MobileMyInfoView extends Composite implements MyInfoView {

	
	private CwConstants constants;
	
	
	/**
	   * Editor driver for this view.
	   */
	  interface Driver extends RequestFactoryEditorDriver<SajuDataTableProxy, MobileMyInfoView> {
	  }
	
	
	/**
	   * The UiBinder interface.
	   */
	  interface MobileMyInfoViewUiBinder extends UiBinder<Widget, MobileMyInfoView> {
	  }

	  /**
	   * The UiBinder used to generate the view.
	   */
	  private static MobileMyInfoViewUiBinder uiBinder = GWT.create(MobileMyInfoViewUiBinder.class);

	  
	  /**
	   * The glass panel used to lock the UI.
	   */
	  private static PopupPanel glassPanel;
	  
	  
	  /**
	   * The title.
	   */
	  @UiField(provided = true)
	  @Ignore
	  Label subTitle;
	  
	  /**
	   * The descLbl.
	   */
	  @UiField(provided = true)
	  @Ignore
	  Label descLbl;
	  
	  
	  
	  
	  /**
	   * The year.
	   */
	  @UiField(provided = true)
	  ValueListBox<String> solar_yearEditor;
	  //TextBox solar_yearEditor;
	  
	  /**
	   * The month.
	   */
	  @UiField(provided = true)
	  ValueListBox<String> solar_monthEditor;
	  //TextBox solar_monthEditor;
	  
	  /**
	   * The date.
	   */
	  @UiField(provided = true)
	  ValueListBox<String> solar_dateEditor;
	  //TextBox solar_dateEditor;
	  
	  /**
	   * The time.
	   */
	  @UiField(provided = true)
	  EditorListBox birth_timeEditor;
	  //ListBox birth_timeEditor;
	  //TextBox birth_timeEditor;
	  
	  /**
	   * The sex.
	   */
	  @UiField(provided = true)
	  ValueListBox<String> sexEditor;
	  //TextBox sexEditor;
	  
	  /**
	   * The job.
	   */
	  @UiField(provided = true)
	  ValueListBox<String> jobEditor;
	  //TextBox jobEditor;
	  
	  /**
	   * The addr.
	   */
	  @UiField(provided = true)
	  ValueListBox<String> addr1Editor;
	  //TextBox addr1Editor;
	  
	  /**
	   * The saveButton.
	   */
	  @UiField(provided = true)
	  @Ignore
	  TextButton saveButton;
	  
	  /**
	   * The cancelButton.
	   */
	  @UiField(provided = true)
	  @Ignore
	  TextButton cancelButton;
	  
	  
  private final Driver driver = GWT.create(Driver.class);
	  
  private Presenter presenter;

  /**
   * Construct a new {@link MobileTaskEditView}.
   */
  public MobileMyInfoView(CwConstants constants) {
  
	  this.constants=constants;
	 
	  subTitle = new Label();
	  subTitle.setText(constants.mainMenuNameMyInfo());
	  
	  descLbl = new Label();
	  descLbl.setText(constants.myInfoDesc());
	  
	  //나머지 것도 constants 처리하자.
	  
	  
	  //solar_yearEditor = new TextBox();
	  solar_yearEditor = new ValueListBox<String>(new Renderer<String>(){
			  @Override
			  public String render(String object)
			  {
			  return object;
			  }

			  @Override
			  public void render(String object, Appendable appendable) throws IOException
			  {
			  }
			  });	 
	  
	  ArrayList yearList = new ArrayList();
	  for (int i = 1881, j=0; i < 2050; i++,j++) {
		  //yearList.add(Integer.toString(i));
		  solar_yearEditor.setValue(Integer.toString(i));
      }
	  
	  //solar_yearEditor.setValue("0000");
	  //solar_yearEditor.setAcceptableValues(yearList);
	  
	    
	    
	  
	  /*
	    solar_yearEditor = new ValueListBox();
	    for (int i = 1881, j=0; i < 2050; i++,j++) {
	    	solar_yearEditor.addItem(Integer.toString(i));
	    	solar_yearEditor.setValue(j, Integer.toString(i));
	    }
	    solar_yearEditor.setItemSelected(100, true);//default 1981년 선택
	    */
	    
	  
	  solar_monthEditor = new ValueListBox<String>(new Renderer<String>(){
		  @Override
		  public String render(String object)
		  {
		  return object;
		  }

		  @Override
		  public void render(String object, Appendable appendable) throws IOException
		  {
		  }
		  });	 
  
	  ArrayList monthList = new ArrayList();
	  for (int i = 1; i < 13; i++) {
		  //monthList.add(Integer.toString(i));
		  solar_monthEditor.setValue(Integer.toString(i));
	  }
	  //solar_monthEditor.setAcceptableValues(monthList);
	  
	  
	  //solar_monthEditor = new TextBox();
	    /*
	    solar_monthEditor = new ListBox();
	    for (int i = 1, j=0; i < 13; i++,j++) {
	    	solar_monthEditor.addItem(Integer.toString(i));
	    	solar_monthEditor.setValue(j, Integer.toString(i));
	    }
	    */
	    
	  
	  solar_dateEditor = new ValueListBox<String>(new Renderer<String>(){
		  @Override
		  public String render(String object)
		  {
		  return object;
		  }

		  @Override
		  public void render(String object, Appendable appendable) throws IOException
		  {
		  }
		  });	 
  
	  ArrayList dateList = new ArrayList();
	  for (int i = 1, j=0; i < 32; i++,j++) {
		  //dateList.add(Integer.toString(i));
		  solar_dateEditor.setValue(Integer.toString(i));
	  }
	  //solar_dateEditor.setAcceptableValues(dateList);
	  
	  
	    //solar_dateEditor = new TextBox();
	    /*
	    solar_dateEditor = new ListBox();
	    for (int i = 1, j=0; i < 32; i++,j++) {
	    	solar_dateEditor.addItem(Integer.toString(i));
	    	solar_dateEditor.setValue(j, Integer.toString(i));
	    }
		*/
	    
	  
	  birth_timeEditor = new EditorListBox();
	    String[] listTypes = constants.sajuTimeDatas();
	    for (int i = 0; i < listTypes.length; i++) {
	    	birth_timeEditor.addItem(listTypes[i]);
	    	birth_timeEditor.setValue(i, Integer.toString(i));//값을 0~11로 설정(23~1시(0), 1~3시(1)...)
	    }
	  
	    //birth_timeEditor = new TextBox();
	    /*
	    birth_timeEditor = new ListBox();
	    String[] listTypes = constants.sajuTimeDatas();
	    for (int i = 0; i < listTypes.length; i++) {
	    	birth_timeEditor.addItem(listTypes[i]);
	    	birth_timeEditor.setValue(i, Integer.toString(i));//값을 0~11로 설정(23~1시(0), 1~3시(1)...)
	    }
	    */
	    
	    
	    
	    sexEditor = new ValueListBox<String>(new Renderer<String>(){
			  @Override
			  public String render(String object)
			  {
			  return object;
			  }

			  @Override
			  public void render(String object, Appendable appendable) throws IOException
			  {
			  }
			  });	 
	  
		  ArrayList sexList = new ArrayList();
		  String[] sexTypes = constants.sexDatas();
		  for (int i = 0; i < sexTypes.length; i++) {
			  //sexList.add(sexTypes[i]);
			  sexEditor.setValue(sexTypes[i]);
		  }
		  //sexEditor.setAcceptableValues(sexList);
	    
	    
	    //sexEditor = new TextBox();
	    /*
	    sexEditor = new ListBox();
	    String[] sexTypes = constants.sexDatas();
	    for (int i = 0; i < sexTypes.length; i++) {
	    	sexEditor.addItem(sexTypes[i]);
	    	sexEditor.setValue(i, Integer.toString(i+1));//남자1, 여자2
	    }
	    */
	    
		  jobEditor = new ValueListBox<String>(new Renderer<String>(){
			  @Override
			  public String render(String object)
			  {
			  return object;
			  }

			  @Override
			  public void render(String object, Appendable appendable) throws IOException
			  {
			  }
			  });	 
	  
		  ArrayList jobList = new ArrayList();
		  String[] jobs = constants.jobs();
		  for (int i = 0; i < jobs.length; i++) {
			  //jobList.add(jobs[i]);
			  jobEditor.setValue(jobs[i]);
		  }
		  //jobEditor.setAcceptableValues(jobList);  
		  
		  
	    //jobEditor = new TextBox();
	    /*
	    jobEditor = new ListBox();
	    String[] jobs = constants.jobs();
	    for (int i = 0; i < jobs.length; i++) {
	    	jobEditor.addItem(jobs[i]);
	    	jobEditor.setValue(i, jobs[i]);//남자1, 여자2
	    }
	    */
	    
	    
		  addr1Editor = new ValueListBox<String>(new Renderer<String>(){
			  @Override
			  public String render(String object)
			  {
			  return object;
			  }

			  @Override
			  public void render(String object, Appendable appendable) throws IOException
			  {
			  }
			  });	 
	  
		  ArrayList addrList = new ArrayList();
		  String[] addrSido = constants.addrSido();
		  for (int i = 0; i < addrSido.length; i++) {
			  //addrList.add(addrSido[i]);
			  addr1Editor.setValue(addrSido[i]);
		  }
		  //addr1Editor.setAcceptableValues(addrList);  
		    
		  
	   // addr1Editor = new TextBox();
	    /*
	    addr1Editor = new ListBox();
	    String[] addrSido = constants.addrSido();
	    for (int i = 0; i < addrSido.length; i++) {
	    	addr1Editor.addItem(addrSido[i]);
	    	addr1Editor.setValue(i, addrSido[i]);//남자1, 여자2
	    }
	    */
	    driver.initialize(this);
	    
	    
	    saveButton = new TextButton();
	    saveButton.setText(constants.saveBtn());  
	 // Create a new task or modify the current task when done is pressed.
	    saveButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        if (presenter != null) {
	          presenter.saveMyInfo();
	        }
	      }
	    });
	    
	    cancelButton = new TextButton();
	    cancelButton.setText(constants.cancelBtn());
	 // Create a new task or modify the current task when done is pressed.
	    cancelButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        if (presenter != null) {
	        	//System.out.println("cancel000000000000########################################");
	          presenter.cancelMyInfo();
	        }
	      }
	    });
	    
	// Initialize the widget.
	initWidget(uiBinder.createAndBindUi(this));
  }
  
  public RequestFactoryEditorDriver<SajuDataTableProxy, ?> getEditorDriver() {
	    return driver;
	  }
  
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
  
  public void setLocked(boolean locked) {
	    setGlassPanelVisible(locked);
	  }
  
//필드 초기화
  public void init(){
	  solar_yearEditor.setValue("1981");
	  solar_monthEditor.setValue("1");
	  solar_dateEditor.setValue("1");
	  birth_timeEditor.setValue("1");
	  birth_timeEditor.setItemSelected(0, true);
	  jobEditor.setValue("직장인");
	  sexEditor.setValue("남");
	  addr1Editor.setValue("서울");
	  
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
