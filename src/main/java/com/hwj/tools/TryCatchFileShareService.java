package com.hwj.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.FileShare;
import com.hwj.service.IShareFileService;


@Component
public class TryCatchFileShareService {

	// 用来具体操作数据库的
	@Autowired
	private IShareFileService iShareFileService;

	public List<FileShare> getShareFile(String propertyName, Object value) {
		List<FileShare> list;
		try {
			list = iShareFileService.getAll(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;

	}

	public boolean saveShareFile(FileShare fileShare) {

		try {
			iShareFileService.save(fileShare);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public List<FileShare> getShareFile() {
		List<FileShare> list;
		try {
			list = iShareFileService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
		return list;

	}

	public List<FileShare> selectFile(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		List<FileShare> list;

		try {
			list = iShareFileService.select(propertyName1, propertyName2,
					value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return list;
	}

	public List<FileShare> getShareFile(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		List<FileShare> list;

		try {
			list = iShareFileService.getAll(propertyName1, value1,
					propertyName2, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	public boolean delShareFile(FileShare fileShare) {

		try {
			this.iShareFileService.delete(fileShare);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

		return true;
	}

	public FileShare getFileShare(String propertyName1, Object value1,
			String propertyName2, Object value2) {

		FileShare fileShare = new FileShare();
		try {
			fileShare = this.iShareFileService.get(propertyName1,
					propertyName2, value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return fileShare;
	}

	public FileShare getFileShare(String propertyName1, Object value1,
			String propertyName2, Object value2, String propertyName3,
			Object value3) {

		FileShare fileShare = new FileShare();
		try {
			fileShare = this.iShareFileService.get(propertyName1,
					propertyName2, propertyName3, value1, value2, value3);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return fileShare;

	}

	public boolean haveFileShare(String propertyName1, Object value1,
			String propertyName2, Object value2) {

		FileShare fileShare = iShareFileService.get(propertyName1,
				propertyName2, value1, value2);

		try {
			if (fileShare == null) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public boolean updateFileShare(FileShare fileShare) {

		try {
			iShareFileService.update(fileShare);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;

	}

	public boolean delAllFileShare(String propertyName1, Object value1,
			String propertyName2, Object value2) {

		List<FileShare> list = this.getShareFile(propertyName1, value1,
				propertyName2, value2);
		try {

			for (int i = 0; i < list.size(); i++) {

				FileShare fileShare = list.get(i);
				this.delShareFile(fileShare);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

		return true;
	}

}
