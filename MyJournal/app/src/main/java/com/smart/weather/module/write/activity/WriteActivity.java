package com.smart.weather.module.write.activity;

import android.content.Intent;
import android.os.Bundle;

import com.smart.weather.R;
import com.smart.weather.base.BaseActivity;
import com.smart.weather.module.write.WriteFragment;

/**
 * @author guandongchen
 */
public class WriteActivity extends BaseActivity {

    private WriteFragment writeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        writeFragment = WriteFragment.Companion.newInstance();

        getSupportFragmentManager().beginTransaction().replace(R.id.contentView,writeFragment).commit();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        writeFragment.onActivityResult(requestCode, resultCode, data);
    }
}
