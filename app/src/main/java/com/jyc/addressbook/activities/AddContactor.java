package com.jyc.addressbook.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.jyc.addressbook.R;
import com.jyc.addressbook.po.Contactor;


public class AddContactor extends AppCompatActivity {
    private TitleBar mTitleBar;
    // 需要添加的联系人
    private Contactor contactor;
    private EditText forname;
    private EditText forcompany;
    private EditText foremail;
    private EditText forgroup;
    private EditText forphone;
    private EditText forringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contactor);

        contactor = new Contactor();
        forname = (EditText) findViewById(R.id.forname);
        forcompany = (EditText) findViewById(R.id.forcompany);
        foremail = (EditText) findViewById(R.id.foremail);
        forgroup = (EditText) findViewById(R.id.forgroup);
        forphone = (EditText) findViewById(R.id.forphone);
        forringtone = (EditText) findViewById(R.id.forringtone);



        mTitleBar = (TitleBar) findViewById(R.id.titleBar);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                // 取消加入联系人
                finish();
            }
            @Override
            public void onTitleClick(View v) { }
            @Override
            public void onRightClick(View v) {
                // 添加联系人到数据库中，并返回
                contactor.setCompany(forcompany.getText().toString());
                contactor.setEmail(foremail.getText().toString());
                contactor.setGroupname(forgroup.getText().toString());
                contactor.setPhone(forphone.getText().toString());
                contactor.setName(forname.getText().toString());
                contactor.setRingtone(forringtone.getText().toString());
                contactor.save();
                finish();
            }
        });

    }
}
