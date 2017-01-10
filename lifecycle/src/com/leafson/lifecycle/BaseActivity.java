package com.leafson.lifecycle;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leafson.lifecycle.nanjing.http.BaseReqest;
import com.leafson.lifecycle.nanjing.http.BaseResponse;
import com.leafson.lifecycle.nanjing.http.HttpUtil;
import com.leafson.lifecycle.nanjing.view.WaitDialog;
import com.leafson.lifecycle.utils.ToastUtil;
import com.leafson.lifecycle.utils.Utils;

public  class BaseActivity extends Activity
{
  public ImageView backImage;
  private long exitTime = 0L;
  private Handler mHandler;
  public Dialog mProgressBar = null;
  protected ImageView right_menu;
  public TextView titleText;




//  public void QuitAppDialog()
//  {
//    ExitApp();
//  }


//
//  public void finish()
//  {
//    if (getCurrentFocus() != null)
//      ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
//    ActivityManager.getScreenManager().popActivityOut(this);
//    super.finish();
//  }

  public Handler getmHandler()
  {
    return this.mHandler;
  }

  public void hideProgressBar()
  {
    try
    {
      if (this.mProgressBar == null)
        return;
      if (!this.mProgressBar.isShowing())
        return;
      this.mProgressBar.dismiss();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void initButtonCallBack()
  {
    View localView = findViewById(2131034122);
    if (localView == null)
      return;
    localView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        BaseActivity.this.finish();
      }
    });
  }


  public boolean isProgressBarShown()
  {
    return (this.mProgressBar != null) && (this.mProgressBar.isShowing());
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
//    initLayout();
//    findAllButton();
//    initButtonCallBack();
//    initHandler();
 
  }

  protected void onDestroy()
  {
    hideProgressBar();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onResume()
  {
    super.onResume();
  }

  protected void onStart()
  {
    super.onStart();
  }

  public void sendHttpMsg(BaseReqest paramBaseReqest, BaseResponse paramBaseResponse)
  {
    HttpUtil.sendHttp(paramBaseReqest, paramBaseResponse, getmHandler());
  }

  public void setTitle(String paramString)
  {
    if (this.titleText == null)
      return;
    this.titleText.setText(paramString);
  }

  public void showProgressBar(String paramString)
  {
    if (this.mProgressBar == null)
    {
      this.mProgressBar = WaitDialog.createLoadingDialog(this, "正在加载");
      this.mProgressBar.setCanceledOnTouchOutside(false);
    }
    if (Utils.isNotEmpty(paramString))
      WaitDialog.SetLoadingDialogTip(this.mProgressBar, paramString);
    if ((this.mProgressBar.isShowing()) || (isFinishing()))
      return;
    this.mProgressBar.show();
  }

  public void showToast(String paramString)
  {
    ToastUtil.show(this, paramString);
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.activity.BaseActivity
 * JD-Core Version:    0.5.4
 */