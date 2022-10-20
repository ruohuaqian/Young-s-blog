package EffectiveJava;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author xijin yang
 * @date 2022/10/19
 */
public class Elvis implements Serializable {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {

    }

    private final String[] favoriteSongs = {"Hound Dog", "Heartbreak Hotel"};

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
