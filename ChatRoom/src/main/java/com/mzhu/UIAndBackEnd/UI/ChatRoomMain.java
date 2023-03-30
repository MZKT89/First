/*
 * Created by JFormDesigner on Thu Mar 30 14:58:52 CST 2023
 */

package com.mzhu.UIAndBackEnd.UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.net.Socket;
import javax.swing.*;

import com.mzhu.UIAndBackEnd.BackEnd.ClientRunnableSendingMessage;
import com.mzhu.pojo.User;

import net.miginfocom.swing.*;

/**
 * @author zrh
 */
public class ChatRoomMain extends JFrame implements MouseListener, KeyListener {
    private User user;
    private Socket socket;
    private BufferedWriter bw;


    public ChatRoomMain(Socket socket, User user, BufferedWriter bw) {
        this.user = user;
        this.socket = socket;
        this.bw = bw;
        initComponents();
    }

    public ChatRoomMain() {
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        textPane1 = new JTextPane();
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        button1 = new JButton();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/background.png")).getImage());
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[631,fill]" +
                        "[3,fill]" +
                        "[61,fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[45,fill]" +
                        "[fill]" +
                        "[125,fill]" +
                        "[47,fill]" +
                        "[47,fill]" +
                        "[81,fill]" +
                        "[61,fill]" +
                        "[fill]",
                // rows
                "[435]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));

        //======== scrollPane1 ========
        {

            //---- textPane1 ----
            textPane1.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
            textPane1.setEditable(false);
            scrollPane1.setViewportView(textPane1);
        }
        contentPane.add(scrollPane1, "cell 0 0 11 10,grow");

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());
        }
        contentPane.add(panel1, "cell 1 0 12 10");

        //---- label1 ----
        label1.setText("text");
        label1.setIcon(new ImageIcon(getClass().getResource("/background.png")));
        contentPane.add(label1, "cell 1 0 12 10,grow,wmin 7,hmin 20");
        contentPane.add(textField1, "cell 0 10 11 1,grow");

        //---- button1 ----
        button1.setText("Send");
        contentPane.add(button1, "cell 11 10 2 1");
        setSize(975, 694);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
        //设置聊天信息框
        textPane1.addMouseListener(this);
        scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        //设置信息输入框
        textField1.addKeyListener(this);
        //设置发送按钮
        button1.addMouseListener(this);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    StringBuilder sb = new StringBuilder("");

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            System.out.println("回车发送");
            //发送
            //获取聊天框中的信息
            getTextAndSending();
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JComponent source = (JComponent) e.getSource();
        if (source == this.button1) {
            System.out.println("发送按钮被点击");
            //发送
            //获取聊天框中的信息
            getTextAndSending();
        }
    }

        @Override
        public void mouseEntered (MouseEvent e){

        }

        @Override
        public void mouseExited (MouseEvent e){

        }

        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
        private JScrollPane scrollPane1;
        private JTextPane textPane1;
        private JPanel panel1;
        private JLabel label1;
        private JTextField textField1;
        private JButton button1;
        // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
        public void processReceivedMessage(String message) {
            int splitSymbolPosition = message.indexOf("&");
            String usernameOfMessage = message.substring(0, splitSymbolPosition);
            String content = message.substring(splitSymbolPosition + 1);
            System.out.println(usernameOfMessage + ": " + content);
            textPane1.setText(textPane1.getText() + "\n" + usernameOfMessage + ": " + content);
            //  textPane1.setText(textPane1.getText() + "&" +message);
        }
    private void getTextAndSending() {
        String text = textField1.getText();
        if (text != null) {
            System.out.println(text);
            sb.append(user.getUsername() + "&" + text);
            String ultimateText = sb.toString();
            sb.setLength(0);
            System.out.println(ultimateText);
            System.out.println("准备进入线程");
            //创建发送信息线程
            new Thread(new ClientRunnableSendingMessage(socket, user, ultimateText, bw)).start();
            System.out.println("更新输入框");
            this.textField1.setText("");
        }
    }
    }
