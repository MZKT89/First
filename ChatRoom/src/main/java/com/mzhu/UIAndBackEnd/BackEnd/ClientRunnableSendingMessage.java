package com.mzhu.UIAndBackEnd.BackEnd;

import com.mzhu.pojo.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class ClientRunnableSendingMessage implements Runnable {
    private Socket socket;
    private User user;
    private String message;
    private BufferedWriter bw;
    @Override
    public void run() {
        System.out.println("用户端发送线程创建");
        try {
            //获取输出流
            //  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(message);
            System.out.println("客户端写出数据 "+message+" 到服务端");
            bw.newLine();
            bw.flush();
            //往服务器写出结束标记
            //socket.shutdownOutput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ClientRunnableSendingMessage(Socket socket, User user, String message,BufferedWriter bw) {
        this.socket = socket;
        this.user = user;
        this.message = message;
        this.bw = bw;
    }
}
