package com.hwj.serviceImpl;

import com.hwj.entity.ShareMap;
import com.hwj.repository.ShareMapRepository;
import com.hwj.service.ShareMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ShareMapServiceImpl implements ShareMapService {

    @Autowired
    private ShareMapRepository shareMapRepository;


}
