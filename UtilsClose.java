package com.test.NetCode.TCP.ChatRoom;

import java.io.Closeable;

public class UtilsClose {

    public static void close(Closeable...srcs){
        for (Closeable src : srcs) {
            try{
                if (src != null) {
                    src.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
