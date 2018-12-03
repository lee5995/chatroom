package com.test.NetCode.TCP.ChatRoom;

import java.io.*;
import java.net.*;

public class ChatClient {
    int port;
    Socket client;

    public ChatClient(String host,int port){
        this.port=port;
        try {
            client=new Socket(host,port);
        }
        catch (IOException e) {
            System.out.println("服务器连接失败");
            try {
                client.close();
            }catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void start(){
        TCPSend send = new TCPSend(client);
        TCPReceive receive = new TCPReceive(client);
        new Thread(send).start();
        new Thread(receive).start();
    }

    public static void main(String[] args) throws IOException {
        ChatClient chat=new ChatClient("localhost",9999);
        chat.start();
    }
}

