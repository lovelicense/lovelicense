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
package com.google.gwt.homepage.lovelicense.shared;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.homepage.lovelicense.server.GuestBookAnswerTable;
import com.google.gwt.homepage.lovelicense.server.GuestBookTable;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import java.util.Date;

/**
 * A task used in the task list.
 */
@ProxyFor(GuestBookTable.class)
public interface GuestBookTableProxy extends EntityProxy {

	
	public String getId();
	
	public GuestBookAnswerTableProxy getAnswer();

	

	public Date getReg_dt();
	public Date getModi_dt();
	
	public String getReg_dt_yyyymmdd();
	public String getModi_dt_yyyymmdd();
	
	public void setModi_dt_yyyymmdd(String yyyymmdd);
	public void setReg_dt_yyyymmdd(String yyyymmdd);
	
	public String getSubject();


	public String getEmail();

	public String getContents();

	public int getCnt();
	
	public boolean getIsSelf();
	
	public void setSubject(String subject);
	public void setContents(String contents);
	public void setAnswer(GuestBookAnswerTableProxy answer);
	
	
}
