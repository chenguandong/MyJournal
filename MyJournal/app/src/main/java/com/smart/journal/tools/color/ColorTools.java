package com.smart.journal.tools.color;

import java.util.Random;

/**
 * @author guandongchen
 * @date 2018/12/6
 */
public class ColorTools {
    public static int getRandomColor(){
        Random myRandom = new Random();
        int ranColor = 0xff000000 | myRandom.nextInt(0x00ffffff);
        return ranColor;

    }
}
