package com.hwj.serviceImpl;

import com.hwj.entity.Zsd;
import com.hwj.repository.ZsdRepository;
import com.hwj.service.ZsdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZsdServiceImpl implements ZsdService {

    @Autowired
    private ZsdRepository zsdRepository;


    @Override
    public void update(String zsdms, String nodeid) { zsdRepository.updateZsd(zsdms,nodeid); }

    @Override
    public void deleteByNodeid(String nodeid) { zsdRepository.deleteZsdByNodeid(nodeid); }

    @Override
    public void deleteByMapid(String mapid) { zsdRepository.deleteByMapid(mapid); }

    @Override
    public Zsd findZsd(String nodeid) { return zsdRepository.findZsdByNodeid(nodeid); }

    @Override
    public void save(Zsd zsd) { zsdRepository.save(zsd); }

}
