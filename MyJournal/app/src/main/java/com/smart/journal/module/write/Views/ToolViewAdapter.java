package com.smart.journal.module.write.Views;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smart.journal.R;

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

        AppCompatImageView imageView = helper.getView(R.id.toolImageView);
        imageView.setImageDrawable(ContextCompat.getDrawable(mContext,item.getIcon()));
    }
}
