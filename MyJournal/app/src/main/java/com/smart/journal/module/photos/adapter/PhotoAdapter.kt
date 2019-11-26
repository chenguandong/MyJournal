package com.smart.journal.module.photos.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smart.journal.R


/**
 * @author guandongchen
 * @date 2018/9/6
 */
class PhotoAdapter : BaseQuickAdapter<PhotoBean, BaseViewHolder> {

    constructor(layoutResId: Int, data: List<PhotoBean>?) : super(layoutResId, data)

    constructor(data: List<PhotoBean>?) : super(data)

    constructor(layoutResId: Int) : super(layoutResId)

    override fun convert(helper: BaseViewHolder, item: PhotoBean) {
        Glide.with(mContext).load(item.photoURL).into(helper.getView(R.id.photoImageView))
    }
}
