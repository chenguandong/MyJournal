package com.smart.journal.module.journal.tools;

import com.smart.journal.contants.Contancts;

/**
 * @author guandongchen
 * @date 2018/9/6
 */
public class JournalTools {

    /**
     * 获取第一张图片
     * @param content 日记内容
     * @return
     */
    public static String getFistPhoto(String content){
        String contents[] = content.split(Contancts.FILE_TYPE_SPLIT);
        for (String mcontent:
                contents) {
            if (mcontent.startsWith(Contancts.FILE_TYPE_TEXT)){
            }else if (mcontent.startsWith(Contancts.FILE_TYPE_IMAGE)){
                return mcontent.substring(Contancts.FILE_TYPE_IMAGE.length(), mcontent.length());

            }
        }
        return "";
    }
}
