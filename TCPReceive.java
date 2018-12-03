package com.test.NetCode.TCP.ChatRoom;


import java.io.*;
import java.net.Socket;

public class TCPReceive implements Runnable{
    DataInputStream dis;
    String msg="";

    public TCPReceive(Socket client) {
        try {
            dis =new DataInputStream(client.getInputStream());
        }catch (Exception e){
            release();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                msg = dis.readUTF();
                System.out.println(msg);
            } catch (Exception e) {
                release();
            }
        }
    }
    //关闭资源
    private void release () {
        UtilsClose.close(dis);
    }
}
