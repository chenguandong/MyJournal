package com.smart.weather.module.write.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.smart.weather.R;
import com.smart.weather.module.write.bean.JournalBean;

import java.util.List;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class WriteAdapter extends BaseMultiItemQuickAdapter<JournalBean, BaseViewHolder> {


    public enum WriteAdapterModel{
        WriteAdapterModel_SHOW,
        WriteAdapterModel_EDIT
    }

    private WriteAdapterModel writeAdapterModel;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WriteAdapter(List<JournalBean> data,WriteAdapterModel writeAdapterModel) {
        super(data);
        this.writeAdapterModel = writeAdapterModel;
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

                if (writeAdapterModel==WriteAdapterModel.WriteAdapterModel_SHOW){
                    editText.setEnabled(false);
                }
                break;
            case JournalBean.WRITE_TAG_IMAGE:
                SimpleDraweeView imageView = helper.getView(R.id.write_imageview);
                Glide.with(mContext).load(item.getImageURL()).into(imageView);

                break;
            default:

                break;
        }
    }
}
