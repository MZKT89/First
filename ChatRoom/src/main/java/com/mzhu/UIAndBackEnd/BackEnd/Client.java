package com.mzhu.UIAndBackEnd.BackEnd;

import com.mzhu.UIAndBackEnd.UI.ChatRoomMain;
import com.mzhu.pojo.User;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Client {
    private User user;
    Socket socket;
    ChatRoomMain chatRoomUI;
    ThreadPoolExecutor pool = new ThreadPoolExecutor(5,20,60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );
    public Client(User user) {
        this.user = user;
        //客户端已打开
        System.out.println("打开客户端");
        try {
            socket = new Socket("127.0.0.1", 10086);
            System.out.println("服务端连接成功");
            //获取输出流
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            //传给服务端的输出流
//            BufferedOutputStream outStreamToServer = new BufferedOutputStream(socket.getOutputStream());
//            BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
            //打开聊天界面
            chatRoomUI = new ChatRoomMain(socket,user,bw);
            //创建线程接收服务端群发和回写的信息
            pool.submit(new ClientRunnableReceivingMessage(socket,chatRoomUI,br));
            //获取输入流
            //BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("客户端对象创建失败");
        }
    }


}
