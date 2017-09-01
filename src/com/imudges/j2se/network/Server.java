package com.imudges.j2se.network;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private Socket socket;
    //声明一个ServerSocket对象
    private ServerSocket serverSocket;
    //输入流
    private BufferedReader bufferedReader;
    //输出流
    private PrintWriter printWriter;
    //储存所有连接的socket
    private Map<String ,Socket> clientSocket = new HashMap<>();

    private Gson gson = new Gson();
    /**
     * 构造函数
     * */
    public Server() {}


    public static void main(String args[]){
        Server server = new Server();
        server.getServer();
    }

    /**
     * 获取连接
     * */
    public void getServer(){
        try {
            serverSocket = new ServerSocket(6666);//绑定的端口为2017，此端口要与客户端请求的一致
            while(true){
                System.err.println("等待客户端连接......");
                //从队列中取出Socket或等待连接
                socket = serverSocket.accept();

                if (socket.isConnected()){
                    System.out.println("连接成功！");
                }
                //开新的线程，为这个socket服务
                new HandlerThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现一个Runnable接口，处理和client的一些事物
     * */
    private class HandlerThread implements Runnable{
        private Socket socket;

        public HandlerThread(Socket socket) {
            this.socket = socket;
            new Thread(this).start();
        }

        @Override
        public void run() {
            try {

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //处理客户端发来的数据
                String clientMsg = null;
                while ((clientMsg = bufferedReader.readLine())!=null){
                    System.out.println("客户端发来的消息：" + clientMsg);
                    //向客户端回复信息
                    printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("ok");
                    printWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.err.println("服务器finally异常，异常信息：" + e.getMessage());
                    }
                }
            }
        }
    }
}
