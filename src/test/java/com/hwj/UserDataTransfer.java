package com.hwj;

import com.hwj.entity.Admin;
import com.hwj.repository.AdminRepository;
import com.hwj.service.AdminService;
import com.hwj.util.PBKDF2Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDataTransfer {

    @Autowired
    private PBKDF2Util pbkdf2Util;
    @Autowired
    private AdminRepository adminRepository;

    //transfer
    @Test
    public void userDataTransferTest() throws Exception {

        List<Admin> list = adminRepository.findAll();

        for(ListIterator<Admin> it = list.listIterator(); it.hasNext(); ) {
            Admin admin = it.next();
            String salt = pbkdf2Util.generateSalt();
            String password = pbkdf2Util.getEncryptedPassword(admin.getPassword(),salt);
            admin.setSalt(salt);
            admin.setIp("0:0:0:0:0:0:0:1");
            admin.setPassword(password);
            admin.setCreateDate(new Date());

            adminRepository.save(admin);
        }


    }



}
