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

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookListView;
import com.google.gwt.homepage.lovelicense.client.presenter.GuestBookListView.CwConstants;
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.widget.client.TextButton;
import com.google.web.bindery.event.shared.EventBus;

import java.util.Date;
import java.util.List;

/**
 * View used to display the list of Tasks.
 */
public class MobileGuestBookListView extends Composite implements GuestBookListView {
  
    
	private CwConstants constants;
	
	final private int pageSize=3;
   /**
   * The UiBinder interface.
   */
  interface MobileGuestBookListViewUiBinder extends UiBinder<Widget, MobileGuestBookListView> {
  }

  /**
   * The UiBinder used to generate the view.
   */
  private static MobileGuestBookListViewUiBinder uiBinder = GWT.create(MobileGuestBookListViewUiBinder.class);

  
  
  /**
   * The key provider that provides the unique ID of a contact.
   */
  public static final ProvidesKey<GuestBookTableProxy> KEY_PROVIDER = new ProvidesKey<GuestBookTableProxy>() {
    public Object getKey(GuestBookTableProxy item) {
      return item == null ? null : item.getId();
    }
  };
  
  
  
  
  /**
   * Displays the list of tasks.
   */
  
  /**
   * The title.
   */
  @UiField(provided = true)
  Label subTitle;
  
  @UiField(provided = true)
  CellTable<GuestBookTableProxy> guestBookList;
  
  /**
   * The pager used to change the range of data.
   */
  @UiField(provided = true)
  SimplePager pager;

  
  @UiField
  TextButton writeButton;
  
  /**
   * The presenter for this view.
   */
  private Presenter presenter;

  /**
   * Construct a new {@link MobileTaskListView}.
   */
  //public MobileGuestBookListView(EventBus bus,final PlaceController placeController,CwConstants constants) {
  public MobileGuestBookListView(CwConstants constants) {

	  this.constants=constants;
	  
    // Create the CellList.
    //CellListResources cellListRes = GWT.create(CellListResources.class);
	  
	  subTitle = new Label();
	  subTitle.setText(constants.mainMenuNameVisitBbs());
	
	    
    guestBookList = new CellTable<GuestBookTableProxy>(pageSize, KEY_PROVIDER);
    //guestBookList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);

    
    guestBookList.setWidth("100%", true);
    
    // Create a Pager to control the table.
    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
    pager.setDisplay(guestBookList);
    
    
    
    /*
     * Inform the presenter when the user selects a task from the task list. We
     * use a NoSelectionModel because we don't want the task to remain selected,
     * we just want to be notified of the selection event.
     */
    final NoSelectionModel<GuestBookTableProxy> selectionModel = new NoSelectionModel<GuestBookTableProxy>();
    guestBookList.setSelectionModel(selectionModel);
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        // select the list
        if (presenter != null) {
          presenter.selectTask(selectionModel.getLastSelectedObject());
        }
      }
    });

    
 // Initialize the columns.
    initTableColumns(selectionModel);
    
    
    // Initialize the widget.
    initWidget(uiBinder.createAndBindUi(this));
    
    
 
    writeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (presenter != null) {
          presenter.writeGuestBook();
        }
      }
    });
  }

  
  
  /**
   * Add the columns to the table.
   */
  private void initTableColumns(
      final NoSelectionModel<GuestBookTableProxy> selectionModel) {
   
    // 제목
    Column<GuestBookTableProxy, String> subjectColumn = new Column<GuestBookTableProxy, String>(
        new TextCell()) {
      @Override
      public String getValue(GuestBookTableProxy object) {
        return object.getSubject();
      }
    };
    //subjectColumn.setSortable(false);
    guestBookList.addColumn(subjectColumn, constants.guestBookListMenuSubject());
    guestBookList.setColumnWidth(subjectColumn, 40, Unit.PCT);
    
   
       // A custom date format
        DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy. MM. dd");
       
        
    
   // 글쓴날짜
    Column<GuestBookTableProxy, Date> dateColumn = new Column<GuestBookTableProxy, Date>(
        new DateCell(fmt)) {
      @Override
      public Date getValue(GuestBookTableProxy object) {
        return object.getReg_dt();
      }
    };
    //subjectColumn.setSortable(false);
    guestBookList.addColumn(dateColumn, constants.guestBookListMenuDate());
    guestBookList.setColumnWidth(dateColumn, 25, Unit.PCT);

    
    
    // 작성자
    Column<GuestBookTableProxy, String> writerColumn = new Column<GuestBookTableProxy, String>(
        new TextCell()) {
      @Override
      public String getValue(GuestBookTableProxy object) {
        return object.getEmail();
      }
    };
    //subjectColumn.setSortable(false);
    guestBookList.addColumn(writerColumn, constants.guestBookListMenuWriter());
    guestBookList.setColumnWidth(writerColumn, 25, Unit.PCT);
    
    
    // 조회수
    Column<GuestBookTableProxy, String> cntColumn = new Column<GuestBookTableProxy, String>(
        new TextCell()) {
      @Override
      public String getValue(GuestBookTableProxy object) {
        return Integer.toString(object.getCnt());
      }
    };
    //subjectColumn.setSortable(false);
    guestBookList.addColumn(cntColumn, constants.guestBookListMenuCnt());
    guestBookList.setColumnWidth(cntColumn, 10, Unit.PCT);
    
    
  }

  
  
  public void clearList() {
	  guestBookList.setVisibleRangeAndClearData(guestBookList.getVisibleRange(), true);
  }

  public void setPresenter(Presenter presenter) {
    if (this.presenter != null) {
      this.presenter.stop();
    }
    this.presenter = presenter;
  }

  public void setGuestBook(List<GuestBookTableProxy> tasks) {
	  guestBookList.setRowData(tasks);
  }
}
