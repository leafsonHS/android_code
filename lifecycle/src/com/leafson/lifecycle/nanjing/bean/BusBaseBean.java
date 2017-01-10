package com.leafson.lifecycle.nanjing.bean;

import java.io.Serializable;

import com.leafson.lifecycle.db.Column;

public class BusBaseBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String distence;
  public int is_true;
  public String getDistence() {
	return distence;
}
public void setDistence(String distence) {
	this.distence = distence;
}
public int getIs_true() {
	return is_true;
}
public void setIs_true(int is_true) {
	this.is_true = is_true;
}
public String getLine_code() {
	return line_code;
}
public void setLine_code(String line_code) {
	this.line_code = line_code;
}
public String getLine_id() {
	return line_id;
}
public void setLine_id(String line_id) {
	this.line_id = line_id;
}
public String getLine_interval() {
	return line_interval;
}
public void setLine_interval(String line_interval) {
	this.line_interval = line_interval;
}
public String getLine_st_status() {
	return line_st_status;
}
public void setLine_st_status(String line_st_status) {
	this.line_st_status = line_st_status;
}
public String getLine_st_updated() {
	return line_st_updated;
}
public void setLine_st_updated(String line_st_updated) {
	this.line_st_updated = line_st_updated;
}
public String getLine_station_id() {
	return line_station_id;
}
public void setLine_station_id(String line_station_id) {
	this.line_station_id = line_station_id;
}
public String getSt_id() {
	return st_id;
}
public void setSt_id(String st_id) {
	this.st_id = st_id;
}
public String getSt_name() {
	return st_name;
}
public void setSt_name(String st_name) {
	this.st_name = st_name;
}
public double getSt_real_lat() {
	return st_real_lat;
}
public void setSt_real_lat(double st_real_lat) {
	this.st_real_lat = st_real_lat;
}
public double getSt_real_lon() {
	return st_real_lon;
}
public void setSt_real_lon(double st_real_lon) {
	this.st_real_lon = st_real_lon;
}
public int getSt_stop_level() {
	return st_stop_level;
}
public void setSt_stop_level(int st_stop_level) {
	this.st_stop_level = st_stop_level;
}
public String getUpdate_time() {
	return update_time;
}
public void setUpdate_time(String update_time) {
	this.update_time = update_time;
}
public String getUpdown_type() {
	return updown_type;
}
public void setUpdown_type(String updown_type) {
	this.updown_type = updown_type;
}
public String getWeiba_id() {
	return weiba_id;
}
public void setWeiba_id(String weiba_id) {
	this.weiba_id = weiba_id;
}
public String line_code;
  public String line_id;
  @Column("line_interval")
  public String line_interval;
  public String line_st_status;
  public String line_st_updated;
  public String line_station_id;
  public String st_id;
  public String st_name;
  public double st_real_lat;
  public double st_real_lon;
  public int st_stop_level;
  @Column("update_time")
  public String update_time;
  public String updown_type;
  public String weiba_id;
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.model.BusBaseBean
 * JD-Core Version:    0.5.4
 */