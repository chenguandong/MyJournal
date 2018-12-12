package com.smart.weather.module.journal.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smart.weather.R;
import com.smart.weather.contants.Contancts;
import com.smart.weather.module.journal.bean.JournalItemBean;
import com.smart.weather.module.write.bean.JournalBeanDBBean;
import com.smart.weather.tools.DateTools;
import com.smart.weather.tools.StringTools;

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
        TextView infoTextview = helper.getView(R.id.infoTextView);
        TextView weekTextview = helper.getView(R.id.weekTextView);
        TextView dayTextview = helper.getView(R.id.dayTextView);

        String imageURL = "";
        if (item.getJournalItemBean()==null){
            JournalItemBean itemBean = new JournalItemBean();
            StringBuilder contentBuilder = new StringBuilder();

            if (item.getContent()!=null){

                String contents[] = item.getContent().split(Contancts.FILE_TYPE_SPLIT);

                for (String content:
                        contents) {
                    if (content.startsWith(Contancts.FILE_TYPE_TEXT)){
                        contentBuilder.append(content.substring(Contancts.FILE_TYPE_TEXT.length(),content.length()));
                    }else if (content.startsWith(Contancts.FILE_TYPE_IMAGE)){
                        if (TextUtils.isEmpty(imageURL)) {
                            imageURL = content.substring(Contancts.FILE_TYPE_IMAGE.length(), content.length());
                        }

                    }

                }


                itemBean.setContent(contentBuilder.toString());
                item.setJournalItemBean(itemBean);
            }
        }

        contentTextview.setText(item.getJournalItemBean().getContent());
        if (item.getLocation()!=null){
            infoTextview.setText(DateTools.formatTime(item.getDate().getTime())+"."+StringTools.getNotNullString(item.getLocation().getAdress()));
        }
        weekTextview.setText(DateTools.getChineseWeek(item.getDate()));
        dayTextview.setText(DateTools.getYMd(item.getDate())[2]+"");

        if (!TextUtils.isEmpty(imageURL)){
            Glide.with(mContext).load(imageURL).into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }
    }
}
