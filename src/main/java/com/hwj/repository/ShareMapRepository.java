package com.hwj.repository;

import com.hwj.entity.ShareMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface ShareMapRepository extends PagingAndSortingRepository<ShareMap, String> {

    @Transactional
    @Modifying
    @Query(value = "delete from share_map where mapid = ?1 ",nativeQuery = true)
    void delShareMap(String mapid);


    @Query(value = "select * from share_map " +
                    "where 1=1 " +
                    "and (case when share_id != '' then share_id = :share_id else 1=1 end ) " +
                    "and (case when mapname != '' then mapname like CONCAT('%',:mapname,'%') else 1=1 end ) " +
                    "and (case when :firstDate != '' and :lastDate != '' then share_date between :firstDate and date_add(:lastDate,interval 1 day ) else 1=1 end ) " +
                    "order by ?#{#pageable}",
            countQuery = "select count(*) from share_map " +
                    "where 1=1 " +
                    "and (case when share_id != '' then share_id = :share_id else 1=1 end ) " +
                    "and (case when mapname != '' then mapname like CONCAT('%',:mapname,'%') else 1=1 end ) " +
                    "and (case when :firstDate != '' and :lastDate != '' then share_date between :firstDate and date_add(:lastDate,interval 1 day ) else 1=1 end ) ",
            nativeQuery = true)
    Page<ShareMap> getShareMapByPage(@Param("share_id") String share_id, @Param("mapname") String mapname,
                                     @Param("firstDate")Date firstDate, @Param("lastDate") Date lastDate, Pageable pageable);



}
