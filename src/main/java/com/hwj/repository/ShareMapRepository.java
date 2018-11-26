package com.hwj.repository;

import com.hwj.entity.ShareMap;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface ShareMapRepository extends PagingAndSortingRepository<ShareMap, String> {

}
