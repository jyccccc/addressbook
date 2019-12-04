package com.jyc.addressbook.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jyc.addressbook.R;
import com.jyc.addressbook.adapters.MessageAdapter;
import com.jyc.addressbook.po.Contactor;
import com.jyc.addressbook.po.MessageRecord;

import org.litepal.crud.DataSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MakeMessage extends AppCompatActivity {
    // 保存数据库中的与某个人的短信记录
    private List<MessageRecord> messageRecords = new ArrayList<MessageRecord>();
    // 布局管理器
    private LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    // 按钮
    private ImageButton back;
    private ImageButton send;
    // 适配器
    private MessageAdapter messageAdapter;
    // 输入框
    private EditText receiver;
    private EditText content;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_message);

        receiver = (EditText)findViewById(R.id.receiver);
        content = (EditText) findViewById(R.id.content);
        send = (ImageButton) findViewById(R.id.send);
        recyclerView = findViewById(R.id.msgRecycle);
        back = (ImageButton) findViewById(R.id.back);

        // 如果是从联系人信息那里过来
        if (getIntent().getStringExtra("phone") != null) {
            receiver.setText(getIntent().getStringExtra("phone"));
        }

        // 显示信息
        initMessage();
        messageAdapter = new MessageAdapter(messageRecords);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(layoutManager);

        // 绑定事件
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    send.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                            R.color.colorLightBlue),
                            PorterDuff.Mode.SRC_ATOP);
                } else {
                    send.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                            android.R.color.darker_gray),
                            PorterDuff.Mode.SRC_ATOP);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        // 发送
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgPhone = receiver.getText().toString();
                String msgContent = content.getText().toString();
                // 检查输入内容和号码是否为空
                if (msgContent.length() == 0 || msgPhone.length() == 0) {
                    Toast.makeText(MakeMessage.this,"请先选择联系人或填写内容",Toast.LENGTH_SHORT).show();
                } else {
                    //获取当前时间
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    MessageRecord messageRecord = new MessageRecord();
                    messageRecord.setTime(simpleDateFormat.format(date));
                    messageRecord.setContext(msgContent);
                    messageRecord.setPhone(msgPhone);
                    // 根据电话号码，查询是否此号码已保存了，否则用号码代替名字
                    Contactor contactor = DataSupport.where("phone=?",msgPhone)
                            .find(Contactor.class).get(0);
                    if (contactor.getName().length() != 0) {
                        messageRecord.setName(contactor.getName());
                    } else {
                        messageRecord.setName(msgPhone);
                    }
                    // 显示出来
                    refreshData(messageRecord);
                    // 添加到数据库中
                    messageRecord.save();
                    // 发送信息
                    SmsManager smsManager =SmsManager.getDefault();
                    smsManager.sendTextMessage(msgPhone,null,msgContent,null,null);
                    // 设置内容为空
                    content.setText("");
                }
            }
        });
        // 返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 获取与某个联系人的所有短信记录
    private void initMessage() {
        messageRecords = DataSupport.where("phone = ?",receiver.getText().toString())
                .find(MessageRecord.class);
    }

    // 刷新recyclerView显示的内容
    private void refreshData(MessageRecord messageRecord) {
        messageRecords.add(messageRecord);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
    }


}
