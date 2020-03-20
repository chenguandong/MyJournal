package com.smart.journal.module.write.views;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.smart.journal.R;
import com.smart.journal.module.write.bean.ToolBean;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class ToolViewAdapter extends BaseQuickAdapter<ToolBean, BaseViewHolder>{

    public ToolViewAdapter(int layoutResId, @Nullable List<ToolBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ToolBean item) {

        AppCompatImageView imageView = helper.getView(R.id.toolImageView);
        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),item.getIcon()));
    }
}
