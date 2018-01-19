package com.smart.weather.module.write.Views;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smart.weather.R;

import java.util.List;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class ToolViewAdapter extends BaseQuickAdapter<ToolBean,BaseViewHolder>{

    public ToolViewAdapter(int layoutResId, @Nullable List<ToolBean> data) {
        super(layoutResId, data);
    }

    public ToolViewAdapter(@Nullable List<ToolBean> data) {
        super(data);
    }

    public ToolViewAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolBean item) {

        ImageView imageView = helper.getView(R.id.toolImageView);
        imageView.setImageDrawable(ContextCompat.getDrawable(mContext,item.getIcon()));
    }
}
