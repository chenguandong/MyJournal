package com.smart.journal.module.tags.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import androidx.annotation.StringDef
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.commit
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import com.smart.journal.module.journal.JournalFragment
import com.smart.journal.module.menu.fragment.LocationSearchFragment
import com.smart.journal.module.menu.fragment.LocationSearchFragment.LocationSearchFragmentDelegate
import com.smart.journal.module.tags.activity.SearchActivity.TagActivityType.Companion.LOCATION_SEARCH
import com.smart.journal.module.tags.activity.SearchActivity.TagActivityType.Companion.TAG_SEARCH
import com.smart.journal.module.tags.bean.TagsDbBean
import com.smart.journal.module.tags.fragments.TagFragment
import com.smart.journal.module.tags.fragments.TagSearchFragment

class SearchActivity : BaseActivity() {
    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @StringDef(TAG_SEARCH,LOCATION_SEARCH)
    annotation class TagActivityType {
        companion object {
            const val TAG_SEARCH = "tag_search"
            const val LOCATION_SEARCH = "location_search"
        }
    }

    var mSearchView: SearchView? = null

    var tagSearchFragment: TagSearchFragment? = null

    var tagFragment: TagFragment? = null

    var locationSearchFragment:LocationSearchFragment? =null

    override fun initData() {
    }

    override fun initView() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tag_activity)
        if (savedInstanceState == null) {

            when(intent.getSerializableExtra(ACTIVITY_TYPE)){
                TAG_SEARCH->{
                    initSimpleToolbar(resources.getString(R.string.tag))
                    tagSearchFragment = TagSearchFragment.newInstance(object : TagSearchFragment.TagSearchFragmentDelegate {
                        override fun onItemClickListener(tagsDbBean: TagsDbBean) {
                            tagsDbBean.name?.let {
                                supportFragmentManager.commit {
                                    replace(R.id.container, JournalFragment.newInstance(JournalFragment.FRAGMENT_TYPE_SEARCH_TAG, it))
                                    setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                                }
                            }

                        }

                    })
                    supportFragmentManager.commit {
                        replace(R.id.container, tagSearchFragment!!)
                    }
                }
                LOCATION_SEARCH->{
                    initSimpleToolbar(resources.getString(R.string.location))
                    locationSearchFragment = LocationSearchFragment.newInstance("","")
                    locationSearchFragment!!.delegate = object : LocationSearchFragmentDelegate {
                        override fun onItemClick(address: String) {
                            supportFragmentManager.commit {
                                replace(R.id.container, JournalFragment.newInstance(JournalFragment.FRAGMENT_TYPE_SEARCH_LOCATION, address))
                                setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            }
                        }
                    }

                    supportFragmentManager.commit {
                        replace(R.id.container, locationSearchFragment!!)
                    }
                }
                else->{
                    tagFragment = TagFragment.newInstance()
                    supportFragmentManager.commit {
                        replace(R.id.container, tagFragment!!)
                    }
                }
            }
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)
        val searchItem = menu!!.findItem(R.id.toolbar_search_view)
        mSearchView = searchItem.actionView as SearchView
        mSearchView!!.isSubmitButtonEnabled = true
        mSearchView!!.setIconifiedByDefault(true)
        mSearchView!!.queryHint = "搜索"
        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String?): Boolean {

                when(intent.getSerializableExtra(ACTIVITY_TYPE)){
                    TAG_SEARCH->{
                        tagSearchFragment?.let {
                            it.searchText(s!!)
                        }
                    }
                    LOCATION_SEARCH->{
                        locationSearchFragment?.let {
                            it.searchText(s!!)
                        }
                    }
                    else ->{
                        tagFragment?.let {
                            it.searchText(s!!)
                        }
                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    companion object {
        const val ACTIVITY_TYPE = "activity_type"

        @JvmStatic
        fun startActivity(context: Context,@TagActivityType tagAction: String){
            context.startActivity(
                    Intent(context,SearchActivity::class.java).putExtra(ACTIVITY_TYPE,tagAction)
            )

        }
    }
}
