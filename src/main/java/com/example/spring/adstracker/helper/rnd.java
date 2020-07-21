package com.example.spring.adstracker.helper;

/**
 *
 * @author trident
 */
public class rnd {
        
    public static int getRNDValue(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
