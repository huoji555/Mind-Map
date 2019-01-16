package com.hwj.serviceImpl;

import com.hwj.entity.Menu;
import com.hwj.repository.MenuRepository;
import com.hwj.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;


    @Override
    public List<Menu> getMenu() { return menuRepository.findAll(); }




}
