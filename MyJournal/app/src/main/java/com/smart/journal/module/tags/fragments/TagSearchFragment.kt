package com.smart.journal.module.tags.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.smart.journal.module.tags.bean.TagsDbBean

class TagSearchFragment : TagFragment {

    interface TagSearchFragmentDelegate{
        fun onItemClickListener(tagsDbBean: TagsDbBean)
    }

    var delegate:TagSearchFragmentDelegate? = null

    constructor(delegate: TagSearchFragmentDelegate?) : super() {
        this.delegate = delegate
    }

    constructor() : super()


    override fun setOnItemClickListen() {
        super.setOnItemClickListen()
        tagAdapter!!.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                    delegate?.let {
                        it.onItemClickListener(adapter.data[position] as TagsDbBean)
                    }
            }

        })
    }


    override fun searchText(keyWord:String) {
        if (keyWord.isNullOrBlank()) {
            viewModel.loadUsers()
        } else {
            viewModel.queryTagByNameNoAdd(keyWord)
        }
    }

    companion object {
        fun newInstance(param: TagSearchFragmentDelegate): TagSearchFragment {
            return TagSearchFragment(param)
        }
    }

}
