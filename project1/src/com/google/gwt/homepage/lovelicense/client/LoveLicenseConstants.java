/*
 * Copyright 2008 Google Inc.
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

import com.google.gwt.i18n.client.Constants;

import com.google.gwt.homepage.lovelicense.client.presenter.CurrentSajuView;

import com.google.gwt.homepage.lovelicense.client.presenter.MainView;
import com.google.gwt.homepage.lovelicense.client.presenter.MyInfoView;
import com.google.gwt.homepage.lovelicense.client.presenter.SajuViewView;
import com.google.gwt.homepage.lovelicense.client.presenter.SearchFriendView;


//import com.google.gwt.homepage.smile.shared.EnneagramQuestions;

/**
 * Constants used throughout the showcase.
 */
public interface LoveLicenseConstants extends Constants, MainView.CwConstants, MyInfoView.CwConstants, SajuViewView.CwConstants, CurrentSajuView.CwConstants, SearchFriendView.CwConstants{

  /**
   * The path to source code for examples, raw files, and style definitions.
   */
  String DST_SOURCE = "gwtShowcaseSource/";

  /**
   * The destination folder for parsed source code from Showcase examples.
   */
  String DST_SOURCE_EXAMPLE = DST_SOURCE + "java/";

  /**
   * The destination folder for raw files that are included in entirety.
   */
  String DST_SOURCE_RAW = DST_SOURCE + "raw/";

  /**
   * The destination folder for parsed CSS styles used in Showcase examples.
   */
  String DST_SOURCE_STYLE = DST_SOURCE + "css/";

  /**
   * Link to GWT homepage.
   */
  String GWT_HOMEPAGE = "http://code.google.com/webtoolkit/";

  /**
   * Link to GWT examples page.
   */
  String GWT_EXAMPLES = GWT_HOMEPAGE + "examples/";

  /**
   * The available style themes that the user can select.
   */
  String[] STYLE_THEMES = {"standard", "chrome", "dark"};

  String categoryI18N();

  String categoryLists();

  String categoryOther();

  String categoryPanels();

  String categoryPopups();

  String categoryTables();

  String categoryTextInput();

  String categoryWidgets();

  /**
   * @return text for the link to more examples
   */
  String mainLinkExamples();
  String mainLinkExamplesUrl();
  

  /**
   * @return text for the link to the GWT homepage
   */
  String mainLinkHomepage();
  String mainLinkHomepageUrl();

  /**
   * @return the title of the main menu
   */
  String mainMenuTitle();

  /**
   * @return the sub title of the application
   */
  String mainSubTitle();

  /**
   * @return the title of the application
   */
  String mainTitle();
  
  
  String menu_1();
  String menu_1_1();
  String menu_1_2();
  String menu_1_3();
  String menu_2();
  String menu_2_1();
  String menu_2_2();
  String menu_2_3();
  String menu_3();
  String menu_3_1();
  String menu_3_2();
  String menu_3_3();
  
  
  String[] addrSido();//시도
  
  String[] sexDatas();//남여
  String[] jobs();//직업 
  
  

  
  
  
}
