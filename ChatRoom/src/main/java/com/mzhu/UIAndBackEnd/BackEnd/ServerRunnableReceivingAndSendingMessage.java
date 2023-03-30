package com.mzhu.UIAndBackEnd.BackEnd;

import com.mzhu.pojo.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerRunnableReceivingAndSendingMessage implements Runnable{
    private Socket socket;
    private ArrayList<Socket> list;

    public ServerRunnableReceivingAndSendingMessage() {
    }

    @Override
    public void run() {
        //服务端接受后具体实现
        //点击发送按钮，发送文本域中的内容
        System.out.println("服务端接受并发送线程创建成功");
        try {

            while(true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("输入流创建成功");
                //接收信息
                String message = br.readLine();
                System.out.println(message.substring(1));
                for (Socket s : list) {
                    //s依次表示每一个客户端的连接对象
                    //发送信息给socket list里的每一个客户端
                    writeMessageToClient(s,message);
                }
            }
        } catch (IOException e) {
            System.out.println("获取输入流失败");
        }
         /*  String str;
            while (true) {
                String message = br.readLine();
                System.out.println(user.getUsername() + "发送过来消息：" + message);
                //群发
                while((str = br.readLine()) != null) {
                    for (Socket s : list) {
                        //s依次表示每一个客户端的连接对象
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                        bw.write(str);
                        bw.newLine();
                        bw.flush();
                    }
                }
//                while ((str = br.readLine()) != null) {
//                    for (Socket s : list) {
//                        System.out.println("向用户写出行数据");
//                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
//                        bw.write(str);
//                        bw.newLine();
//                        bw.flush();
//                    }
                }

          */

    }
    public void writeMessageToClient(Socket s, String message) throws IOException {
        //获取输出流
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write(message);
        bw.newLine();
        bw.flush();
    }
    public ServerRunnableReceivingAndSendingMessage(Socket socket, ArrayList<Socket> list) {
        this.socket = socket;
        this.list = list;
    }



    /**
     * 获取
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * 设置
     * @param socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * 获取
     * @return list
     */
    public ArrayList<Socket> getList() {
        return list;
    }

    /**
     * 设置
     * @param list
     */
    public void setList(ArrayList<Socket> list) {
        this.list = list;
    }

    public String toString() {
        return "ServerRunnableReceivingAndSendingMessage{socket = " + socket + ", list = " + list + "}";
    }
}
