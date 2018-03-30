package com.smart.weather

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import butterknife.ButterKnife
import com.smart.weather.base.BaseActivity
import com.smart.weather.module.weather.WeatherFragment
import com.smart.weather.module.calendar.CalendarFragment
import com.smart.weather.module.journal.JournalFragment
import com.smart.weather.module.map.MapFragment
import com.smart.weather.module.mine.MineFragment
import com.smart.weather.module.write.activity.WriteActivity
import com.smart.weather.tools.BottomNavigationViewHelper
import com.smart.weather.tools.PermissionTools
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * @author guandongchen
 */
class MainActivity : BaseActivity() {

    private var fragmentPagerAdapter: FragmentPagerAdapter? = null
    private val fragmentList = ArrayList<Fragment>()
    private var journalFragment: Fragment? = null
    private var mapFragment: Fragment? = null
    private var mineFragment: Fragment? = null
    private var weatherFragment: Fragment? = null
    private var calendarFragment: Fragment? = null

    private val titles = arrayOf("日记", "地图", "日历", "我的")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initSimpleToolbarWithNoBack(titles[0])
        initView()
        initData()
    }

    override fun initView() {

        BottomNavigationViewHelper.disableShiftMode(navigationView)

        journalFragment = JournalFragment.newInstance()

        mapFragment = MapFragment.newInstance()

        mineFragment = MineFragment.newInstance()

        weatherFragment = WeatherFragment.newInstance()

        calendarFragment = CalendarFragment.newInstance()

        fragmentList.add(journalFragment as JournalFragment)
        fragmentList.add((mapFragment as MapFragment?)!!)
        fragmentList.add((calendarFragment as CalendarFragment?)!!)
        fragmentList.add((weatherFragment as WeatherFragment?)!!)

        viewPager!!.offscreenPageLimit = fragmentList.size

        fragmentPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {

                return fragmentList[position]
            }

            override fun getCount(): Int {
                return fragmentList.size
            }

        }

        viewPager!!.adapter = fragmentPagerAdapter

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                navigationView!!.menu.getItem(position).isChecked = true

                title = titles[position]
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        navigationView!!.setOnNavigationItemSelectedListener { item ->
            var title = ""
            when (item.itemId) {

                R.id.item1 -> {
                    viewPager!!.setCurrentItem(0, false)
                    title = titles[0]
                }
                R.id.item2 -> {
                    viewPager!!.setCurrentItem(1, false)
                    title = titles[1]
                }
                R.id.item3 -> {
                    viewPager!!.setCurrentItem(2, false)
                    title = titles[2]
                }
                R.id.item4 -> {
                    viewPager!!.setCurrentItem(3, false)
                    title = titles[3]
                }
                R.id.item_add -> startActivity(Intent(this@MainActivity, WriteActivity::class.java))
            }
            setToolbarTitle(title)
            true
        }

        PermissionTools.checkPermission(this@MainActivity, PermissionTools.PermissionType.PERMISSION_TYPE_LOCATION, object : PermissionTools.PermissionCallBack {
            override fun permissionYES() {

            }

            override fun permissionNO() {

            }
        })
    }

    override fun initData() {}


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.toolbar_right_action).setTitle("保存").setEnabled(false).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.toolbar_right_action) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
