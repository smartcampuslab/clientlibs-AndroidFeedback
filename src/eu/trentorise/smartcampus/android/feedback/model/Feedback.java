/*******************************************************************************
 * Copyright 2012-2013 Trento RISE
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either   express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package eu.trentorise.smartcampus.android.feedback.model;

/**
 * Feedback data bean
 * @author raman
 *
 */
public class Feedback {
	// id of the user who created this record, filled in server-side
	private String creatorId;
	// notes
	private String note;
	// type (e.g., bug, problem, etc.)
	private String type;
	// task difficulty parameter
//	private Integer difficulty;
//	// id of the application where the feedback is created
	private String appId;
	// id of the user task (corresponds to the fragment/form where the feedback is called)
	private String activityId;
	// time when the record is created, filled in server-side
	private Long reportTime;
	// id of the screenshot file attached to the feedback, filled in server-side
	private String fileId;
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
//	public Integer getDifficulty() {
//		return difficulty;
//	}
//	public void setDifficulty(Integer difficulty) {
//		this.difficulty = difficulty;
//	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public Long getReportTime() {
		return reportTime;
	}
	public void setReportTime(Long reportTime) {
		this.reportTime = reportTime;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	
}
