package com.hwj.tools;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.FileStream;
import com.hwj.service.IFileStreamService;

@Component
public class TryCatchFileStreamService {

	@Autowired
	IFileStreamService iFileStreamService;

	// 保存文件流
	public boolean saveFileStream(FileStream fileStream) {

		try {
			System.out.println("终于能存了");
			iFileStreamService.save(fileStream);
			System.out.println("想必是存上了");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("没有存上");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// 获取对应的文件流
	public FileStream getFileStream1(String propertyName1,
			String propertyName2, Object value1, Object value2) {

		FileStream fileStream = null;
		try {
			fileStream = this.iFileStreamService.get(propertyName1,
					propertyName2, value1, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return fileStream;
	}

	// 回收站内加载文件
	public List<FileStream> getFileStream(String propertyName1, Object value1,
			String propertyName2, Object value2) {

		List<FileStream> list = null;
		try {
			list = this.iFileStreamService.getAll(propertyName1, value1,
					propertyName2, value2);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	// 回收站内加载文件
	public List<FileStream> getFileStream(String propertyName, Object value) {

		List<FileStream> list = null;
		try {
			list = this.iFileStreamService.getAll(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return list;
	}

	// 删除回收站内文件
	public boolean delFileStream(FileStream fileStream) {

		try {
			this.iFileStreamService.delete(fileStream);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	// 修改回收站内文件
	public boolean updateFileStream(FileStream fileStream) {

		try {
			this.iFileStreamService.update(fileStream);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;

	}

}
