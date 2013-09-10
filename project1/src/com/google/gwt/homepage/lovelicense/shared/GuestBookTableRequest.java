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


import com.google.gwt.homepage.lovelicense.server.GuestBookTable;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


import java.util.List;

/**
 * Remote request for {@link Task}.
 */
@Service(GuestBookTable.class)
public interface GuestBookTableRequest extends RequestContext {

  
  Request<List<GuestBookTableProxy>> findAllGuestBooks();
  
   //Request<GuestBookTableProxy> findGuestBookTable(Long id);
  Request<GuestBookTableProxy> findGuestBookTable(String strKey);
  Request<Void> updateCnt(String id);
  
   
  InstanceRequest<GuestBookTableProxy, Void> persistEdit();//수정
  InstanceRequest<GuestBookTableProxy, Void> persistCreate();//신규등록
  InstanceRequest<GuestBookTableProxy, Void> remove();
   
}
