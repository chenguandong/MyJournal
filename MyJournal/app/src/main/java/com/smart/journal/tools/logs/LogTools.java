package com.smart.journal.tools.logs;

import com.orhanobut.logger.Logger;

/**
 * @author guandongchen
 * @date 2018/1/5
 */
public class LogTools {
    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void d(Object object) {
        Logger.d(object);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
