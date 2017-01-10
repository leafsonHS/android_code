package com.leafson.lifecycle.nanjing.bean;

import com.leafson.lifecycle.db.Column;
import com.leafson.lifecycle.db.TableName;

@TableName("TABLE_LINEOBJ")
public class LineBean extends BusLinesBean
{
  private static final long serialVersionUID = 1L;
  public int N;
  @Column("weiba_id") 
  public String weiba_id;
  @Column("line_name")
  public String line_name;
  @Column("line_status")
  public String line_status;
  @Column("line_status") 
  public String updown_id;

  
  public String toString()
  {
    return this.line_name;
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.model.LineBean
 * JD-Core Version:    0.5.4
 */