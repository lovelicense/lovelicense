/*
 * Copyright 2010 Google Inc.
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
package com.google.gwt.homepage.lovelicense.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Edits People.
 */
public class GuestBookDetailEditor extends Composite implements Editor<GuestBookTableProxy> {
  interface Binder extends UiBinder<Widget, GuestBookDetailEditor> {
  }

  @UiField
  Label subject;
  
  @UiField
  NumberLabel<Integer> cnt;

  @UiField
  Label email;
  
  @UiField
  Label reg_dt_yyyymmdd;
  
  @UiField
  Label contents;
  
  @UiField
  GuestBookAnswerEditor answer;

  @UiField
  @Ignore
  Label noAnswer;
  
  


  public GuestBookDetailEditor() {
    
    initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));
    noAnswer.setText("답변이 없습니다.");
    answer.setVisible(false);//기본은 답변이 않보이게
    noAnswer.setVisible(true);
  }
  /*
  public void focus() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      public void execute() {
        nameBox.setFocus(true);
      }
    });
  }
  */
  
  public void setAnswerVisible(boolean answerExist){
	  if(answerExist){
		  answer.setVisible(true);//기본은 답변이 보이게
		  noAnswer.setVisible(false);
	  }else{
		  answer.setVisible(false);//기본은 답변이 않보이게
		  noAnswer.setVisible(true);
	  }
  }
  
  public void init(){
	  subject.setText("");
	  cnt.setValue(0);
	  email.setText("");
	  reg_dt_yyyymmdd.setText("");
	  contents.setText("");
	  answer.init();
  }
}