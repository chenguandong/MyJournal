package com.smart.weather.module.write.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.smart.weather.R;
import com.smart.weather.module.write.bean.JournalBean;
import com.smart.weather.tools.EncodeTools;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class WriteAdapter extends BaseMultiItemQuickAdapter<JournalBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WriteAdapter(List<JournalBean> data) {
        super(data);
        addItemType(JournalBean.WRITE_TAG_TEXT, R.layout.item_write_text);
        addItemType(JournalBean.WRITE_TAG_IMAGE, R.layout.item_write_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, final JournalBean item) {

        switch (item.getItemType()) {
            case JournalBean.WRITE_TAG_TEXT:
                final EditText editText = helper.getView(R.id.write_edit_text);
                if (getData().indexOf(item)==0){
                    editText.setHint("请输入");
                }else{
                    editText.setHint("继续输入...");
                }

                editText.setText(TextUtils.isEmpty(item.getContent())?"":item.getContent());
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        item.setContent(editText.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case JournalBean.WRITE_TAG_IMAGE:
                SimpleDraweeView imageView = helper.getView(R.id.write_imageview);
                Glide.with(mContext).load(item.getImageBase64()).into(imageView);


                if (item.getImageBase64().length()>100){
                    if (item.getBitmapWeakReference()==null){
                        item.setBitmapWeakReference(new WeakReference<>(EncodeTools.base64ToBitmap(item.getImageBase64())));
                    }
                    Glide.with(mContext).load(item.getBitmapWeakReference()).into(imageView);

                }else {
                    Glide.with(imageView.getContext()).asBitmap().load(item.getImageBase64())
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    item.setImageBase64(EncodeTools.bitmapToBase64(resource));
                                }
                            });


                }

                break;
            default:

                break;
        }
    }
}
