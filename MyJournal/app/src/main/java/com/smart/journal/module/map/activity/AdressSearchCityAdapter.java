package com.smart.journal.module.map.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.journal.R;
import com.smart.journal.module.map.bean.MjPoiItem;

import java.util.List;

/**
 * Created by guandongchen on 2016/11/22.
 */

public class AdressSearchCityAdapter extends BaseAdapter {

    private List<MjPoiItem> cityList;

    private Context context;

    public int selPostion = -1;

    public AdressSearchCityAdapter(Context context, List<MjPoiItem> cityList) {
        this.cityList = cityList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHodel viewHodel = null;

        if (convertView==null){

            convertView = View.inflate(context, R.layout.item_select_location_subtitle,null);

            viewHodel = new ViewHodel();

            viewHodel.locationTitleTextView = (TextView) convertView.findViewById(R.id.adressTitleTextView);

            viewHodel.locationSubTitleTextView = (TextView) convertView.findViewById(R.id.adressSubTitleTextView);

            viewHodel.arrowImageView = (ImageView) convertView.findViewById(R.id.arrowImageView);

            convertView.setTag(viewHodel);

        }else{

            viewHodel = (ViewHodel) convertView.getTag();


        }

        MjPoiItem poiItem = cityList.get(position);

        viewHodel.locationTitleTextView.setText(poiItem.getSnippet());

        viewHodel.locationSubTitleTextView.setText(poiItem.getTitle());

        if (poiItem.getSnippet() == null || poiItem.getSnippet().equals(poiItem.getTitle())){
            viewHodel.locationSubTitleTextView.setVisibility(View.GONE);
            viewHodel.locationTitleTextView.setSingleLine(false);
        }else{
            viewHodel.locationTitleTextView.setVisibility(View.VISIBLE);
            viewHodel.locationSubTitleTextView.setVisibility(View.VISIBLE);
            viewHodel.locationTitleTextView.setSingleLine(true);
            viewHodel.locationSubTitleTextView.setSingleLine(true);
        }


        if (selPostion==position){
            viewHodel.arrowImageView.setVisibility(View.VISIBLE);
        }else {
            viewHodel.arrowImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHodel{

        TextView locationTitleTextView;

        TextView locationSubTitleTextView;

        ImageView arrowImageView;
    }
}
