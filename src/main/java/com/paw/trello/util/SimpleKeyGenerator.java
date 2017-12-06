package com.paw.trello.util;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SimpleKeyGenerator implements KeyGenerator {

    private static class LazyHolder {

        static final SimpleKeyGenerator INSTANCE = new SimpleKeyGenerator();
    }
    public static SimpleKeyGenerator getInstance() {
        return LazyHolder.INSTANCE;
    }

    public SimpleKeyGenerator() {
        super();
    }

    @Override
    public Key generateKey() {
        String keyString = "simplekey";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}
