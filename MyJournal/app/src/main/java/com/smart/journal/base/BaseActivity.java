package com.smart.journal.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.parfoismeng.slidebacklib.SlideBack;
import com.smart.journal.R;
import com.smart.journal.tools.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author guandongchen
 * @date 2018/1/16
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Context context;
    protected Toolbar toolbar;
    private android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // 在需要滑动返回的Activity中注册
        SlideBack.register(this, () ->{
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // onDestroy时记得解绑
        // 内部使用WeakHashMap，理论上不解绑也行，但最好还是手动解绑一下
        SlideBack.unregister(this);
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    /**
     * 设置普通带back 的导航栏
     * @param titleString 名称
     */
    protected void initSimpleToolbar(String titleString){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(titleString);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(false);
        }

        //toolbar.setNavigationIcon(ContextCompat.getDrawable(context, R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected  void  initSimpleToolbarWithNoBack(String titleString){

        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle(titleString);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
           // actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    protected void setToolbarTitle(String titleString){
        toolbar.setTitle(titleString);
    }

    protected void showToolbar(){

        if (toolbar!=null){
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    protected void hiddenToolbar(){

        if (toolbar!=null){
            toolbar.setVisibility(View.GONE);
        }


    }


    protected abstract void initView();
    protected abstract void initData();

    protected void init(){
        initView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {/* Do something */};
}
