package com.hwj.service;

import com.hwj.entity.LoginRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface LoginRecordService {

    void save(LoginRecord loginRecord);

    Page<LoginRecord> queryLoginRecordByDate(Date firstDate, Date lastDate, Pageable pageable);

}
