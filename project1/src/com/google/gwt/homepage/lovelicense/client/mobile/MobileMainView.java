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
import com.google.gwt.homepage.lovelicense.client.presenter.MainView;
import com.google.gwt.homepage.lovelicense.client.presenter.MainView.CwConstants;
//import com.google.gwt.sample.mobilewebapp.shared.TaskProxy;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.resources.client.ClientBundle.Source;
//import com.google.gwt.sample.mobilewebapp.client.desktop.MainMenuItem;
//import com.google.gwt.sample.mobilewebapp.client.desktop.MobileWebAppShellDesktop.MainMenuStyle;
//import com.google.gwt.sample.mobilewebapp.client.desktop.MobileWebAppShellDesktop.Resources;
import com.google.gwt.homepage.lovelicense.client.place.TaskCurrentSajuPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskGuestBookListPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskMyInfoPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskSajuViewPlace;
import com.google.gwt.homepage.lovelicense.client.place.TaskSearchFriendPlace;
import com.google.gwt.i18n.client.Constants;
//import com.google.gwt.sample.mobilewebapp.presenter.tasklist.TaskListPlace;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.event.shared.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * View used to display the list of Tasks.
 */
public class MobileMainView extends Composite implements MainView {
     
	
	private CwConstants constants;
	
	/**
   * Resources used by the mobile CellList.
   */
	/*
  interface CellListResources extends CellList.Resources {
    @Source({CellList.Style.DEFAULT_CSS, "MobileCellList.css"})
    CellListStyle cellListStyle();
  }
  */

  /**
   * Styles used by the mobile CellList.
   */
	/*
  interface CellListStyle extends CellList.Style {
  }
  */
  
  
  /**
   * CSS override used for the main menu.
   */
  interface MainMenuStyle extends CellList.Style {
  }
  
  /**
   * A ClientBundle that provides images for this widget.
   */
  interface Resources extends CellList.Resources {
    /**
     * The styles used in the main menu. We extend
     * {@link CellList.Style#DEFAULT_CSS} with the styles defined in
     * MainMenuCellList.css.
     */
    @Source({"MainMenuCellList.css", CellList.Style.DEFAULT_CSS})
    MainMenuStyle cellListStyle();
  }
 

  /**
   * The UiBinder interface.
   */
  interface MobileMainViewUiBinder extends UiBinder<Widget, MobileMainView> {
  }

  /**
   * The UiBinder used to generate the view.
   */
  private static MobileMainViewUiBinder uiBinder = GWT.create(MobileMainViewUiBinder.class);

  /**
   * The main menu list.
   */
  @UiField(provided = true)
  CellList<MainMenuItem> mainMenu;

  /**
   * The presenter for this view.
   */
  private Presenter presenter;

  /**
   * Construct a new {@link MobileTaskListView}.
   */
  public MobileMainView(EventBus bus,final PlaceController placeController,CwConstants constants) {

    // Create the CellList.
	  /*
    CellListResources cellListRes = GWT.create(CellListResources.class);
    taskList = new CellList<S>(new TaskProxyCell(), cellListRes);
    taskList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
*/
    /*
     * Inform the presenter when the user selects a task from the task list. We
     * use a NoSelectionModel because we don't want the task to remain selected,
     * we just want to be notified of the selection event.
     */
	  /*
    final NoSelectionModel<TaskProxy> selectionModel = new NoSelectionModel<TaskProxy>();
    taskList.setSelectionModel(selectionModel);
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        // Edit the task.
        if (presenter != null) {
          presenter.selectTask(selectionModel.getLastSelectedObject());
        }
      }
    });
*/
	  this.constants=constants;
    
    // Initialize the main menu.
    Resources resources = GWT.create(Resources.class);
    mainMenu = new CellList<MainMenuItem>(new MainMenuItem.Cell(), resources);
    mainMenu.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);

    // We don't expect to have more than 30 menu items.
    mainMenu.setVisibleRange(0, 30);

    // Add items to the main menu.
    final List<MainMenuItem> menuItems = new ArrayList<MainMenuItem>();
    menuItems.add(new MainMenuItem(constants.mainMenuNameInspectSaju(), new TaskSajuViewPlace()));
    menuItems.add(new MainMenuItem(constants.mainMenuNameCurrentSaju(), new TaskCurrentSajuPlace()));
    menuItems.add(new MainMenuItem(constants.mainMenuNameSearchFriend(), new TaskSearchFriendPlace()));
    menuItems.add(new MainMenuItem(constants.mainMenuNameMyInfo(), new TaskMyInfoPlace()));
    menuItems.add(new MainMenuItem(constants.mainMenuNameVisitBbs(), new TaskGuestBookListPlace(true)));
    mainMenu.setRowData(menuItems);

    // Choose a place when a menu item is selected.
    final SingleSelectionModel<MainMenuItem> selectionModel =
        new SingleSelectionModel<MainMenuItem>();
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
    	  
        MainMenuItem selected = selectionModel.getSelectedObject();
        if (selected != null && !selected.mapsToPlace(placeController.getWhere())) {
        	try{
          placeController.goTo(selected.getPlace());
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
        }
      }
    });
    mainMenu.setSelectionModel(selectionModel);

    // Update selection based on the current place.
    bus.addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
      public void onPlaceChange(PlaceChangeEvent event) {
    	  
        Place place = event.getNewPlace();
        for (MainMenuItem menuItem : menuItems) {

          if (menuItem.mapsToPlace(place)) {
            // We found a match in the main menu.

            selectionModel.setSelected(menuItem, true);
            return;
          }
        }

        // We didn't find a match in the main menu.
        selectionModel.setSelected(null, true);
      }
    });
    
    // Initialize the widget.
    initWidget(uiBinder.createAndBindUi(this));
  }

 // public void clearList() {
   // taskList.setVisibleRangeAndClearData(taskList.getVisibleRange(), true);
 // }

  public void setPresenter(Presenter presenter) {
    if (this.presenter != null) {
      this.presenter.stop();
    }
    this.presenter = presenter;
  }

 // public void setTasks(List<TaskProxy> tasks) {
   // taskList.setRowData(tasks);
 // }
}
