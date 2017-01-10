package com.leafson.lifecycle.nanjing.bean;

import com.amap.api.maps2d.model.LatLng;

public class LinesBusBean extends BusBaseBean
{
  private static final long serialVersionUID = 1L;
  public double P;
  public double Q;
  public String R;
  public String S;
  public LatLng T;
  public int U;
  public int V;
  public int W;
  public String X;
  public String start_station;

  public final LatLng j()
  {
    if ((this.T == null) && (0.0D != this.Q) && (0.0D != this.P))
      this.T = new LatLng(this.Q, this.P);
    return this.T;
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.model.LinesBusBean
 * JD-Core Version:    0.5.4
 */