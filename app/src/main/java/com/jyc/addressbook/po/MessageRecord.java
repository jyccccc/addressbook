package com.jyc.addressbook.po;

import org.litepal.crud.DataSupport;

public class MessageRecord extends DataSupport {
    private Integer id;  // 短信编号
    private String name;  // 通信人名称，默认值：phoneNumber
    private String phone;  // 电话号码
    private String time;  // 短信到达或发送时间
    private String context;  // 短信内容

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
