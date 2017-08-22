package cn.chaboshi.cbslibrary.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by testcbs on 2017/5/24.
 */

public class DebugUtil {

    private static boolean DEBUG = true;

    public static void Tost(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void DebugTost(String message, Context context){
        if(DEBUG)
            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public  static void Log(String message){
        if(DEBUG)
            Log.i("CBS",message);
    }

    public static void setDEBUG(boolean DEBUG) {
        DebugUtil.DEBUG = DEBUG;
    }
}
