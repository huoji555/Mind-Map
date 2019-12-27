package com.hwj.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.FileCollection;
import com.hwj.service.IFileCollectionService;



@Component
public class TryCatchFileCollectionService {

	@Autowired
	private IFileCollectionService iFileCollectionService;

	public List<FileCollection> getFileCollection(String propertyName1,
			Object value1, String propertyName2, Object value2) {

		List<FileCollection> list = null;
		try {
			list = this.iFileCollectionService.getAll(propertyName1, value1,
					propertyName2, value2);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return list;

	}

	public FileCollection getFileCollection1(String propertyName1,
			Object value1, String propertyName2, Object value2) {

		FileCollection fileCollection = null;
		try {
			fileCollection = this.iFileCollectionService.get(propertyName1, propertyName2, value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return fileCollection;
	}

	public FileCollection getFileCollection(String propertyName1,
			Object value1, String propertyName2, Object value2,
			String propertyName3, Object value3) {

		FileCollection fileCollection = null;
		try {
			fileCollection = this.iFileCollectionService.get(propertyName1,
					propertyName2, propertyName3, value1, value2, value3);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return fileCollection;

	}

	public boolean delFileCollection(FileCollection fileCollection) {

		try {
			this.iFileCollectionService.delete(fileCollection);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean saveFileCollection(FileCollection fileCollection) {

		try {
			iFileCollectionService.save(fileCollection);
			System.out.println("到这里了");
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

		return true;
	}

	public List<FileCollection> getFileCollection(String propertyName,
			Object value) {

		List<FileCollection> list = null;
		try {
			list = this.iFileCollectionService.getAll(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return list;
	}

	public boolean haveFileCollection(String propertyName1, Object value1,
			String propertyName2, Object value2) {

		FileCollection fileCollection = null;

		try {
			fileCollection = this.getFileCollection1(propertyName1, value1,
					propertyName2, value2);
			if (fileCollection == null) {
				System.out.println(fileCollection + "################");
				return false;
			}
			System.out.println("@@@@@@@@@@" + fileCollection);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public boolean updateFileCollection(FileCollection fileCollection) {

		try {
			iFileCollectionService.update(fileCollection);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public boolean delAllFileCollection(String propertyName1, Object value1,
			String propertyName2, Object value2) {

		try {

			List<FileCollection> list = this.getFileCollection(propertyName1,
					value1, propertyName2, value2);

			for (int i = 0; i < list.size(); i++) {

				FileCollection fileCollection = list.get(i);
				this.delFileCollection(fileCollection);

			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

		return true;
	}

}
