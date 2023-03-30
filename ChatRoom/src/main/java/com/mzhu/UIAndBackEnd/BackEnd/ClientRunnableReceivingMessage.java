package com.mzhu.UIAndBackEnd.BackEnd;

import com.mzhu.UIAndBackEnd.UI.ChatRoomMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ClientRunnableReceivingMessage implements Runnable{
    private Socket socket;
    private ChatRoomMain chatRoom;
   // StringBuilder sb = new StringBuilder("");
    private BufferedReader br;

    public ClientRunnableReceivingMessage() {
    }
    @Override
    public void run() {
        while(true){
            //接收服务器发送过来的聊天记录
            try {
                // BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    String str;
//                    while ((str = br.readLine()) != null) {
//                        sb.append(str + "\n");
//                    }
//                    String message = sb.toString();
                String message = br.readLine();
                chatRoom.processReceivedMessage(message);
                //   sb.setLength(0);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    public ClientRunnableReceivingMessage(Socket socket, ChatRoomMain chatRoom, BufferedReader br) {
        this.socket = socket;
        this.chatRoom = chatRoom;
        this.br = br;
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
     * @return chatRoom
     */
    public ChatRoomMain getChatRoom() {
        return chatRoom;
    }

    /**
     * 设置
     * @param chatRoom
     */
    public void setChatRoom(ChatRoomMain chatRoom) {
        this.chatRoom = chatRoom;
    }

    /**
     * 获取
     * @return br
     */
    public BufferedReader getBr() {
        return br;
    }

    /**
     * 设置
     * @param br
     */
    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public String toString() {
        return "ClientRunnableReceivingMessage{socket = " + socket + ", chatRoom = " + chatRoom + ", br = " + br + "}";
    }


}
