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
import com.google.gwt.homepage.lovelicense.shared.GuestBookTableProxy;

/**
 * The place in the app that show a task in an editable view.
 */
public class TaskGuestBookViewEditPlace extends Place {

  /**
   * The tokenizer for this place.
   */
  @Prefix("GuestBook")
  public static class Tokenizer implements PlaceTokenizer<TaskGuestBookViewEditPlace> {

    private static final String NO_ID = "create";

    public TaskGuestBookViewEditPlace getPlace(String token) {
      try {
        // Parse the task ID from the URL.
        //Long taskId = Long.parseLong(token);
    	  
        return new TaskGuestBookViewEditPlace(token, null);
      } catch (NumberFormatException e) {
        // If the ID cannot be parsed, assume we are creating a task.
        return TaskGuestBookViewEditPlace.getTaskCreatePlace();
      }
    }

    public String getToken(TaskGuestBookViewEditPlace place) {
      String taskId = place.getTaskId();
      return (taskId == null) ? NO_ID : taskId.toString();
    }
  }

  /**
   * The singleton instance of this place used for creation.
   */
  private static TaskGuestBookViewEditPlace singleton;

  /**
   * Create an instance of {@link TaskEditPlace} associated with the specified
   * task ID.
   * 
   * @param taskId the ID of the task to edit
   * @param task the task to edit, or null if not available
   * @return the place
   */
  public static TaskGuestBookViewEditPlace createTaskEditPlace(String taskId, GuestBookTableProxy task) {
    return new TaskGuestBookViewEditPlace(taskId, task);
  }

  /**
   * Get the singleton instance of the {@link TaskEditPlace} used to create a
   * new task.
   * 
   * @return the place
   */
  public static TaskGuestBookViewEditPlace getTaskCreatePlace() {
    if (singleton == null) {
      singleton = new TaskGuestBookViewEditPlace(null, null);
    }
    return singleton;
  }

  private final GuestBookTableProxy task;
  //private final Long taskId;
  private final String taskId;
  
  /**
   * Construct a new {@link TaskEditPlace} for the specified task id.
   * 
   * @param taskId the ID of the task to edit
   * @param task the task to edit, or null if not available
   */
  private TaskGuestBookViewEditPlace(String taskId, GuestBookTableProxy task) {
    this.taskId = taskId;
    this.task = task;
  }

  /**
   * Get the task to edit.
   * 
   * @return the task to edit, or null if not available
   */
  public GuestBookTableProxy getTask() {
    return task;
  }

  /**
   * Get the ID of the task to edit.
   * 
   * @return the ID of the task, or null if creating a new task
   */
  public String getTaskId() {
    return taskId;
  }
}
