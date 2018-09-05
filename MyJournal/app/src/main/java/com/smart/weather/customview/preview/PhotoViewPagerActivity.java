/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.smart.weather.customview.preview;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.smart.weather.R;
import com.smart.weather.base.BaseActivity;
import com.wingsofts.dragphotoview.DragPhotoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewPagerActivity extends BaseActivity {

    public static final String URLS = "photoURls";//图片集合
    public static final String URLS_CHOOSE_INDEX = "photoURls_choose_index";// 当前选中
    public static final String URLS_SHOW_TAG = "URLS_SHOW_TAG";// 自定义字段

    @BindView(R.id.photoViewPage)
    HackyViewPager photoViewPage;

    private static List<String> photoUrl = new ArrayList<>();

    private int choosedIndex;

    private SamplePagerAdapter samplePagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo);
        ButterKnife.bind(this);
        photoUrl = (List<String>) getIntent().getSerializableExtra(URLS);

        if (photoUrl==null){
            ToastUtils.showShort("无可显示图片");
            return;
        }
        choosedIndex = getIntent().getIntExtra(URLS_CHOOSE_INDEX, 0);

        samplePagerAdapter = new SamplePagerAdapter();
        photoViewPage.setAdapter(samplePagerAdapter);
        photoViewPage.setCurrentItem(choosedIndex);
        photoViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    class SamplePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return photoUrl.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {


            DragPhotoView photoView = new DragPhotoView(container.getContext());

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            photoView.setOnExitListener(new DragPhotoView.OnExitListener() {
                @Override
                public void onExit(DragPhotoView dragPhotoView, float v, float v1, float v2, float v3) {
                    finish();
                }
            });

            photoView.setBackgroundColor(ContextCompat.getColor(container.getContext(),R.color.black));

           Glide.with(container.getContext()).load(photoUrl.get(position)).into(photoView);
            //ImageTools.showImageViewGlide(photoUrl.get(position),photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
