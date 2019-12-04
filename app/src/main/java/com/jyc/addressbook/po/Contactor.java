package com.jyc.addressbook.po;


import org.litepal.crud.DataSupport;

public class Contactor extends DataSupport {
    private Integer id;  // 联系人编号
    private String name;  // 联系人名称，默认值为：phoneNumber
    private String phone;  // 联系人电话
    private String company;   // 联系人公司名
    private String groupname;  // 联系人群组
    private String email;   // 电子邮件
    private String ringtone;   // 电话铃声

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setCompany(String company) {
        this.company = company;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }



    public String getRingtone() {
        return ringtone;
    }

}
