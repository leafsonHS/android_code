package com.leafson.lifecycle.bean;

import com.leafson.lifecycle.db.Column;
import com.leafson.lifecycle.db.ID;
import com.leafson.lifecycle.db.TableName;
@TableName("TABLE_LINEOBJECTDETAIL")
public class LineObjectDetail {
	@ID(autoincrement = true)
	@Column("line_number")
	private String lineNumber;

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getObjText() {
		return ObjText;
	}

	public void setObjText(String objText) {
		ObjText = objText;
	}

	// 1：提前2站提醒 0 不提醒
	@Column("setting_noticeType")
	private String ObjText;

	

}
