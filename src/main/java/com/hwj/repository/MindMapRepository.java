package com.hwj.repository;

import com.hwj.entity.MindMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MindMapRepository extends JpaRepository<MindMap,String> , PagingAndSortingRepository<MindMap,String> {


    MindMap queryMindMapByMapid(String mapid);



}
