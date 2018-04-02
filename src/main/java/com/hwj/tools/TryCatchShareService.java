package com.hwj.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.Share;
import com.hwj.service.IShareService;

@Component
public class TryCatchShareService {

	@Autowired
	private IShareService iShareService;
	
	public List<Share> getShare(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		List<Share> list = null;
		try {
			list = this.iShareService.getAll(propertyName1, value1,
					propertyName2, value2);
		} catch (Exception e) {

			return null;
		}

		return list;
	}

	public Share getshare(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		Share share;
		try {
			share = this.iShareService.get(propertyName1, propertyName2,
					value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return share;
	}

	public Share getShare(String propertyName, Object value) {
		Share share;
		try {
			share = this.iShareService.get(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return share;
	}

	public List<Share> getshare(String propertyName, Object value) {
		List<Share> list;
		try {
			list = this.iShareService.getAll(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	public boolean saveShare(Share share) {
		try {
			this.iShareService.save(share);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	public boolean delShare(Share share){
		try {
			this.iShareService.delete(share);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	/**
	 * @author Ragty
	 * @serialData 2017.12.4
	 * @param 用来构建分享的思维导图界面的分页显示数据
	 * @param currentPage
	 * @param pageSize
	 * @param propertyName
	 * @param value
	 * @return list
	 */
	public List<Share> getSharePageByOne(Integer currentPage,Integer pageSize,
			String propertyName,Object value){
	    List<Share> list=null;
		try {
			list=this.iShareService.getAllByPage(currentPage, pageSize, propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	
	/**
	 * @author Ragty
	 * @serialData 2017.12.4
	 * @param 获取分享过的思维导图的总个数（单个条件获取）
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public Long countShareByOne(String propertyName,Object value){

		Long total;
		try {
			total=this.iShareService.countByOne(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return total;
	}
	
	
}
