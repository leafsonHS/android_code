package com.leafson.lifecycle.nanjing.bean;

import java.util.ArrayList;
import java.util.List;

import com.leafson.lifecycle.db.Column;

public class BusLinesBean extends LinesBusBean
{
  private static final long serialVersionUID = 1L;
  public String c;
  public String d;
  public String end_station;
  public String f;
  public String g;
  public String i;
  public String j;
  public String k;
  @Column("line_code")
  public String line_code;
  @Column("line_id")
  public String line_id;
  @Column("ltd")
  public String ltd;
  public String m;
  public String o;
  public int q;
  public List r = new ArrayList();
  public List s = new ArrayList();
  public ArrayList t = new ArrayList();
  public String updown_type = "0";

  public final void a(String paramString)
  {
    this.m = paramString;
  }

  public void f(String paramString)
  {
    this.line_id = paramString;
  }

  public void g(String paramString)
  {
    this.c = paramString;
  }

  public String h()
  {
    return this.line_id;
  }

  public String i()
  {
    return this.c;
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.model.BusLinesBean
 * JD-Core Version:    0.5.4
 */