package com.jyc.addressbook.dao;

import java.util.ArrayList;
import java.util.List;

import com.jyc.addressbook.po.Contactor;

public interface  ContactorDao {
    // 添加联系人
    public boolean addContactor(Contactor contactor);

    // 删除联系人
    public long deleteContactor(Contactor contactor);

    // 更新联系人信息
    public long updateContactor(Contactor contactor);

    // 包括有条件和无条件查询,查询满足条件的所有联系人
    public List<Contactor> findContactor(Contactor contactor);

}
