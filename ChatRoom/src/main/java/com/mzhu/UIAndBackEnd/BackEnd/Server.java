package com.mzhu.UIAndBackEnd.BackEnd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 1、先处理数据库创建一个新的数据库和新的表
 * 2、打开连接，等待客户端连接
 * 3、创建接收发送信息线程
 */

public class Server {
    //存放连接的客户端
    static ArrayList<Socket> list = new ArrayList<>();
    ThreadPoolExecutor pool = new ThreadPoolExecutor(5,20,60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );
    public Server(){
        //服务端打开
        try {




            ServerSocket serverSocket = new ServerSocket(10086);
            while(true) {
                Socket accept = serverSocket.accept();
                System.out.println("客户端已连接");
                //存放客户端
                Server.list.add(accept);
                //创建服务器线程
                pool.submit(new ServerRunnableReceivingAndSendingMessage(accept,list));
            }
        } catch (IOException e) {
            System.out.println("服务器创建失败");
        }

    }

    public static void main(String[] args) {
        new Server();
    }
}
