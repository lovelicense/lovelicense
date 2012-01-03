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
package com.google.gwt.homepage.lovelicense.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
//import com.google.gwt.sample.mobilewebapp.shared.TaskProxy;

/**
 * The place in the app that show a task in an editable view.
 */
public class TaskMyInfoPlace extends Place {

  /**
   * The tokenizer for this place.
   */
  
  public static class Tokenizer implements PlaceTokenizer<TaskMyInfoPlace> {

   

    public TaskMyInfoPlace getPlace(String token) {
        return new TaskMyInfoPlace();
    }

    public String getToken(TaskMyInfoPlace place) {
      
      return "";
    }
  }

 
  
  public TaskMyInfoPlace() {
	  }
  
  
  /**
   * Create an instance of {@link TaskEditPlace} associated with the specified
   * task ID.
   * 
   * @param taskId the ID of the task to edit
   * @param task the task to edit, or null if not available
   * @return the place
   */


  /**
   * Get the singleton instance of the {@link TaskEditPlace} used to create a
   * new task.
   * 
   * @return the place
   */


  /**
   * Construct a new {@link TaskEditPlace} for the specified task id.
   * 
   * @param taskId the ID of the task to edit
   * @param task the task to edit, or null if not available
   */

  


  /**
   * Get the task to edit.
   * 
   * @return the task to edit, or null if not available
   */

  /**
   * Get the ID of the task to edit.
   * 
   * @return the ID of the task, or null if creating a new task
   */

}
