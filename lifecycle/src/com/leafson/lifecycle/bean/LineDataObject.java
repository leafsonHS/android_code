package com.leafson.lifecycle.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LineDataObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lineCode;
	private String lineName;


	private Map<String, Map<String, Object>> staticStations;
	private List<Map<String, Object>> upLineList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> downLineList = new ArrayList<Map<String, Object>>();
	private String upLine = null;
	private String downLine = null;
	
	private String downtime;
	private String uptime;
	
	public String getDowntime() {
		return downtime;
	}

	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Map<String, Map<String, Object>> getStaticStations() {
		return staticStations;
	}

	public void setStaticStations(
			Map<String, Map<String, Object>> staticStations) {
		this.staticStations = staticStations;
	}

	public List<Map<String, Object>> getUpLineList() {
		return upLineList;
	}

	public void setUpLineList(List<Map<String, Object>> upLineList) {
		this.upLineList = upLineList;
	}

	public List<Map<String, Object>> getDownLineList() {
		return downLineList;
	}

	public void setDownLineList(List<Map<String, Object>> downLineList) {
		this.downLineList = downLineList;
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
}
