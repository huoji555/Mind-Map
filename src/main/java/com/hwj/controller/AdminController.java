package com.hwj.controller;

import com.hwj.entity.Admin;
import com.hwj.repository.AdminRepository;
import com.hwj.service.AdminService;
import com.hwj.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * @auther: Ragty
     * @describe: 根据日期查询所有的用户信息(管理员权限，一会儿加)
     * @param: [firstDate, lastDate, request]
     * @return: com.hwj.util.ResultBean<org.springframework.data.domain.Page<com.hwj.entity.Admin>>
     * @date: 2018/10/29
     */
    @PostMapping("/queryAdminByDate")
    public ResultBean<Page<Admin>> queryAdminByDate(@RequestParam String firstDate1, @RequestParam String lastDate1,
                                                    @RequestParam String page, @RequestParam String size,
                                                    HttpServletRequest request) throws Exception{

        Sort sort = new Sort(Sort.Direction.DESC,"create_date");
        Pageable pageable = new PageRequest(Integer.parseInt(page),Integer.parseInt(size),sort);

        Date firstDate = null;
        Date lastDate = null;

        if (firstDate1 != "" && lastDate1 != "") {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(firstDate1);
            firstDate = sdf.parse(firstDate1);
            lastDate =sdf.parse(lastDate1);
        }

        Page<Admin> list = adminService.findAllAdmin(firstDate,lastDate,pageable);
        return new ResultBean<Page<Admin>>(list);
    }


}
