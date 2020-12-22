package com.hwj.service;

import com.hwj.entity.Zsd;

public interface ZsdService {

    void save(Zsd zsd);

    void update(String zsdms, String nodeid);

    void deleteByNodeid(String nodeid);

    void deleteByMapid(String mapid);

    Zsd findZsd(String nodeid);


}
