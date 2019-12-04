package com.jyc.addressbook.dao.Impl;

import java.util.List;

import com.jyc.addressbook.dao.MessageRecordDao;
import com.jyc.addressbook.po.Contactor;
import com.jyc.addressbook.po.MessageRecord;

import org.litepal.crud.DataSupport;

public class MessageRecordDaoImpl implements MessageRecordDao {
    @Override
    public boolean addMessageRecord(MessageRecord messageRecord) {
        return messageRecord.save();
    }

    @Override
    public long deleteMessageRecord(MessageRecord messageRecord) {

        return DataSupport.deleteAll(MessageRecord.class,
                "id=?",messageRecord.getId().toString());
    }

    @Override
    public List<MessageRecord> findRecordByContactor(Contactor contactor) {
        List<MessageRecord> list = DataSupport.
                where("phone=?",contactor.getPhone()).find(MessageRecord.class);
        return null;
    }
}
