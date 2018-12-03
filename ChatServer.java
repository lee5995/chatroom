package com.test.NetCode.TCP.ChatRoom;

import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    static CopyOnWriteArrayList<Channel> all=new CopyOnWriteArrayList();
    ServerSocket server;
    Socket client;

    public ChatServer(ServerSocket server){
        this.server=server;
    }

    public void start(){
        while(true) {
            try {
                client = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("一个客户端已连接");
            Channel channel = new Channel(client);
            all.add(channel);
            new Thread(channel).start();
        }
    }

    //多线程Channel
    static class Channel implements Runnable{
        private DataInputStream dis;
        private DataOutputStream dos;
        Socket client;
        String msg="";

        public Channel(){
        }

        public Channel( Socket client){
            this.client=client;
        }

        //1.发送消息
        private void send(String msg) {
            try {
                dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF(msg);
                dos.flush();
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }

        //群聊：发送给其他client
        private void sendOthers(){
            for(Channel c : all){
                if (c == this) {
                    continue;
                }
                c.send(msg);
            }
        }

        //2.接收消息
        private  void receive(){
            try{
                dis = new DataInputStream(client.getInputStream());
                msg=dis.readUTF();
            }catch (Exception e){
                System.out.println("ERROR");
            }
        }
        //3.关闭资源
        private void release(){
            UtilsClose.close(dis,dos);
        }

        @Override
        public void run() {
            while(true){
                receive();
                sendOthers();
                if (msg.equals("bye"))
                    break;
            }
            release();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server=new ServerSocket(9999);
        ChatServer chatServer=new ChatServer(server);
        chatServer.start();
    }
}