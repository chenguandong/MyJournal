package com.smart.weather.tools.user;

import com.blankj.utilcode.util.SPUtils;
import com.smart.weather.contants.SPContancts;

/**
 * @author guandongchen
 * @date 2018/9/5
 */
public class UserTools {

    public static void saveLockCode(String lockCode){

        SPUtils.getInstance().put(SPContancts.LOCK_CODE,lockCode);
    }

    public static  String getLockCode(){

        return SPUtils.getInstance().getString(SPContancts.LOCK_CODE);
    }
}
