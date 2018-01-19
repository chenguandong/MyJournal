package com.smart.weather.module.write.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.smart.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class ToolView extends LinearLayout {

    public interface ToolViewDelegate{
        void onItemClick(ToolBean toolBean);
    }

    @BindView(R.id.toolbarRecycleView)
    RecyclerView toolbarRecycleView;
    private Context context;
    private ToolViewAdapter toolViewAdapter;
    private ToolViewDelegate delegate;
    private List<ToolBean>toolBeans = new ArrayList<ToolBean>(){

        {
            add(new ToolBean(R.drawable.ic_add,ToolBean.TOOL_IMAGE));
            add(new ToolBean(R.drawable.ic_weather,ToolBean.TOOL_WEATHER));
        }
    };

    public ToolView(Context context) {
        super(context);
        initView(context);
    }

    public ToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ToolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.view_toolbar, this);
        ButterKnife.bind(view);

        toolViewAdapter = new ToolViewAdapter(R.layout.item_toolview,toolBeans);

        toolbarRecycleView.setLayoutManager(new LinearLayoutManager(context,HORIZONTAL,false));

        toolbarRecycleView.setAdapter(toolViewAdapter);

        view.setBackgroundColor(ContextCompat.getColor(context,R.color.write));

        toolViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (delegate!=null){
                    delegate.onItemClick(toolBeans.get(position));
                }
            }
        });
    }


    public void setDelegate(ToolViewDelegate delegate) {
        this.delegate = delegate;
    }
}
