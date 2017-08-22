package cn.chaboshi.cbslibrary.adapter.cell;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import cn.chaboshi.cbslibrary.activity.BaseActivity;
import cn.chaboshi.cbslibrary.domain.BaseDomain;
import cn.chaboshi.cbslibrary.handler.IDataChangeListener;

/**
 * Created by testcbs on 2017/5/24.
 */

public abstract  class BaseCell<T extends BaseDomain> extends LinearLayout{

    //1：变量声明区域----------------------------------------------------------------------------------------------
    //2：视图生命周期----------------------------------------------------------------------------------------------
    //3：视图逻辑-------------------------------------------------------------------------------------------------
    //4：业务逻辑-------------------------------------------------------------------------------------------------

    //1：变量声明区域----------------------------------------------------------------------------------------------
    //2：视图生命周期----------------------------------------------------------------------------------------------
    public BaseCell(Context context) {
        super(context);
    }

    public BaseCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseCell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseCell(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    //3：视图逻辑-------------------------------------------------------------------------------------------------
    public abstract void initView();
    public abstract void config();
    public abstract void resetCell(T t ,List<T> data,IDataChangeListener dataChangeListener);
    //4：业务逻辑-------------------------------------------------------------------------------------------------

    protected void questionDialog(String title, String message, MaterialDialog.SingleButtonCallback positive, MaterialDialog.SingleButtonCallback negative){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
        if(!TextUtils.isEmpty(title)){ builder.title(title);}
        if(!TextUtils.isEmpty(message)){ builder.content(message);}
        if(positive != null){ builder.positiveText("确定");builder.onPositive(positive);}
        if(negative != null){ builder.negativeText("取消");builder.onNegative(negative);}
        MaterialDialog questionDialog = builder.build();
        questionDialog.setCancelable(false);
        questionDialog.show();
    }

    protected  void tipDialog(String title, String message, MaterialDialog.SingleButtonCallback positive){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
        if(!TextUtils.isEmpty(title)){ builder.title(title);}
        if(!TextUtils.isEmpty(message)){ builder.content(message);}
        if(positive != null){ builder.onPositive(positive);}
        MaterialDialog questionDialog = builder.build();
        questionDialog.show();
    }
}
