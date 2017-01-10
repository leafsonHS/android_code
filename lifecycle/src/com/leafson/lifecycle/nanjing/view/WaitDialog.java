package com.leafson.lifecycle.nanjing.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WaitDialog
{
  public static void SetLoadingDialogTip(Dialog paramDialog, String paramString)
  {
    TextView localTextView = (TextView)paramDialog.findViewById(2131034189);
    if (localTextView == null)
      return;
    localTextView.setText(paramString);
  }

  public static Dialog createLoadingDialog(Context paramContext, String paramString)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903072, null);
    ((TextView)localView.findViewById(2131034189)).setText(paramString);
    Dialog localDialog = new Dialog(paramContext, 2131296257);
    localDialog.setCancelable(false);
    localDialog.setContentView(localView, new LinearLayout.LayoutParams(-1, -1));
    return localDialog;
  }
}

/* Location:           C:\Users\yeguanwen\Desktop\dec\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     cn.nj.busservice.view.WaitDialog
 * JD-Core Version:    0.5.4
 */