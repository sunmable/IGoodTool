package cn.chaboshi.cbslibrary.activity;

/**
 * Created by testcbs on 2017/5/22.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;

import cn.chaboshi.cbslibrary.handler.IDataLoadListener;

/**
 * Created by testcbs on 2017/5/19.
 */
public abstract class BaseActivity extends AppCompatActivity {

    //1：变量声明区域----------------------------------------------------------------------------------------------
    //2：视图生命周期----------------------------------------------------------------------------------------------
    //3：视图逻辑-------------------------------------------------------------------------------------------------
    //4：业务逻辑-------------------------------------------------------------------------------------------------
    //5：内部声明-------------------------------------------------------------------------------------------------
    //6：内部类---------------------------------------------------------------------------------------------------
    //1：变量声明区域----------------------------------------------------------------------------------------------
    public MaterialDialog loaddingDialog;
    private boolean isInited = false;
    //2：视图生命周期----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isInited){
            initView();
            initData(new IDataLoadListener() {
                @Override
                public void onLoadFinish(Object data) {
                    config();
                }
            });
            isInited = true;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExist = intent.getBooleanExtra("EXIST", false);
            if (isExist) {
                this.finish();
            }
        }
    }

    //3：视图逻辑-------------------------------------------------------------------------------------------------
    protected abstract void initView();

    protected abstract void initData(IDataLoadListener dataLoadListener);

    protected abstract void config();

    protected void questionDialog(String title, String message, MaterialDialog.SingleButtonCallback positive,MaterialDialog.SingleButtonCallback negative){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(BaseActivity.this);
        if(!TextUtils.isEmpty(title)){ builder.title(title);}
        if(!TextUtils.isEmpty(message)){ builder.content(message);}
        if(positive != null){ builder.positiveText("确定");builder.onPositive(positive);}
        if(negative != null){ builder.negativeText("取消");builder.onNegative(negative);}
        MaterialDialog questionDialog = builder.build();
        questionDialog.setCancelable(false);
        questionDialog.show();
    }

    protected  void tipDialog(String title, String message, MaterialDialog.SingleButtonCallback positive){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(BaseActivity.this);
        if(!TextUtils.isEmpty(title)){ builder.title(title);}
        if(!TextUtils.isEmpty(message)){ builder.content(message);}
        if(positive != null){ builder.onPositive(positive);}
        MaterialDialog questionDialog = builder.build();
        questionDialog.show();
    }

    //4：业务逻辑-------------------------------------------------------------------------------------------------


    //5：内部声明-------------------------------------------------------------------------------------------------
    private void initBaseView(){
//        View loaddingView = LayoutInflater.from(BaseActivity.this).inflate(R.layout.dialog_loadding,null);
//        View gifView = loaddingView.findViewById(R.id.dialog_loadding_img);
//        gifView.setBackgroundResource(R.drawable.dialog_loadding_anim);
//        AnimationDrawable loaddingAnimation = (AnimationDrawable) gifView.getBackground();
//        loaddingAnimation.start();
//
//        MaterialDialog.Builder builder = new MaterialDialog.Builder(BaseActivity.this);
//        builder.customView(loaddingView,false).cancelable(false);
//        loaddingDialog = builder.build();
    }
    //6：内部类---------------------------------------------------------------------------------------------------

}
