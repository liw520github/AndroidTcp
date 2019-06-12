package com.lenz.arouter.xtcptest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mEtContent;
    private Button mBtnSend;
    private TextView mTvReceived;
    String address = "192.168.5.172";//和服务端同一局域网的ip,具体获取博客有写。
    int port = 666;//连接服务端的端口号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtContent = findViewById(R.id.et_content);
        mBtnSend = findViewById(R.id.btn_send);
        mTvReceived = findViewById(R.id.tv_received);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TcpClientThread thread =new TcpClientThread(address,port,mEtContent.getText().toString(),mHandler);
                thread.start();//开启线程
            }
        });

    }

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String content = (String) msg.obj;
                    mTvReceived.setText(content);
                    break;
            }
        }
    };
}
