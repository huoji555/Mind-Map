package com.hwj.serviceImpl;

import com.hwj.entity.LoginRecord;
import com.hwj.repository.LoginRecordRepository;
import com.hwj.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {

    @Autowired
    private LoginRecordRepository loginRecordRepository;


    @Override
    public void save(LoginRecord loginRecord) { loginRecordRepository.save(loginRecord); }

    @Override
    public Page<LoginRecord> queryLoginRecordByDate(Date firstDate, Date lastDate, Pageable pageable) {
        return loginRecordRepository.queryPageByDate(firstDate, lastDate, pageable);
    }

}
