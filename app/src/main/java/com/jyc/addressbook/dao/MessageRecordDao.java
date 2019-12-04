package com.jyc.addressbook.dao;

import java.util.List;

import com.jyc.addressbook.po.Contactor;
import com.jyc.addressbook.po.MessageRecord;

public interface MessageRecordDao {
    // 插入短信记录
    public  boolean addMessageRecord(MessageRecord MessageRecord);

    // 删除短信记录
    public long deleteMessageRecord(MessageRecord MessageRecord);

    // 查询短信记录
    public List<MessageRecord> findRecordByContactor(Contactor contactor);
}
