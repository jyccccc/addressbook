package com.jyc.addressbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.jyc.addressbook.R;
import com.jyc.addressbook.po.Contactor;

import org.litepal.crud.DataSupport;

import java.util.List;

public class EditContactor extends AppCompatActivity {
    // 修改后的联系人
    private Contactor contactor;
    private TitleBar mTitleBar;
    private EditText forname3;
    private EditText forcompany3;
    private EditText foremail3;
    private EditText forgroup3;
    private EditText forphone3;
    private EditText forringtone3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contactor);

        // 查询数据库，找到要修改的联系人
        String phone = getIntent().getStringExtra("phone");
        contactor =  DataSupport.where("phone = ?",phone).find(Contactor.class).get(0);


        // 设置每个EditText的初始值
        forname3 = (EditText) findViewById(R.id.forname3);
        forcompany3 = (EditText) findViewById(R.id.forcompany3);
        foremail3 = (EditText) findViewById(R.id.foremail3);
        forgroup3 = (EditText) findViewById(R.id.forgroup3);
        forphone3 = (EditText) findViewById(R.id.forphone3);
        forringtone3 = (EditText) findViewById(R.id.forringtone3);

        forcompany3.setText(contactor.getCompany());
        foremail3.setText(contactor.getEmail());
        forgroup3.setText(contactor.getGroupname());
        forphone3.setText(contactor.getPhone());
        forname3.setText(contactor.getName());
        forringtone3.setText(contactor.getRingtone());

        mTitleBar = (TitleBar) findViewById(R.id.titleBar3);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                // 取消修改联系人信息
                finish();
            }
            @Override
            public void onTitleClick(View v) { }
            @Override
            public void onRightClick(View v) {
                // 修改联系人信息,返回到主活动,数据库
                changeContactorInfo();
                contactor.save();
                Toast.makeText(EditContactor.this,"修改成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditContactor.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    
    public void changeContactorInfo() {
        contactor.setCompany(forcompany3.getText().toString());
        contactor.setEmail(foremail3.getText().toString());
        contactor.setGroupname(forgroup3.getText().toString());
        contactor.setPhone(forphone3.getText().toString());
        contactor.setName(forname3.getText().toString());
        contactor.setRingtone(forringtone3.getText().toString());
    }
}
