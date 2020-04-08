package com.smart.journal.module.tags.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.gson.Gson
import com.smart.journal.R
import com.smart.journal.base.BaseFragment
import com.smart.journal.module.tags.adapter.TagAdapter
import com.smart.journal.module.tags.bean.TagsDbBean
import com.smart.journal.module.tags.viewmodel.TagViewModel
import com.smart.journal.tools.DividerItemDecorationTools
import com.smart.journal.tools.eventbus.MessageEvent
import kotlinx.android.synthetic.main.tags_fragment.*
import org.greenrobot.eventbus.EventBus

open class TagFragment : BaseFragment() {

    var tagAdapter: TagAdapter? = null

    private var selectedTags: MutableList<String> = mutableListOf()

    companion object {
        fun newInstance() = TagFragment()
    }

    lateinit var viewModel: TagViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.tags_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getData() {
        initData()
    }

    /**
     * 搜索
     */
    open fun searchText(keyWord:String){
        if (keyWord.isNullOrBlank()) {
            viewModel.loadUsers()
        } else {
            viewModel.queryTagByName(keyWord)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TagViewModel::class.java)
        initView()
        viewModel!!.getTags().observe(viewLifecycleOwner, Observer {
            it?.let {
                tagAdapter!!.setNewData(it as MutableList<TagsDbBean>)
            }

        })

    }

    override fun initData() {

    }

    override fun initView() {

        tagAdapter = TagAdapter(ArrayList())
        recycleView.adapter = tagAdapter
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView!!.addItemDecoration(DividerItemDecorationTools.getItemDecoration(context))
        setOnItemClickListen()


    }
    open fun setOnItemClickListen(){
        tagAdapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

                val tagsDbBean = adapter.data[position] as TagsDbBean

                if (tagsDbBean.name!!.startsWith(resources.getString(R.string.add))) {
                    var tagsDbBeanInsert = TagsDbBean(tagsDbBean.name!!.substring(3, tagsDbBean.name!!.length), 0)
                    viewModel.inertTag(tagsDbBeanInsert)
                    tagsDbBeanInsert.name?.let { selectedTags.add(it) }

                } else {
                    tagsDbBean.name?.let {
                        if (selectedTags.contains(it)) {
                            selectedTags.remove(it)
                        } else {
                            selectedTags.add(it)
                        }
                    }
                }

                tagAdapter!!.selectedTags = selectedTags
            }

        })
    }

    override fun onStop() {
        super.onStop()
        selectedTags?.let {
            EventBus.getDefault().post(MessageEvent(Gson().toJson(selectedTags), MessageEvent.NOTE_TAG_CHANGE))
        }
    }

}
