package com.hwj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hwj.entity.AdminErrorMsg;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AdminErrorMsgRepository extends PagingAndSortingRepository<AdminErrorMsg, Integer>{


    @Query(value = "select * from admin_error_msg " +
            "where 1=1 " +
            "and (case when :firstDate != '' and :lastDate != '' then create_time between :firstDate and date_add(:lastDate,interval 1 day ) else 1=1 end )" +
            "order by ?#{#pageable}",
    countQuery = "select count(*) from admin_error_msg " +
            "where 1=1 " +
            "and (case when :firstDate != '' and :lastDate != '' then create_time between :firstDate and date_add(:lastDate,interval 1 day ) else 1=1 end )",
    nativeQuery = true)
    Page<AdminErrorMsg> queryErrorMsg(@Param("firstDate") Date firstDate, @Param("lastDate") Date lastDate, Pageable pageable);

}
