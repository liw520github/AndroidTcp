package com.lenz.arouter.xtcptest;


import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClientThread extends Thread {
    //ip地址
    private String address;
    //端口号
    private int port;
    //发送内容
    private String msg;
    private Handler mHandler;

    public TcpClientThread(String address, int port, String msg, Handler handler) {
        this.address = address;
        this.port = port;
        this.msg = msg;
        mHandler = handler;
    }

    @Override
    public void run() {
        super.run();
        sendSocket();
    }

    private void sendSocket() {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        Socket socket = null;
        try {
            //创建监听指定服务器地址以及指定服务器监听的端口号
            socket = new Socket(address, port);
            //拿到客户端的socket对象的输出流发送给服务器数据
            OutputStream os = socket.getOutputStream();
            //写入要发送给服务器的数据
            os.write(msg.getBytes());
            os.flush();
            socket.shutdownOutput();
            //拿到socket的输入流，这里存储的是服务器返回的数据
            InputStream is = socket.getInputStream();
            //解析服务器返回的数据
            inputStreamReader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(inputStreamReader);
            String s =null;
            final StringBuffer sb = new StringBuffer();
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s);
            }
            sendMsg(0,sb.toString());
            //关闭io资源
            if (socket!=null){
                socket.close();
            }
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (os!=null){
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//发送消息
    private void sendMsg(int i, Object object) {
        Message message =new Message();
        message.what =i;
        message.obj =object;
        mHandler.sendMessage(message);
    }
}
