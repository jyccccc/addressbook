package com.jyc.addressbook.dao;

import java.util.List;

import com.jyc.addressbook.po.CallRecord;
import com.jyc.addressbook.po.Contactor;

public interface CallRecordDao {
    // 插入通话记录
    public  boolean addCallRecord(CallRecord callRecord);

    // 删除通话记录
    public long deleteCallRecord(CallRecord callRecord);

    // 查询通话记录
    public List<CallRecord> findRecordByContactor(Contactor contactor);
}
