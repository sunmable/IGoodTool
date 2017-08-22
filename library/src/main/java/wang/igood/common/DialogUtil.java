package cn.chaboshi.cbslibrary.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import cn.chaboshi.cbslibrary.R;

/**
 * Created by testcbs on 2017/6/4.
 */

public class DialogUtil {


    /**
     * <a>1.1:等待对话框</a>
     * **/
    public static Dialog loadingDialog(Context context){
        Dialog dialog = null;
        if (dialog == null) {
            dialog = new Dialog(context, R.style.LoaddingDialog);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loadding, null);
        ImageView gifView = (ImageView) view.findViewById(R.id.dialog_loadding_gifview);
        try{
            gifView.setBackgroundResource(R.drawable.dialog_loadding_anim);
            AnimationDrawable anim = (AnimationDrawable) gifView.getBackground();
            anim.start();
        }catch (Exception e){
            gifView.setBackgroundResource(R.drawable.icon_loadding_1);
        }

        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;
    }
}
