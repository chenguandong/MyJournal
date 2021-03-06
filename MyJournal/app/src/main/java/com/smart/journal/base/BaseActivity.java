package com.smart.journal.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.smart.journal.R;
import com.smart.journal.tools.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

/**
 * @author guandongchen
 * @date 2018/1/16
 */
public abstract class BaseActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected Context context;

    protected Toolbar toolbar;

    private androidx.appcompat.app.ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置普通带back 的导航栏
     *
     * @param titleString 名称
     */
    protected void initSimpleToolbar(String titleString) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(titleString);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
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

    protected void initSimpleToolbarWithNoBack(String titleString) {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(titleString);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            // actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    protected void setToolbarTitle(String titleString) {
        toolbar.setTitle(titleString);
    }

    protected void showToolbar() {

        if (toolbar != null) {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    protected void hiddenToolbar() {

        if (toolbar != null) {
            toolbar.setVisibility(View.GONE);
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    protected void init() {
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {/* Do something */}
}
