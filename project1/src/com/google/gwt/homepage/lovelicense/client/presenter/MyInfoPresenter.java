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

import com.google.gwt.core.client.GWT;
import com.google.gwt.homepage.gaerequest.client.GaeAuthRequestTransport;
import com.google.gwt.homepage.lovelicense.client.ClientFactory;
import com.google.gwt.homepage.lovelicense.client.place.TaskMainPlace;
import com.google.gwt.homepage.lovelicense.shared.LoveLicenseRequestFactory;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableProxy;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableRequest;

import com.google.gwt.place.shared.Place;

import com.google.gwt.homepage.lovelicense.client.event.ActionEvent;
import com.google.gwt.homepage.lovelicense.client.event.ActionNames;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestTransport;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

/**
 * Activity that presents a list of tasks.
 */
public class MyInfoPresenter implements MyInfoView.Presenter {

  
	 private static final Logger log = Logger.getLogger(MyInfoPresenter.class.getName());


  private final ClientFactory clientFactory;

  private EventBus eventBus;
  
  /**
   * The current task being edited, provided by RequestFactory.
   */
  private SajuDataTableProxy editTask;
  
  private Request<Void> taskPersistRequest;
  

  public MyInfoPresenter(ClientFactory clientFactory ) {

    this.clientFactory = clientFactory;
    clientFactory.getMyInfoView().setPresenter(this);
    getSajuDataTable();//회원정보(사주)를 가져온다.
  }

  
  /**
   * Refresh the task list.
   */
  private void getSajuDataTable() { 
	  //System.out.println("############################getSajuDataTable()");
	getView().setLocked(true);
	
/*
	RequestTransport requestTransport = new GaeAuthRequestTransport(eventBus);
	MobileWebAppRequestFactory requestFactory = GWT.create(MobileWebAppRequestFactory.class);
    requestFactory.initialize(eventBus, requestTransport);
	long a=3;
	
	System.out.println("??????????????????????????????");
	requestFactory.taskRequest().findAllTasks().fire(
	        new Receiver<List<TaskProxy>>() {
	          @Override
	          public void onFailure(ServerFailure error) {
	            // ignore
	        	  System.out.println("error error"+error.getMessage());
	          }

	          @Override
	          public void onSuccess(List<TaskProxy> response) {
System.out.println("####23232323############size"+response.size());	            
	            // Display the tasks in the view.
	            if (response == null) {
	              response = Collections.<TaskProxy> emptyList();
	            }
	           

	           
	          }
	        });
	
	System.out.println("55555555555555555555555555555555555555555");	
*/
	//SajuDataTableProxy
	
    clientFactory.getRequestFactory().sajuDataTableRequest().findSajuDataTableByEmail().fire(
        new Receiver<SajuDataTableProxy>() {
          @Override
          public void onFailure(ServerFailure error) {
            // ignore
        	 // System.out.println("############################findSajuDataTableByEmail.fail"+error.getMessage());
        	  getView().setLocked(false);
          }

          @Override
          public void onSuccess(SajuDataTableProxy response) {
            // Early exit if this activity has already been canceled.
            if (eventBus == null) {
              return;
            }

            editTask=response;
          // System.out.println("result##########"+response);
            
            //start()메소드에 넣어 두니
          //서버에서 정보를 가져오는 것을 비동기로 가져오기 때문에 문제발생
            //서버에서 editProxy를 가져오기전에 create인지 edit인지 판단해버림.
          //아래 if실행전에 수정/신규 구분이 않되고 있음
            if (editTask == null) {
      	      startCreate();
      	    } else {
      	      startEdit();
      	    }
            
      	  //System.out.println("############################findSajuDataTableByEmail.succes"); 
            // save the response to storage
            //clientFactory.getTaskProxyLocalStorage().setTasks(response);

            
          }
        });
    
   // System.out.println("55555555555555555555555555555555555555555");
  }
 
  @Override
  public Widget asWidget() {
    return getView().asWidget();
  }

  @Override
  public String mayStop() {
    return null; // always happy to stop
  }

 

  @Override
  public void start(EventBus eventBus) {
    this.eventBus = eventBus;
//System.out.println("start");	


  
  }

  
  
  
  @Override
  public void stop() {
	  //끝낼때 view에 값 초기화
	  getView().init();
    //eventBus = null;
  }

  
  
  private void startCreate() {
	    //isEditing = false;
	    //getView().setEditing(false);
//System.out.println("create");	  
	    SajuDataTableRequest request = clientFactory.getRequestFactory().sajuDataTableRequest();
	    editTask = request.create(SajuDataTableProxy.class);
	   
	

		//default값 설정
		editTask.setSolar_year("1981");
		editTask.setSolar_month("1");
		editTask.setSolar_date("1");
		editTask.setSex("남");
		editTask.setAddr1("서울");
		editTask.setJob("직장인");
		

	    getView().getEditorDriver().edit(editTask, request);
	    getView().setLocked(false);
  }

  private void startEdit() {
	    //isEditing = true;
	    //getView().setEditing(true);
	    // Lock the display until the task is loaded.
	    //getView().setLocked(true);
	  //System.out.println("edit");	  
	  getView().getEditorDriver().edit(editTask,clientFactory.getRequestFactory().sajuDataTableRequest());
	  getView().setLocked(false);
	  /*  clientFactory.getRequestFactory().taskRequest().findTask(this.taskId).fire(
	        new Receiver<TaskProxy>() {
	          @Override
	          public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
	            getView().setLocked(false);
	            getView().getEditorDriver().setConstraintViolations(violations);
	          }

	          @Override
	          public void onFailure(ServerFailure error) {
	            getView().setLocked(false);
	            doCancelTask();
	            super.onFailure(error);
	          }

	          @Override
	          public void onSuccess(TaskProxy response) {
	            // Early exit if we have already stopped.
	            if (eventBus == null) {
	              return;
	            }

	            // Task not found.
	            if (response == null) {
	              Window.alert("The task with id '" + taskId + "' could not be found."
	                  + " Please select a different task from the task list.");
	              doCancelTask();
	              return;
	            }

	            // Show the task.
	            editTask = response;
	            getView().getEditorDriver().edit(response,
	                clientFactory.getRequestFactory().taskRequest());
	            getView().setLocked(false);
	          }
	        });
	        */
	  }
  
  
  
  private MyInfoView getView() {
    return clientFactory.getMyInfoView();
  }

  
  public void goTo(Place place){
	  clientFactory.getPlaceController().goTo(place);
  }
  
  public void saveMyInfo(){
	  //Window.alert("저장하였습니다.");
	  //clientFactory.getPlaceController().goTo(new TaskMainPlace());
	  
	   // Flush the changes into the editable task.
	    SajuDataTableRequest context = (SajuDataTableRequest) clientFactory.getMyInfoView().getEditorDriver().flush();

	    /*
	     * Create a persist request the first time we try to save this task. If a
	     * request already exists, reuse it.
	     */
	    if (taskPersistRequest == null) {
	    	//System.out.println("taskPersistRequest##############");
	      taskPersistRequest = context.persist().using(editTask);
	    }
//System.out.println("save0");


/*
System.out.println("getBirth_time"+editTask.getSolar_year());
System.out.println("getSolar_month"+editTask.getSolar_month());
System.out.println("getSolar_date"+editTask.getSolar_date());
System.out.println("getBirth_time"+editTask.getBirth_time());
System.out.println("getSex"+editTask.getSex());
System.out.println("getJob"+editTask.getJob());
System.out.println("getAddr1"+editTask.getAddr1());
*/

	    // Fire the request.
	    taskPersistRequest.fire(new Receiver<Void>() {
	      
	    	  @Override
	          public void onFailure(ServerFailure error) {
	            // ignore
	        	  //System.out.println("fail_save1"+error.toString());
	        	  //System.out.println("fail_save1"+error.getMessage());
	        	 
	          }	
	    	

	      @Override
	      public void onSuccess(Void response) {
	        editTask = null;
//System.out.println("saveok0");
	        // Notify the user that the task was updated.
	        MyInfoPresenter.this.notify("MyInfo Saved");
//System.out.println("saveok1");	        
	        // Return to the task list.
	        // history를 남기지 않기 위해 이벤트를 사용
	        ActionEvent.fire(eventBus, ActionNames.MYINFO_SAVED);
	      }
	    });
	  
  }
  
  
  public void cancelMyInfo(){
//System.out.println("cancel########################################");	 
	        // Notify the user that the task was updated.
	        MyInfoPresenter.this.notify("MyInfo Cancel");
	        //System.out.println("cancel22222222222########################################");	        
	        // Return to the task list.
	        // history를 남기지 않기 위해 이벤트를 사용
	        ActionEvent.fire(eventBus, ActionNames.MYINFO_SAVED);
	        //System.out.println("cancel33333333333########################################");
  }
  
  
  /**
   * Notify the user of a message.
   * 
   * @param message the message to display
   */
  private void notify(String message) {
    // TODO Add notification pop-up
    log.fine("Tell the user: " + message);
  }

  
}
