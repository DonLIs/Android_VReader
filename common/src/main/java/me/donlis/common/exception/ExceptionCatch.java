package me.donlis.common.exception;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import androidx.annotation.NonNull;

public class ExceptionCatch implements Thread.UncaughtExceptionHandler{

    private Context mContext;

    private ExceptionCatch(){

    }

    private static class ExceptionCatchHolder{
        private static final ExceptionCatch instance = new ExceptionCatch();
    }

    public static ExceptionCatch getInstance(){
        return ExceptionCatchHolder.instance;
    }

    public void init(Context context){
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        saveToSdcard(throwable);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "System error", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();

        throwable.printStackTrace();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void setCatchInfo(){
        if(mContext == null){
            throw new NullPointerException("should init first");
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private void saveToSdcard(Throwable ex) {
        String fileName = null;
        StringBuffer sBuffer = new StringBuffer();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String message = Arrays.toString(ex.getStackTrace());
        message = message.replace(",",",\n");

        // 添加异常信息
        sBuffer.append(df.format(new Date()));
        sBuffer.append(":");
        sBuffer.append(ex.toString());
        sBuffer.append("\n");
        sBuffer.append(message);
        sBuffer.append("\n\r");

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ){
            fileName = Environment.getExternalStorageDirectory().getPath() + "/me.donlis.vreader/";
        }else{
            fileName = mContext.getFilesDir()+ File.separator;
        }
        File dir = new File(fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(fileName,"log.txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file,true);
            fos.write(sBuffer.toString().getBytes());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
