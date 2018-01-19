package com.smart.weather.module.write;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.weather.R;
import com.smart.weather.app.MyApp;
import com.smart.weather.base.BaseFragment;
import com.smart.weather.module.write.Views.ToolBean;
import com.smart.weather.module.write.Views.ToolView;
import com.smart.weather.module.write.adapter.WriteAdapter;
import com.smart.weather.module.write.bean.JournalBean;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteFragment extends BaseFragment {
    public static final int REQUEST_CODE_CHOOSE = 9;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.toolView)
    ToolView toolView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<JournalBean> writeSectionBeans = new ArrayList<>();
    private WriteAdapter adapter;

    public WriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WriteFragment newInstance() {
        WriteFragment fragment = new WriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "");
        args.putString(ARG_PARAM2, "");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    @Override
    protected void initView() {
        writeSectionBeans.add(new JournalBean(""));

        adapter = new WriteAdapter(writeSectionBeans);
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        recycleView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        toolView.setDelegate(new ToolView.ToolViewDelegate() {
            @Override
            public void onItemClick(ToolBean toolBean) {

                switch (toolBean.getItemType()){
                    case ToolBean.TOOL_IMAGE:
                        Matisse.from(getActivity())
                                .choose(MimeType.allOf())
                                .countable(true)
                                .maxSelectable(9)
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f)
                                .theme(R.style.Matisse_Zhihu)
                                .imageEngine(new PicassoEngine())
                                .capture(true)
                                .captureStrategy(
                                        new CaptureStrategy(true, MyApp.UPDATE_APP_ID))
                                .forResult(REQUEST_CODE_CHOOSE);
                        break;
                    case ToolBean.TOOL_WEATHER:
                        break;
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    private void filterJournalItem(){

         Predicate<JournalBean> predicate = new Predicate<JournalBean>() {

                         @Override
                         public boolean evaluate(JournalBean bean) {
                             // TODO Auto-generated method stub
                             return TextUtils.isEmpty(bean.getContent())&&TextUtils.isEmpty(bean.getImageBase64());
                         }
                     };
        List<JournalBean> result = (List<JournalBean>) CollectionUtils.select(writeSectionBeans, predicate);
        writeSectionBeans.removeAll(result);
        writeSectionBeans.add(new JournalBean(""));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected= Matisse.obtainResult(data);
            for (Uri uri:
                 mSelected) {
                writeSectionBeans.add(new JournalBean("",uri.toString()));
            }
            filterJournalItem();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
