package com.hwj.repository;

import com.hwj.entity.LoginRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LoginRecordRepository extends PagingAndSortingRepository<LoginRecord, String> {


    @Query( value = "SELECT * from Login_record " +
                    "where 1=1 " +
                    "and (case when :firstDate != '' and :lastDate != '' then login_time between :firstDate and date_add(:lastDate,interval 1 day ) else 1=1 end )" +
                    "order by ?#{#pageable}",
            countQuery = "SELECT count(*) from Login_record " +
                     "where 1=1 " +
                     "and (case when :firstDate != '' and :lastDate != '' then login_time between :firstDate and date_add(:lastDate,interval 1 day ) else 1=1 end )",
            nativeQuery = true)
    Page<LoginRecord> queryPageByDate(@Param("firstDate") Date firstDate, @Param("lastDate") Date lastDate, Pageable pageable);


}
