package EffectiveJava;

import java.io.Serializable;

import EffectiveJava.Elvis;

public class ElvisStealer implements Serializable {
    static Elvis impersonator;
    private Elvis payload;

    private Object readResolve() {
        impersonator = payload;
        return new String[]{"A Fool such as I"};
    }

    private static final long serialVersionUID = 0;
}
