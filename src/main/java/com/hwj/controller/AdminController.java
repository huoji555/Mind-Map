package com.hwj.controller;

import com.google.common.collect.Maps;
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
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

        HttpSession session = request.getSession();
        String roleId = String.valueOf(session.getAttribute("roleId"));

        if (roleId == "2") {
            return new ResultBean<>();
        }

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




    /**
     * @auther: Ragty
     * @describe: 用户授权
     * @param: [username, newRoleId]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/10/31
     */
    @PostMapping("/updateAuthorize")
    public ResultBean<Map<String,Object>> updateAuthorize(@RequestParam String username, @RequestParam String newRoleId) {

        Map<String,Object> result = Maps.newHashMap();

        Admin admin = adminService.queryAdminByUsernameOrEmail(username,"");
        admin.setRoleId(Integer.parseInt(newRoleId));

        adminService.save(admin);

        result.put("status",200);
        result.put("message","授权成功");
        return new ResultBean<>(result);
    }


}
