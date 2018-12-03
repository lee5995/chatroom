package com.test.NetCode.TCP.ChatRoom;

import java.io.*;
import java.net.Socket;


public class TCPSend implements  Runnable {
    Socket client;
    DataOutputStream dos;
    BufferedReader br;
    String msg="";

    public TCPSend(){}

    public TCPSend( Socket client) {
        this.client=client;
        try {
            br=new BufferedReader(new InputStreamReader(System.in));
            dos=new DataOutputStream(client.getOutputStream());
        }catch (Exception e){
            release();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                msg = br.readLine();
                dos.writeUTF(msg);
                dos.flush();
            } catch (Exception e) {
                release();
            }
            if (msg.equals("bye")) {
                break;
            }
        }
        release();
    }

    //关闭资源
    private void release () {
        UtilsClose.close(dos,br,client);
    }
}