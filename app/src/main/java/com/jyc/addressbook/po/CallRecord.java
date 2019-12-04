package com.jyc.addressbook.po;

import org.litepal.crud.DataSupport;

public class CallRecord extends DataSupport {
    private Integer id;  // 通话记录编号
    private String name;  // 通话对象名字，默认值 phoneNumber
    private String phone;  // 电话号码
    private String time;  // 通话到达或发起时间


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

}
