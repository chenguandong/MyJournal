package com.smart.journal.tools.user

import com.blankj.utilcode.util.SPUtils
import com.smart.journal.contants.SPContancts

/**
 * @author guandongchen
 * @date 2018/9/5
 */
object UserTools {

    val lockCode: String
        get() = SPUtils.getInstance().getString(SPContancts.LOCK_CODE)

    fun saveLockCode(lockCode: String) {

        SPUtils.getInstance().put(SPContancts.LOCK_CODE, lockCode)
    }

    fun deleteLockCode() {
        SPUtils.getInstance().remove(SPContancts.LOCK_CODE)
    }
}
