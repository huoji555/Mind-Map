package com.hwj.service;

import com.hwj.entity.ShareMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ShareMapService {

    void save(ShareMap shareMap);

    void delete(String mapid);

    boolean existsShareMap(String mapid);

    Page<ShareMap> getShareMapList(String share_id, String mapname, Date firstDate, Date lastDate, Pageable pageable);

}
