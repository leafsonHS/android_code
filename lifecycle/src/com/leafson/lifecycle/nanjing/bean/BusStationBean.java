package com.leafson.lifecycle.nanjing.bean;

public class BusStationBean extends BusBaseBean
{
  public static final long serialVersionUID = 1L;
  public boolean isReady = false;
  public int line_distance;
  public String st_side;
  public String st_status;

  public boolean isReady() {
	return isReady;
}

public void setReady(boolean isReady) {
	this.isReady = isReady;
}

public int getLine_distance() {
	return line_distance;
}

public void setLine_distance(int line_distance) {
	this.line_distance = line_distance;
}

public String getSt_side() {
	return st_side;
}

public void setSt_side(String st_side) {
	this.st_side = st_side;
}

public String getSt_status() {
	return st_status;
}

public void setSt_status(String st_status) {
	this.st_status = st_status;
}

public String toString()
  {
    return this.st_name;
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.model.BusStationBean
 * JD-Core Version:    0.5.4
 */