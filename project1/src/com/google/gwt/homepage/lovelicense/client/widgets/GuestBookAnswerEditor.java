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
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.homepage.lovelicense.shared.GuestBookAnswerTableProxy;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Allows an AddressProxy to be edited.
 */
public class GuestBookAnswerEditor extends Composite implements Editor<GuestBookAnswerTableProxy> {
  interface Binder extends UiBinder<Widget, GuestBookAnswerEditor> {
  }

  @UiField
  Label email;
  @UiField
  Label reg_dt_yyyymmdd;
  @UiField
  Label contents;


  public GuestBookAnswerEditor() {
    initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));
  }
  
  public void init(){
	  email.setText("");
	  reg_dt_yyyymmdd.setText("");
	  contents.setText("");
	 
  }
  
}
