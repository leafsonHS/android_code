package com.leafson.lifecycle.nanjing.http;

public abstract class BaseResponse
{
  public static final int Command_BusLineStationsUpdate_MsgNo = 32772;
  public static final int Command_BusLinesUpdate_MsgNo = 32769;
  public static final int Command_BusStationsUpdate_MsgNo = 32771;
  public static final int Command_BusUpdownsUpdate_MsgNo = 32770;
  public static final int Command_GetLine_MsgNo = 32773;
  protected int msgno;
  protected String resultcode = "";
  protected String resultmessage = "";

  public int getMsgno()
  {
    return this.msgno;
  }

  public abstract void init(String paramString);

  public boolean isOK()
  {
    return true;
  }

  public void setMsgno(int paramInt)
  {
    this.msgno = paramInt;
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.http.BaseResponse
 * JD-Core Version:    0.5.4
 */