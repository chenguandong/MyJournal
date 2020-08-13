package com.smart.journal.module.tags.fragments

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.smart.journal.module.journal.SearchEable
import com.smart.journal.module.tags.activity.SearchActivity
import com.smart.journal.module.tags.bean.TagsDbBean

class TagSearchFragment : TagFragment , SearchEable {

    interface TagSearchFragmentDelegate {
        fun onItemClickListener(tagsDbBean: TagsDbBean)
        fun onNoKeyWordSearch()
    }

    var delegate: TagSearchFragmentDelegate? = null

    constructor(delegate: TagSearchFragmentDelegate?) : super() {
        this.delegate = delegate
    }

    constructor() : super()


    override fun setOnItemClickListen() {
        super.setOnItemClickListen()
        tagAdapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                delegate?.let {
                    it.onItemClickListener(adapter.data[position] as TagsDbBean)
                }
            }

        })
    }



    companion object {
        fun newInstance(param: TagSearchFragmentDelegate): TagSearchFragment {
            return TagSearchFragment(param)
        }
    }

    override fun doSerarch(serarchKey: String, searchType: String) {
        if (serarchKey.isNullOrBlank()) {
            //viewModel.loadAllTags()
            delegate?.let {
                it.onNoKeyWordSearch()
            }
        } else {
            viewModel.queryTagByNameNoAdd(serarchKey)
        }
    }

}
