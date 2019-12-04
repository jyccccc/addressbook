package com.jyc.addressbook.dao.Impl;

import java.util.List;

import com.jyc.addressbook.dao.CallRecordDao;
import com.jyc.addressbook.po.CallRecord;
import com.jyc.addressbook.po.Contactor;

import org.litepal.crud.DataSupport;

public class CallRecordDaoImpl implements CallRecordDao {
    @Override
    public boolean addCallRecord(CallRecord callRecord) {
        return callRecord.save();
    }

    @Override
    public long deleteCallRecord(CallRecord callRecord) {
        return DataSupport.deleteAll(CallRecord.class,
                "id=?",callRecord.getId().toString());
    }

    @Override
    public List<CallRecord> findRecordByContactor(Contactor contactor) {
        List<CallRecord> list = DataSupport
                .where("phone=?",contactor.getPhone()).find(CallRecord.class);
        return list;
    }
}
