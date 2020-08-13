package com.smart.journal.module.tags.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import com.smart.journal.module.journal.JournalFragment
import com.smart.journal.module.journal.SearchEable
import com.smart.journal.module.tags.activity.SearchActivity
import com.smart.journal.module.tags.bean.TagsDbBean
import kotlinx.android.synthetic.main.act_search_adress.*
import kotlinx.android.synthetic.main.fragment_tag_search_wapper.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TagSearchWapperFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TagSearchWapperFragment : Fragment(), SearchEable {

    private var param1: String? = null
    private var param2: String? = null

    private var fragments = arrayListOf<SearchEable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_search_wapper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragments!!.add(TagSearchFragment.newInstance(object :TagSearchFragment.TagSearchFragmentDelegate{
            override fun onItemClickListener(tagsDbBean: TagsDbBean) {

            }

            override fun onNoKeyWordSearch() {

            }

        }))
        fragments!!.add(JournalFragment.newInstance(JournalFragment.FRAGMENT_TYPE_SEARCH_TAG, ""))

        viewPager!!.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
               return fragments.get(position) as Fragment
            }

            override fun getCount(): Int {
              return  fragments.size
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TagSearchWapperFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


    override fun doSerarch(serarchKey: String, searchType: String) {
        if (TextUtils.isEmpty(serarchKey)){
            viewPager.setCurrentItem(0,false)
            fragments[0].doSerarch(serarchKey,searchType)
        }else{
            viewPager.setCurrentItem(1,false)
            fragments[1].doSerarch(serarchKey,searchType)
        }

    }
}