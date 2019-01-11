package com.hwj.repository;

import com.hwj.entity.Zsd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Table;
import javax.transaction.Transactional;

public interface ZsdRepository extends JpaRepository<Zsd,String> {

    Zsd findZsdByNodeid(String nodeId);

    @Transactional
    @Modifying
    @Query(value = "update zsd set zsdms = :zsdms where nodeid = :nodeid",nativeQuery = true)
    void updateZsd(@Param("zsdms") String zsdms, @Param("nodeid") String nodeid);

    @Transactional
    @Modifying
    @Query(value = "delete from zsd where nodeid = ?1",nativeQuery = true)
    void deleteZsdByNodeid(String nodeid);

    @Transactional
    @Modifying
    @Query(value = "delete from zsd where mapid = ?1",nativeQuery = true)
    void deleteByMapid(String mapid);


}
