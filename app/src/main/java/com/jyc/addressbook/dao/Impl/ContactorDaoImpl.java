package com.jyc.addressbook.dao.Impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.jyc.addressbook.dao.ContactorDao;
import com.jyc.addressbook.po.Contactor;

import org.litepal.crud.DataSupport;


public class ContactorDaoImpl implements ContactorDao {

    /**
     * 包括有条件和无条件查询
     * @param contactor
     * @return
     */
    @Override
    public List<Contactor> findContactor(Contactor contactor) {
        // 查询条件，有条件查询包括了按姓名和名字（可拓展）
        StringBuilder stats = new StringBuilder("1=1");
        // 使用模糊查询，若需按name查询
        if (contactor.getName() != null && contactor.getName() != "") {
            stats.append("and name like %"+contactor.getName()+"%");
        }
        // 若需按GroupName查询
        if (contactor.getGroupname() != null && contactor.getGroupname() != "") {
            stats.append("and groupname="+contactor.getGroupname());
        }
        // 若需按id查询
        if (contactor.getId() != null && contactor.getId() != 0) {
            stats.append("and id="+contactor.getId());
        }
        // 获取查询结果
        List<Contactor> list = DataSupport.where(stats.toString()).find(Contactor.class);
        return list;
    }

    /**
     * 添加联系人
     * @param contactor
     * @return
     */
    @Override
    public boolean addContactor(Contactor contactor) {
        // 保存联系人
        return contactor.save();
    }

    /**
     * 修改联系人信息，包括添加到群组中、从群组中删除和移动群组
     * @param contactor
     */
    @Override
    public long updateContactor(Contactor contactor) {
        // 根据主键来更新
        return contactor.updateAll("id",contactor.getId().toString());
    }

    /**
     * 删除联系人信息
     * @param contactor
     * @return
     */
    @Override
    public long deleteContactor(Contactor contactor) {
        return DataSupport.deleteAll(Contactor.class,
                "id=",contactor.getId().toString());
    }
}
