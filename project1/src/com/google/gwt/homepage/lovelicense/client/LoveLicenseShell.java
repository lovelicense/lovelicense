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
package com.google.gwt.homepage.lovelicense.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * An interface that describes the UI Shell.
 */
public interface LoveLicenseShell extends AcceptsOneWidget, IsWidget {
  /**
   * Show or hide the Add button, which must post an
   * {@link com.google.gwt.sample.mobilewebapp.client.event.ActionEvent
   * ActionEvent} with a source name of
   * {@link com.google.gwt.sample.mobilewebapp.client.event.ActionNames#ADD_TASK
   * ActionEventNames.ADD_TASK} when clicked.
   * <p>
   * TODO: this is ridiculous, and is in the process of being changed to
   * something much simpler
   * 
   * @param visible to show the button, false to hide it
   */
  //void setAddButtonVisible(boolean visible);
}