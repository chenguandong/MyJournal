package com.smart.weather.module.write.Views;

import java.io.Serializable;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class ToolBean implements Serializable{

    public static final String TOOL_WEATHER = "Weather";
    public static final String TOOL_IMAGE = "Image";

    private int icon;
    private boolean selected;
    private String itemType;

    public ToolBean(int icon, boolean selected) {
        this.icon = icon;
        this.selected = selected;
    }

    public ToolBean(int icon, String itemType) {
        this.icon = icon;
        this.itemType = itemType;
    }

    public ToolBean(int icon) {
        this.icon = icon;
    }

    public ToolBean() {
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
