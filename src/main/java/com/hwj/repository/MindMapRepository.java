package com.hwj.repository;

import com.hwj.entity.MindMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface MindMapRepository extends JpaRepository<MindMap,String> , PagingAndSortingRepository<MindMap,String> {


    MindMap queryMindMapByMapid(String mapid);

    @Transactional
    @Modifying
    @Query(value = "delete from mind_map where mapid = ?1 ",nativeQuery = true)
    void deleteMindMapByMapid(String mapid);



}
