package com.smart.weather.module.journal.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smart.weather.R;
import com.smart.weather.module.write.bean.JournalBeanDBBean;
import com.smart.weather.tools.EncodeTools;

import java.util.List;

/**
 * @author guandongchen
 * @date 2018/1/22
 */

public class JournalAdapter extends BaseQuickAdapter<JournalBeanDBBean,BaseViewHolder>{
    public JournalAdapter(int layoutResId, @Nullable List<JournalBeanDBBean> data) {
        super(layoutResId, data);
    }

    public JournalAdapter(@Nullable List<JournalBeanDBBean> data) {
        super(data);
    }

    public JournalAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, JournalBeanDBBean item) {

        ImageView imageView = helper.getView(R.id.imageView);
        TextView contentTextview = helper.getView(R.id.contentView);

        StringBuilder contentBuilder = new StringBuilder();
        String imageBase64 = "";
        if (item.getContent()!=null){

            String contents[] = item.getContent().split("~~~");

            for (String content:
            contents) {
                if (content.startsWith("text://")){
                    contentBuilder.append(content.substring("text://".length(),content.length()));
                }else if (content.startsWith("image://")){
                    imageBase64 = content.substring("image://".length(),content.length());
                }

            }
        }

        contentTextview.setText(contentBuilder.toString());

        if (!TextUtils.isEmpty(imageBase64)){
            Glide.with(mContext).load(EncodeTools.base64ToBitmap(imageBase64)).into(imageView);
        }
    }
}
