package com.hwj.repository;

import com.hwj.entity.MindMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface MindMapRepository extends JpaRepository<MindMap,String> , PagingAndSortingRepository<MindMap,String> {


    MindMap queryMindMapByMapid(String mapid);

    @Transactional
    @Modifying
    @Query(value = "delete from mind_map where mapid = ?1 ",nativeQuery = true)
    void deleteMindMapByMapid(String mapid);


    @Query(value = "select id, mapid, mapname, null as map_list, userid, create_date, update_date from mind_map " +
                    "where 1=1 " +
                    "and (case when :userid != '' then userid = :userid else 1=1 end ) " +
                    "and (case when :mapname != '' then mapname like CONCAT('%',:mapname,'%') else 1=1 end ) " +
                    "and (case when :firstDate != '' and :lastDate != '' then create_date between :firstDate and date_add(:lastDate,interval 1 day ) else 1=1 end ) " +
                    "order by ?#{#pageable}",
        countQuery = "select count(*) from mind_map " +
                    "where 1=1 " +
                    "and (case when :userid != '' then userid = :userid else 1=1 end ) " +
                    "and (case when :mapname != '' then mapname like CONCAT('%',:mapname,'%') else 1=1 end ) " +
                    "and (case when :firstDate != '' and :lastDate != '' then create_date between :firstDate and date_add(:lastDate,interval 1 day ) else 1=1 end ) ",
        nativeQuery = true)
    Page<MindMap> queryMindMapByPage(@Param("userid") String userid, @Param("mapname") String mapname,
                                     @Param("firstDate") Date firstDate, @Param("lastDate") Date lastDate, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "update mind_map set map_list = :mapList, update_date = :updateDate, " +
            "mapname = IF(:mapname = '', mapname, :mapname) " +
            " where id = :id ", nativeQuery = true)
    void updateMindMap(@Param("mapList") String mapList, @Param("updateDate") Date updateDate,
                       @Param("mapname") String mapname, @Param("id") Integer id);



}
