package com.smart.journal.module.journal.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import com.smart.journal.module.journal.JournalFragment
import com.smart.journal.module.journal.SearchEable
import com.smart.journal.module.journal.SearchEableType
import kotlinx.android.synthetic.main.activity_global_search.*

class GlobalSearchActivity : BaseActivity() {
    private var mSearchView: SearchView? = null
    private var fragments: SparseArray<SearchEable> = SparseArray()
    private var currnetIndex: Int = 0
    private var searchKeyWord: String = ""
    override fun initData() {
    }

    override fun initView() {
        fragments.put(0, JournalFragment.newInstance(JournalFragment.FRAGMENT_EMPTY, ""))
        fragments.put(1, JournalFragment.newInstance(JournalFragment.FRAGMENT_EMPTY, ""))
        fragments.put(2, JournalFragment.newInstance(JournalFragment.FRAGMENT_EMPTY, ""))
        fragments.put(3, JournalFragment.newInstance(JournalFragment.FRAGMENT_EMPTY, ""))
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size()
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position] as Fragment
            }

        }
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currnetIndex = position
                if (searchKeyWord.isNotBlank()) {
                    doSearch(currnetIndex, searchKeyWord)
                }
            }
        })
        viewPager2.offscreenPageLimit = fragments.size()
        TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> {
                    tab.text = context.getString(R.string.journal)
                }
                1 -> {
                    tab.text = context.getString(R.string.favourite)
                }
                2 -> {
                    tab.text = context.getString(R.string.tag)
                }
                3 -> {
                    tab.text = context.getString(R.string.location)
                }
            }
        }).attach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_search)
        initSimpleToolbar("搜索")
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)
        val searchItem = menu!!.findItem(R.id.toolbar_search_view)
        mSearchView = searchItem.actionView as SearchView
        mSearchView!!.queryHint = "搜索"
        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String?): Boolean {
                searchKeyWord = s!!
                doSearch(currnetIndex, searchKeyWord)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    private fun doSearch(currentIndex: Int, searchStr: String) {

        when (currentIndex) {
            0 -> fragments[currnetIndex].doSerarch(searchStr, SearchEableType.ALL)
            1 -> fragments[currnetIndex].doSerarch(searchStr, SearchEableType.FAVOURITE)
            2 -> fragments[currnetIndex].doSerarch(searchStr, SearchEableType.TAG)
            3 -> fragments[currnetIndex].doSerarch(searchStr, SearchEableType.LOCATION)

        }
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, GlobalSearchActivity::class.java))
        }
    }
}
