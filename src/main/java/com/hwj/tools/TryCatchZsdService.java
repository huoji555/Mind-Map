package com.hwj.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.Zsd;
import com.hwj.service.IZsdService;

@Component
public class TryCatchZsdService {

	@Autowired
	private IZsdService iZsdService;
	
	public List<Zsd> getZsd(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		List<Zsd> list = null;
		try {
			list = this.iZsdService.getAll(propertyName1, value1,
					propertyName2, value2);
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	public List<Zsd> getZsd(String propertyName, Object value) {
		List<Zsd> list = null;
		try {
			list = this.iZsdService.getAll(propertyName, value);
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	public Zsd getZsd1(String propertyName1, String propertyName2,
			Object value1, Object value2) {

		Zsd zsd1 = null;
		try {
			zsd1 = iZsdService
					.get(propertyName1, propertyName2, value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return zsd1;
	}

	public boolean saveZsd(Zsd zsd) {
		try {
			this.iZsdService.save(zsd);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean update(Zsd zsd) {
		try {
			this.iZsdService.update(zsd);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	//删除知识点
	public boolean deleteZsd(Zsd zsd){
		try {
			
			this.iZsdService.delete(zsd);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	
}
