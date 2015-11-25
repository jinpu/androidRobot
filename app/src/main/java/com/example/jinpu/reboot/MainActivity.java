package com.example.jinpu.reboot;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Handler;


import bean.chatmessage;
import tools.HttpTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView mMsg;
    private ChatMessageApdapter mAdapter;
    private List<chatmessage> mDatas;
    private EditText mInputmsg;
    private Button msendmsg;
    private chatmessage chatmsg = null;

    //更新主界面
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg)
        {//等待接收子线程的数据
            if (msg.what == 0x1) {
                if (msg.obj != null) {
                    chatmsg = (chatmessage) msg.obj;
                }
                // 添加数据到list中，更新数据
                mDatas.add(chatmsg);
                mAdapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();

        initListener();
        initDatas();

    }
    public  void initview()
    {
        mMsg = (ListView)findViewById(R.id.id_ly_list);
        mInputmsg = (EditText)findViewById(R.id.id_input_msg);
        msendmsg = (Button)findViewById(R.id.id_send_msg);

    }

    public void initDatas()
    {
        mDatas = new ArrayList<chatmessage>();
        mDatas.add(new chatmessage("您好！",chatmessage.Type.INCOMING,new Date()));
        mAdapter = new ChatMessageApdapter(this,mDatas);
        mMsg.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initListener()
    {
        msendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tomsg = mInputmsg.getText().toString();
                if(TextUtils.isEmpty(tomsg))
                {
                    Toast.makeText(MainActivity.this,"消息不能为空！",Toast.LENGTH_SHORT).show();
                    return ;
                }
                chatmessage toMessage = new chatmessage();
                toMessage.setDate(new Date());
                toMessage.setMsg(tomsg);
                toMessage.setType(chatmessage.Type.OUTCOMING);
                mDatas.add(toMessage);
                mAdapter.notifyDataSetChanged();
                mInputmsg.setText("");

                new Thread()
                {
                    public void run()
                    {
                        chatmessage frommsg = HttpTools.sendMessage(tomsg);

                        Message m = new Message();
                        m.what = 0x1;
                        m.obj = frommsg;
                        mHandler.sendMessage(m);
                    };
                }.start();
            }
        });
    }
}
