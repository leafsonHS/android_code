package com.leafson.fuzhou.version;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.leafson.fuzhou.R;

public class UpdateManager 
{ 
    /* ������ */ 
    private static final int DOWNLOAD = 1; 
    /* ���ؽ��� */ 
    private static final int DOWNLOAD_FINISH = 2; 
    /* ���������XML��Ϣ */ 
    HashMap<String, String> mHashMap; 
    /* ���ر���·�� */ 
    private String mSavePath; 
    /* ��¼���������� */ 
    private int progress; 
    /* �Ƿ�ȡ������ */ 
    private boolean cancelUpdate = false; 
 
    private Context mContext; 
    /* ���½����� */ 
    private ProgressBar mProgress; 
    private Dialog mDownloadDialog; 
 
    private Handler mHandler = new Handler() 
    { 
        public void handleMessage(Message msg) 
        { 
            switch (msg.what) 
            { 
            // �������� 
            case DOWNLOAD: 
                // ���ý�����λ�� 
                mProgress.setProgress(progress); 
                break; 
            case DOWNLOAD_FINISH: 
                // ��װ�ļ� 
                installApk(); 
                break; 
            default: 
                break; 
            } 
        }; 
    }; 
 
    public UpdateManager(Context context) 
    { 
        this.mContext = context; 
    } 
 
    /**
     * �����������
     */ 
    public void checkUpdate() 
    { 
    	
    	new Checker().start();
//        if (isUpdate()) 
//        { 
//            // ��ʾ��ʾ�Ի��� 
//            showNoticeDialog(); 
//        } else 
//        { 
//            Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show(); 
//        } 
    } 
 
    /**
     * �����ļ��߳�
     */ 
    private class Checker extends Thread 
    { 
        @Override 
        public void run() 
        {
        		Looper.prepare();
        	  if (isUpdate()) 
              { 
                  // ��ʾ��ʾ�Ի��� 
                  showNoticeDialog(); 
              } else 
              { 
                  Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show(); 
              } 
        } 
    }; 
    
    /**
     * ��������Ƿ��и��°汾
     * 
     * @return
     */ 
    private boolean isUpdate() 
    { 
        // ��ȡ��ǰ�����汾 
        int versionCode = getVersionCode(mContext); 
        // ��version.xml�ŵ������ϣ�Ȼ���ȡ�ļ���Ϣ 
        //InputStream inStream = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml"); 
        // ����XML�ļ��� ����XML�ļ��Ƚ�С�����ʹ��DOM��ʽ���н��� 
        ParseXmlService service = new ParseXmlService(); 
        try 
        { 
            String path = "http://192.168.6.207:6030/version.xml";   //��ַ�Ƿ�������version.xml���ӵ�ַ 
            URL url = new URL(path); 
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
            conn.setReadTimeout(5*1000); 
            conn.setRequestMethod("GET"); 
            InputStream inStream = conn.getInputStream(); 
            mHashMap = service.parseXml(inStream); 
        } catch (Exception e) 
        { 
            e.printStackTrace(); 
        } 
        if (null != mHashMap) 
        { 
            int serviceCode = Integer.valueOf(mHashMap.get("version")); 
            // �汾�ж� 
            if (serviceCode > versionCode) 
            { 
                return true; 
            } 
        } 
        return false; 
    } 
    
    

    
 
    /**
     * ��ȡ�����汾��
     * 
     * @param context
     * @return
     */ 
    private int getVersionCode(Context context) 
    { 
        int versionCode = 0; 
        try 
        { 
            // ��ȡ�����汾�ţ���ӦAndroidManifest.xml��android:versionCode 
            versionCode = context.getPackageManager().getPackageInfo("com.leafson.lifecycle", 0).versionCode; 
        } catch (NameNotFoundException e) 
        { 
            e.printStackTrace(); 
        } 
        return versionCode; 
    } 
 
    /**
     * ��ʾ�������¶Ի���
     */ 
    private void showNoticeDialog() 
    { 
    	
    
        // ����Ի��� 
        AlertDialog.Builder builder = new Builder(mContext); 
        builder.setTitle(R.string.soft_update_title); 
        builder.setMessage(R.string.soft_update_info); 
        // ���� 
        builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener() 
        { 
            @Override 
            public void onClick(DialogInterface dialog, int which) 
            { 
                dialog.dismiss(); 
                // ��ʾ���ضԻ��� 
                showDownloadDialog(); 
            } 
        }); 
        // �Ժ���� 
        builder.setNegativeButton(R.string.soft_update_later, new OnClickListener() 
        { 
            @Override 
            public void onClick(DialogInterface dialog, int which) 
            { 
                dialog.dismiss(); 
            } 
        }); 
      
        try{
        Dialog noticeDialog = builder.create(); 
      
        noticeDialog.show();
        Looper.loop();
        }catch(Exception e)
        {
        	e.printStackTrace();
        	
        }
        } 
 
    /**
     * ��ʾ�������ضԻ���
     */ 
    private void showDownloadDialog() 
    { 
    	
        // �����������ضԻ��� 
        AlertDialog.Builder builder = new Builder(mContext); 
        builder.setTitle(R.string.soft_updating); 
        // �����ضԻ������ӽ����� 
        final LayoutInflater inflater = LayoutInflater.from(mContext); 
        View v = inflater.inflate(R.layout.softupdate_progress, null); 
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress); 
        builder.setView(v); 
        // ȡ������ 
        builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener() 
        { 
            @Override 
            public void onClick(DialogInterface dialog, int which) 
            { 
                dialog.dismiss(); 
                // ����ȡ��״̬ 
                cancelUpdate = true; 
            } 
        }); 
        mDownloadDialog = builder.create(); 
        mDownloadDialog.show(); 
      
        // �����ļ� 
        downloadApk(); 
       // Looper.myLooper().quit();
        Looper.loop();
        
    } 
 

    
    /**
     * ����apk�ļ�
     */ 
    private void downloadApk() 
    { 
        // �������߳��������� 
         downloadApkThread(); 
    } 
 
  
        public void downloadApkThread() 
        { 
            try 
            { 
                // �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ�� 
              // if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
               // { 
                    // ��ô洢����·�� 
                    String sdpath = Environment.getExternalStorageState() + "/"; 
                    mSavePath = sdpath + "download"; 
                    URL url = new URL(mHashMap.get("url")); 
                    // �������� 
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
                    conn.connect(); 
                    // ��ȡ�ļ���С 
                    int length = conn.getContentLength(); 
                    // ���������� 
                    InputStream is = conn.getInputStream(); 
 
                    File file = new File(mSavePath); 
                    // �ж��ļ�Ŀ¼�Ƿ���� 
                    if (!file.exists()) 
                    { 
                        file.mkdir(); 
                    } 
                    File apkFile = new File(mSavePath, mHashMap.get("name")); 
                    FileOutputStream fos = new FileOutputStream(apkFile); 
                    int count = 0; 
                    // ���� 
                    byte buf[] = new byte[1024]; 
                    // д�뵽�ļ��� 
                    do 
                    { 
                        int numread = is.read(buf); 
                        count += numread; 
                        // ���������λ�� 
                        progress = (int) (((float) count / length) * 100); 
                        // ���½��� 
                        mHandler.sendEmptyMessage(DOWNLOAD); 
                        if (numread <= 0) 
                        { 
                            // ������� 
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH); 
                            break; 
                        } 
                        // д���ļ� 
                        fos.write(buf, 0, numread); 
                    } while (!cancelUpdate);// ���ȡ����ֹͣ����. 
                    fos.close(); 
                    is.close(); 
           //  } 
        
            } catch (Exception e) 
            { 
                e.printStackTrace(); 
            } 
            // ȡ�����ضԻ�����ʾ 
            mDownloadDialog.dismiss(); 
            Looper.loop();
        } 
 
    /**
     * ��װAPK�ļ�
     */ 
    private void installApk() 
    { 
        File apkfile = new File(mSavePath, mHashMap.get("name")); 
        if (!apkfile.exists()) 
        { 
            return; 
        } 
        // ͨ��Intent��װAPK�ļ� 
        Intent i = new Intent(Intent.ACTION_VIEW); 
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        mContext.startActivity(i); 
    } 
}