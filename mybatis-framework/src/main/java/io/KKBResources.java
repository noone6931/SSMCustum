package io;

import java.io.InputStream;

public class KKBResources {

    public static InputStream getResourceAsStream(String location) {
        return KKBResources.class.getClassLoader().getResourceAsStream(location);
    }

}
