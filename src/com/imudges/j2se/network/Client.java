package com.imudges.j2se.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    public String msg = "";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public void connect(){

        try {
            //设置服务器IP，绑定端口
            socket = new Socket("127.0.0.1",2017);
            System.out.println("连接完成！");

            //向服务器发送数据
            //初始化输出流 用来向服务器传递数据
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            printWriter.write(msg);
            //清空缓冲区的数据流  数据流向：内存->缓冲区->文件（或输出），如果不用.flush()，可能缓冲区内部还有数据残留，.flush()会将缓冲区内部的数据强制输出
            printWriter.flush();
            //关闭输出流，但是不关闭网络连接
            socket.shutdownOutput();

            //接收服务器数据
            //初始化输入流 用来获取服务器下发的数据
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String reply = null;
            while(!((reply = bufferedReader.readLine()) ==null)){
                System.out.println("服务器发送的数据为：" + reply);
            }
            //关闭输入流，但是不关闭网络连接
            socket.shutdownInput();


            //彻底关闭资源
            printWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String args[]){
//        Client client = new Client();
//        client.connect();
//    }
}
