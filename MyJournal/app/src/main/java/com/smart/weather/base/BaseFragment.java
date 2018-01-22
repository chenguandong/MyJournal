package com.smart.weather.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.weather.R;


public abstract class BaseFragment extends Fragment {

    protected Context context;
    protected android.support.v7.app.ActionBar actionBar;
    protected Toolbar toolbar;
    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
    }
    /**
     * 设置普通带back 的导航栏
     * @param titleString 名称
     */
    protected void initSimpleToolbar(View view,String titleString){
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(titleString);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    protected void hiddenToolbarBackButton(){

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected abstract void initView();
    protected abstract void initData();
    protected void init(){
        initView();
        initData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.pictrue_list, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
}
