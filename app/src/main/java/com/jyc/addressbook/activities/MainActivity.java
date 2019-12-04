package com.jyc.addressbook.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jyc.addressbook.utilities.LinearItemDecoration;
import com.jyc.addressbook.R;
import com.jyc.addressbook.adapters.CallRecordAdapter;
import com.jyc.addressbook.adapters.ContactorAdapter;
import com.jyc.addressbook.adapters.MessageRecordAdapter;
import com.jyc.addressbook.po.CallRecord;
import com.jyc.addressbook.po.Contactor;
import com.jyc.addressbook.po.MessageRecord;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    // 保存数据库中的联系人
    private List<Contactor> contactors = new ArrayList<Contactor>();
    // 保存数据库中的通话记录
    private List<CallRecord> callRecords = new ArrayList<CallRecord>();
    // 保存数据库中的短信记录
    private List<MessageRecord> messageRecords = new ArrayList<MessageRecord>();
    // 布局管理器
    private LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    // 移动按钮
    private FloatingActionButton mFab;
    // 三种状态,0:call,1:contactors,2:message
    private Integer state;
    // 组件
    private RecyclerView recyclerView;
    private EditText editText;
    // 适配器
    private ContactorAdapter contactorAdapter;
    private CallRecordAdapter callRecordAdapter;
    private MessageRecordAdapter messageRecordAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            editText.setText("");
            switch (item.getItemId()) {
                case R.id.navigation_call:
                    mFab.setVisibility(View.INVISIBLE);
                    // 展示通话记录
                    initCallRecords();
                    // 添加适配器
                    callRecordAdapter = new CallRecordAdapter(callRecords);
                    recyclerView.setAdapter(callRecordAdapter);
                    return true;
                case R.id.navigation_contactor:
                    mFab.setVisibility(View.VISIBLE);
                    state = 1;
                    // 展示所有联系人
                    initContactor();
                    // 添加适配器
                    contactorAdapter = new ContactorAdapter(contactors);
                    recyclerView.setAdapter(contactorAdapter);
                    return true;
                case R.id.navigation_msg:
                    mFab.setVisibility(View.VISIBLE);
                    state = 2;
                    // 显示所有短信记录
                    initMessageRecord();
                    // 添加适配器
                    messageRecordAdapter = new MessageRecordAdapter(messageRecords);
                    recyclerView.setAdapter(messageRecordAdapter);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定底部导航栏按钮操作
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        // 创建数据库
        LitePal.getDatabase();

        // 绑定添加事件
        mFab = (FloatingActionButton) findViewById(R.id.mFab);
        mFab.setVisibility(View.INVISIBLE);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (state) {
                    case 0:  // 显示拨号键盘
                        break;
                    case 1:  // 添加联系人
                        intent = new Intent(MainActivity.this, AddContactor.class);
                        startActivity(intent);
                        break;
                    case 2:  // 发送短信
                        intent = new Intent(MainActivity.this, MakeMessage.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        // 默认打开通话记录
        state = 0;
        // 展示通话记录
        initCallRecords();
        // 添加适配器
        recyclerView = findViewById(R.id.dataView);
        recyclerView.setLayoutManager(layoutManager);
        // 添加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        CallRecordAdapter callRecordAdapter = new CallRecordAdapter(callRecords);
        recyclerView.setAdapter(callRecordAdapter);

        // 默认不弹出搜索联系人输入框
        editText = (EditText) findViewById(R.id.search_bar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        editText.clearFocus();
        // 输入状态监听
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 根据输入的内容搜索联系人（支持电话和名字搜索）
                String input = editText.getText().toString();
                Pattern numberPattern = Pattern.compile("\\d+");
                Matcher matcher = numberPattern.matcher(input);
                // 保存搜索结果
                List<Contactor> result = new ArrayList<Contactor>();
                // 如果是数字
                if (matcher.find()) {
                    // 使用电话号码查询联系人
                    result = DataSupport.where("phone like ?",input).find(Contactor.class);

                } else {
                    // 使用姓名查询联系人
                    result = DataSupport.where("name like ?",input).find(Contactor.class);
                }
                // 显示搜索结果
                contactorAdapter = new ContactorAdapter(result);
                recyclerView.setAdapter(contactorAdapter);
            }
        });
        // 焦点获取状态监听
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // 当获取焦点时，设置输入光标，设置添加按钮消失
                    editText.setCursorVisible(true);
                    mFab.setVisibility(View.INVISIBLE);
                    // 还可以显示历史搜索记录,同样也是保存到数据库中

                } else {
                    // 失去焦点后，取消光标显示
                    editText.setCursorVisible(false);
                    mFab.setVisibility(View.VISIBLE);
                    // 收起软键盘
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }
        });

        // 申请权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, 2);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, 3);
        }
    }

    /**
     * 查找所有联系人信息
     */
    private void initContactor() {
        contactors.clear();
        contactors = DataSupport.findAll(Contactor.class);
    }

    /**
     * 查找所有通话记录
     */
    private void initCallRecords() {
        callRecords.clear();
        callRecords = DataSupport.findAll(CallRecord.class);
    }

    /**
     * 查找所有短信记录
     */
    private void initMessageRecord() {
        messageRecords.clear();
        messageRecords = DataSupport.findAll(MessageRecord.class);
    }
}
