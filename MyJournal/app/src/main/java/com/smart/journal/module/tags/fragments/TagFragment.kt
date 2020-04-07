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

class TagFragment : BaseFragment() {

    private var tagAdapter: TagAdapter? = null

    private var selectedTags: MutableList<String> = mutableListOf()

    companion object {
        fun newInstance() = TagFragment()
    }

    private lateinit var viewModel: TagViewModel

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_search_view, menu)

        val searchItem = menu.findItem(R.id.toolbar_search_view)
        var mSearchView: SearchView = searchItem.actionView as SearchView
        mSearchView.setSubmitButtonEnabled(true)
        mSearchView.setIconifiedByDefault(true)
        mSearchView.queryHint = "搜索"
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String?): Boolean {
                if (s.isNullOrBlank()) {
                    viewModel.loadUsers()
                } else {
                    viewModel.queryTagByName(s)
                }
                return true
            }
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TagViewModel::class.java)

        initView()
        initSimpleToolbar(view, resources.getString(R.string.tag))

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

        tagAdapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

                val tagsDbBean = adapter.data[position] as TagsDbBean

                if (tagsDbBean.name!!.startsWith("添加")) {
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
