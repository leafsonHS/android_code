package com.leafson.fuzhou.bean;

import com.leafson.fuzhou.db.Column;
import com.leafson.fuzhou.db.ID;
import com.leafson.fuzhou.db.TableName;
@TableName("TABLE_LINEOBJ")
public class LineObj {
	@Column("lineobj_linename")
	private String linename;
	@Column("lineobj_busLineId")
	private String busLineId;
	@Column("lineobj_upLine")
	private String upLine;
	@Column("lineobj_downLine")
	private String downLine;
	@Column("lineobj_upTime")
	private String upTime;
	@Column("lineobj_downTime")
	private String downTime;
	@Column("lineobj_linenamePinYin")
	private String linenamePinYin;

	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getBusLineId() {
		return busLineId;
	}
	public void setBusLineId(String busLineId) {
		this.busLineId = busLineId;
	}
	public String getUpLine() {
		return upLine;
	}
	public void setUpLine(String upLine) {
		this.upLine = upLine;
	}
	public String getDownLine() {
		return downLine;
	}
	public void setDownLine(String downLine) {
		this.downLine = downLine;
	}
	public String getUpTime() {
		return upTime;
	}
	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}
	public String getDownTime() {
		return downTime;
	}
	public void setDownTime(String downTime) {
		this.downTime = downTime;
	}
	public String getLinenamePinYin() {
		return linenamePinYin;
	}
	public void setLinenamePinYin(String linenamePinYin) {
		this.linenamePinYin = linenamePinYin;
	}

	
	

}
