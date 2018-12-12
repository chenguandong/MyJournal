package com.smart.weather.customview.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.smart.weather.R;
import com.smart.weather.contants.Contancts;
import com.smart.weather.customview.preview.PhotoViewTools;
import com.smart.weather.module.write.adapter.WriteAdapter;
import com.smart.weather.module.write.bean.JournalBean;
import com.smart.weather.module.write.bean.JournalBeanDBBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author guandongchen
 * @date 2018/9/4
 */
@SuppressLint("ValidFragment")
public class PreViewBottomSheetDialogFragment extends android.support.design.widget.BottomSheetDialogFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private BottomSheetBehavior behavior;
    private List<JournalBean> writeSectionBeans = new ArrayList<>();
    private WriteAdapter adapter;
    private JournalBeanDBBean journalBeanDBBean;


    public PreViewBottomSheetDialogFragment(JournalBeanDBBean journalBeanDBBean) {
        this.journalBeanDBBean = journalBeanDBBean;

        if (journalBeanDBBean.getContent()!=null){

            String[] contents = journalBeanDBBean.getContent().split(Contancts.FILE_TYPE_SPLIT);

            for (String content:
                    contents) {
                if (content.startsWith(Contancts.FILE_TYPE_TEXT)){
                    writeSectionBeans.add(new JournalBean(content.replace(Contancts.FILE_TYPE_TEXT,"")));
                }else if (content.startsWith(Contancts.FILE_TYPE_IMAGE)){
                    writeSectionBeans.add(new JournalBean("",content.replace(Contancts.FILE_TYPE_IMAGE,"")));
                }

            }

        }
    }

    public PreViewBottomSheetDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_dialog_preview_bottom_sheet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new WriteAdapter(writeSectionBeans, WriteAdapter.WriteAdapterModel.WriteAdapterModel_SHOW);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener((adapter, view1, position) -> {
           if ( writeSectionBeans.get(position).getItemType()==JournalBean.WRITE_TAG_IMAGE){
               PhotoViewTools.showPhotos(new ArrayList<String>(){
                   {
                       add(writeSectionBeans.get(position).getImageURL());
                   }
               },0,getContext());
           }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext(), R.style.TransparentBottomSheetStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置软键盘不自动弹出
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = ScreenUtils.getScreenHeight();
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void doclick(View v) {
        //点击任意布局关闭
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
