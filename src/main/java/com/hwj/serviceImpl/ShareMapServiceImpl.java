package com.hwj.serviceImpl;

import com.hwj.entity.ShareMap;
import com.hwj.repository.ShareMapRepository;
import com.hwj.service.ShareMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ShareMapServiceImpl implements ShareMapService {

    @Autowired
    private ShareMapRepository shareMapRepository;

    @Override
    public void save(ShareMap shareMap) { shareMapRepository.save(shareMap); }

    @Override
    public void delete(String mapid) { shareMapRepository.delShareMap(mapid); }

    @Override
    public Page<ShareMap> getShareMapList(String share_id, String mapname, Date firstDate, Date lastDate, Pageable pageable) {
        return shareMapRepository.getShareMapByPage(share_id, mapname, firstDate, lastDate, pageable);
    }

}
