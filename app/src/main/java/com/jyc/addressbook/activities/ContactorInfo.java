package com.jyc.addressbook.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.jyc.addressbook.R;
import com.jyc.addressbook.po.CallRecord;
import com.jyc.addressbook.po.Contactor;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactorInfo extends AppCompatActivity implements View.OnClickListener{

    Contactor contactor;

    ImageButton editBtn;
    ImageButton callBtn;
    ImageButton msgBtn;
    ImageButton deleteBtn;

    TextView name;
    TextView phone;
    TextView group;
    TextView company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactor_info);

        // 根据传过来的phone属性，找到对应的联系人
        Intent intent = getIntent();
        String phone = intent.getStringExtra("contactor");
        // 调用数据库
        contactor =  DataSupport.where("phone = ?",phone).find(Contactor.class).get(0);

        // 将联系人信息显示
        showContactorInfo();

        // 绑定按钮事件
        editBtn = (ImageButton) findViewById(R.id.edit);
        callBtn = (ImageButton) findViewById(R.id.call);
        msgBtn = (ImageButton) findViewById(R.id.text);
        deleteBtn = (ImageButton) findViewById(R.id.delete);
        editBtn.setOnClickListener(this);
        callBtn.setOnClickListener(this);
        msgBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        TitleBar mTitleBar = (TitleBar) findViewById(R.id.titleBar1);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                // 返回
                finish();
            }
            @Override
            public void onTitleClick(View v) { }
            @Override
            public void onRightClick(View v) { }
        });
    }

    private void showContactorInfo() {
        name = (TextView) findViewById(R.id.forname1);
        phone = (TextView) findViewById(R.id.forphone1);
        company = (TextView) findViewById(R.id.forgroup1);
        group = (TextView) findViewById(R.id.forcompany1);
        name.setText(contactor.getName());
        phone.setText(contactor.getPhone());
        company.setText(contactor.getCompany());
        group.setText(contactor.getGroupname());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit:
                // 跳转到编辑页面
                Intent intent = new Intent(ContactorInfo.this,EditContactor.class);
                intent.putExtra("phone",contactor.getPhone());
                startActivity(intent);
                break;
            case R.id.call:
                // 拨号
                callContactor();
                break;
            case R.id.text:
                // 跳转到短信界面，并置短信收件人为这个人
                intent = new Intent(ContactorInfo.this,MakeMessage.class);
                intent.putExtra("phone",contactor.getPhone());
                startActivity(intent);
                break;
            case R.id.delete:
                // 弹出是否删除的询问框
                deleteContactor();
                break;
        }
    }

    private void deleteContactor() {
        AlertDialog dialog = new AlertDialog.Builder(ContactorInfo.this)
                .setMessage("是否确定删除该联系人？")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 根据contactor的phone在数据库中删除联系人
                        String phone = contactor.getPhone();
                        DataSupport.deleteAll(Contactor.class,"phone = ?",phone);
                        // 返回主程序
                        Intent intent = new Intent(ContactorInfo.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消",null)
                .show();
        // 显示按钮
        Button buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(ContactorInfo.this, R.color.colorPrimary));
        buttonNegative.setTextColor(ContextCompat.getColor(ContactorInfo.this, R.color.colorPrimary));
    }

    private void callContactor() {
        String phone = contactor.getPhone();
        // 拨打电话
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));

        // 添加到数据库中
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        CallRecord callRecord = new CallRecord();
        callRecord.setTime(simpleDateFormat.format(date));
        callRecord.setPhone(phone);
        callRecord.setName(contactor.getName());
        callRecord.save();

        // 获取权限
        if (ContextCompat.checkSelfPermission(ContactorInfo.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactorInfo.this,
                    new String[]{Manifest.permission.CALL_PHONE}, 3);
        }
        startActivity(callIntent);
    }
}
