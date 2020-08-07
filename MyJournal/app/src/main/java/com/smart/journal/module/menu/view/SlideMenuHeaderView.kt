package com.smart.journal.module.menu.view

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.logger.Logger
import com.smart.journal.R
import com.smart.journal.app.MyApp
import com.smart.journal.module.write.db.JournalDBHelper
import kotlinx.android.synthetic.main.header_slide_menu_view.view.*
import kotlinx.coroutines.*

/**
 *
 * @author guandongchen
 * @date 2020/4/7
 */
class SlideMenuHeaderView : LinearLayout, View.OnClickListener {

    interface SlideMenuHeaderViewDelegate {
        fun onTagItemClick()
        fun onLocationItemClick()
        fun onFavouriteItemClick()
        fun onThisDayItemClick()
    }

    private var delegate: SlideMenuHeaderViewDelegate? = null

    constructor(context: Context?, delegate: SlideMenuHeaderViewDelegate) : super(context) {
        this.delegate = delegate
        initView()
        initData()
    }

    /**
     * 刷新数据
     */
    public fun refresh(){
        initData()
    }

    private fun initData() {
       GlobalScope.launch {
           var inCount = 0
           var allJournal  =  MyApp.database!!.mJournalDao().allJournalNoLiveData()
           for(journal in allJournal){
               if (DateUtils.isToday(journal.date)){
                   inCount++
               }
           }
            withContext(Dispatchers.Main){
                numTextView!!.text = "$inCount"
                numTextView!!.visibility = if (inCount>0) View.VISIBLE else View.GONE
            }
       }
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    private fun initView() {
        View.inflate(context, R.layout.header_slide_menu_view, this)
        tagTextView.setOnClickListener(this)
        locationTextView.setOnClickListener(this)
        favouriteTextView.setOnClickListener(this)
        onThisDayTextView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            //标签
            R.id.tagTextView -> {
                delegate?.let {
                    it.onTagItemClick()
                }
            }
            //位置
            R.id.locationTextView -> {
                delegate?.let {
                    it.onLocationItemClick()
                }
            }
            //收藏
            R.id.favouriteTextView -> {
                delegate?.let {
                    it.onFavouriteItemClick()
                }
            }
            //那年今日
            R.id.onThisDayTextView -> {
                delegate?.let {
                    it.onThisDayItemClick()
                }
            }

        }
    }
}