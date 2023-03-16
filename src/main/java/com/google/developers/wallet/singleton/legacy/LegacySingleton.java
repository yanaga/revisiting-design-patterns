package com.google.developers.wallet.singleton.legacy;

public class LegacySingleton {

    private static final LegacySingleton INSTANCE = new LegacySingleton();

    public static LegacySingleton getInstance() {
        return INSTANCE;
    }

}
