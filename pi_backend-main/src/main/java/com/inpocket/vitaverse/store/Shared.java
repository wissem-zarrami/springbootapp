package com.inpocket.vitaverse.store;

import java.io.File;

public class Shared {
    public final static String UPLOAD_DIRECTORY =
            "C:\\esprit\\projets\\pi_integration\\PIFRONT\\src\\assets\\uploads";
            //System.getProperty("java.io.tmpdir") + "/vitaverse_data";
    static {
        File directory = new File(UPLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
