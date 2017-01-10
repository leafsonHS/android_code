package com.leafson.lifecycle.bean;

import com.leafson.lifecycle.db.Column;
import com.leafson.lifecycle.db.ID;
import com.leafson.lifecycle.db.TableName;
@TableName("TABLE_SETTING")
public class Setting {
	@ID(autoincrement = true)
	@Column("setting_id")
	private Long id;

	// 1：提前2站提醒 0 不提醒
	@Column("setting_noticeType")
	private String noticeType;
	@Column("setting_refreashInterval")
	private String refreashInterval;
	@Column("setting_defaultBusLine")
	private String defaultBusLine;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getRefreashInterval() {
		return refreashInterval;
	}

	public void setRefreashInterval(String refreashInterval) {
		this.refreashInterval = refreashInterval;
	}

	public String getDefaultBusLine() {
		return defaultBusLine;
	}

	public void setDefaultBusLine(String defaultBusLine) {
		this.defaultBusLine = defaultBusLine;
	}


}
