package com.hwj.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.UploadFile;
import com.hwj.service.IUploadFileService;

@Component
public class TryCatchUploadFileService {

	@Autowired
	private IUploadFileService iUploadFileService;
	
	/**
	 * @author Ragty
	 * @param 根据两个属性获取list
	 * @serialData 2018.3.6
	 * @param propertyName1
	 * @param value1
	 * @param propertyName2
	 * @param value2
	 * @return
	 */
	public List<UploadFile> getUploadeFile(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		List<UploadFile> list = null;
		try {
			list = this.iUploadFileService.getAll(propertyName1, value1,
					propertyName2, value2);
		} catch (Exception e) {

			return null;
		}

		return list;
	}
	
	
	/**
	 * @author Ragty
	 * @param 根据两个属性获取实体类
	 * @serialData 2018.3.6
	 * @param propertyName1
	 * @param propertyName2
	 * @param value1
	 * @param value2
	 * @return
	 */
	public UploadFile getUploadFile(String propertyName1, String propertyName2,
			Object value1, Object value2) {
		UploadFile uploadFile = null;
		try {
			uploadFile = this.iUploadFileService.get(propertyName1,
					propertyName2, value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return uploadFile;

	}

	
	/**
	 * @author Ragty
	 * @param 根据三个属性获取实体类
	 * @serialData 2018.3.6
	 * @param propertyName1
	 * @param propertyName2
	 * @param propertyName3
	 * @param value1
	 * @param value2
	 * @param value3
	 * @return
	 */
	public UploadFile getUploadFile(String propertyName1, String propertyName2,
			String propertyName3, Object value1, Object value2, Object value3) {
		UploadFile uploadFile = null;
		try {
			uploadFile = this.iUploadFileService.get(propertyName1,
					propertyName2, propertyName3, value1, value2, value3);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return uploadFile;
	}
	

	/**
	 * @author Ragty
	 * @param 保存实体类
	 * @serialData 2018.3.6
	 * @param uploadFile
	 * @return
	 */
	public boolean saveUploadFile(UploadFile uploadFile) {
		try {
			this.iUploadFileService.save(uploadFile);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty
	 * @param 删除实体类
	 * @serialData 2018.3.6
	 * @param uploadFile
	 * @return
	 */
	public boolean deleteUploadFile(UploadFile uploadFile) {
		try {
			this.iUploadFileService.delete(uploadFile);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty
	 * @param 修改实体类
	 * @serialData 2018.3.6
	 * @param uploadFile
	 * @return
	 */
	public boolean updateUploadeFile(UploadFile uploadFile) {
		try {
			this.iUploadFileService.update(uploadFile);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty
	 * @param 根据三个属性查询符合条件的实体类
	 * @serialData 2018.3.6
	 * @param propertyName1
	 * @param propertyName2
	 * @param value1
	 * @param value2
	 * @return
	 */
	public List<UploadFile> selectUploadFile(String propertyName1,
			String propertyName2, Object value1, Object value2) {
		List<UploadFile> list;
		try {
			list = iUploadFileService.select(propertyName1, propertyName2,
					value1, value2);
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	
	/**
	 * @author Ragty
	 * @param 根据两个属性判断是否有否和条件的数据
	 * @serialData 2018.3.6
	 * @param propertyName1
	 * @param propertyName2
	 * @param value1
	 * @param value2
	 * @return
	 */
	public boolean haveUploadFile(String propertyName1, String propertyName2,
			Object value1, Object value2) {

		UploadFile uploadFile = null;
		try {
			uploadFile = this.iUploadFileService.get(propertyName1,
					propertyName2, value1, value2);
			System.out.println(uploadFile + "######################");
			if (uploadFile == null) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	
	/**
	 * @author Ragty
	 * @param 删除所有符合条件的数据
	 * @serialData 2018.3.6
	 * @param propertyName1
	 * @param propertyName2
	 * @param value1
	 * @param value2
	 * @return
	 */
	public boolean delAllUploadFile(String propertyName1, String propertyName2,
			Object value1, Object value2) {

		List<UploadFile> list = this.getUploadeFile(propertyName1, value1,
				propertyName2, value2);

		try {

			for (int i = 0; i < list.size(); i++) {
				UploadFile uploadFile = list.get(i);
				this.deleteUploadFile(uploadFile);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

		return true;

	}
	
	
	/**
	 * @author Ragty
	 * @param 根据一个属性获取符合条件的数据
	 * @serialData 2018.3.6
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<UploadFile> getUploadFile(String propertyName,Object value){
		
		List<UploadFile> list=null;
		try {
			list=this.iUploadFileService.getAll(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}
	
	
}
