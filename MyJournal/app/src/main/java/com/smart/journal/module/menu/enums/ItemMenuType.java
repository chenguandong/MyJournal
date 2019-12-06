package com.smart.journal.module.menu.enums;

/**
 * @author chenguandong
 * @date 2019/10/24
 * @desc
 * @email chenguandong@outlook.com
 */
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Item 类型
 *
 * @author Administrator
 */
@IntDef({ ItemMenuType.MENU_NOTE_BOOK})
@Retention(RetentionPolicy.SOURCE)
public @interface ItemMenuType {
    /**
     * 日记本
     */
    int MENU_NOTE_BOOK = 0;
}
