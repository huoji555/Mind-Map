package com.hwj.repository;

import com.hwj.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,String> {

    @Query(value = "select * from menu where parentid = '' ", nativeQuery = true)
    List<Menu> queryFirst();

    @Query(value = "select * from menu where parentid = ?1 ", nativeQuery = true)
    List<Menu> querySecond(String id);

}
