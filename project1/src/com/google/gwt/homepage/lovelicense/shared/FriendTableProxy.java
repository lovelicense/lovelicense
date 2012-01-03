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

import com.google.gwt.homepage.lovelicense.server.FriendTable;
import com.google.gwt.homepage.lovelicense.server.SajuDataTable;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

import java.util.Date;

/**
 * A task used in the task list.
 */
@ProxyFor(FriendTable.class)
public interface FriendTableProxy extends ValueProxy {

	public String getI_email();

	public void setI_email(String i_email);

	public String getFriend_email();

	public void setFriend_email(String friend_email);

	public Integer getScore();

	public void setScore(Integer score);




	public String getAddr1();




	public void setAddr1(String addr1);




	public String getSex();




	public void setSex(String sex);




	public String getJob();




	public void setJob(String job);
	
}
