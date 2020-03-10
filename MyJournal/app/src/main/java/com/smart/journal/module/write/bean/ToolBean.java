package com.smart.journal.module.write.bean;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class ToolBean implements Serializable{

    @StringDef({ToolBeanType.TOOL_IMAGE,ToolBeanType.TOOL_LOCATION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ToolBeanType{
        /**
         * 图片
         */
        String TOOL_IMAGE = "Image";
        String TOOL_LOCATION = "Location";
        String TOOL_MORE = "More";
    }

    private int icon;
    private boolean selected;
    @ToolBeanType
    private String itemType;

    public ToolBean(int icon, boolean selected) {
        this.icon = icon;
        this.selected = selected;
    }

    public ToolBean(int icon,@ToolBeanType String itemType) {
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
