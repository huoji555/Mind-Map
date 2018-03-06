package com.hwj.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;



import com.hwj.entityUtil.BeSaveFileUitl;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class FileUpload {
	// 保存图片到服务器上
	public String[] saveFile(BeSaveFileUitl be) {
		String fileExtension = be.getFileExtension();
		byte[] file = be.getFilesByte();
		String fileURL = be.getFileURL();

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date newsDate = new Date();
		// fileDate是上传图片的时间
		String fileDate = format.format(newsDate);
		// 以文件的类别作为保存文件的文件夹名
		File filePackage = new File(fileURL);
		// 以日期为文件夹名
		File dateFile = new File(filePackage + "\\" + fileDate);

		String[] str = new String[3];
		// 文件夹已存在,保存文件
		if (filePackage.exists()) {
			// 文件夹已存在,保存文件
			if (dateFile.exists()) {
				str = this.savefile(fileURL, fileDate, file, fileExtension);
				return str;
			}
			// 文件夹不存在，创建文件夹，保存文件
			else {
				if (dateFile.mkdirs()) {
					str = this.savefile(fileURL, fileDate, file, fileExtension);
					return str;
				} else {
					return str;
				}
			}
		}

		// 文件夹不存在，创建文件夹，保存文件
		else {
			// 新建文件夹
			boolean boo = filePackage.mkdirs();
			if (boo) {
				// 文件夹已存在,保存文件
				if (dateFile.exists()) {
					str = this.savefile(fileURL, fileDate, file, fileExtension);
					return str;
				}
				// 文件夹不存在，创建文件夹，保存文件
				else {
					if (dateFile.mkdirs()) {
						str = this.savefile(fileURL, fileDate, file,
								fileExtension);
						System.out.println("str------>" + str);
						return str;
					} else {
						return str;
					}
				}
			} else {
				return str;
			}
		}
	}

	// 文件夹不存在，创建文件夹，保存文件
	private String[] savefile(String fileURL, String fileDate, byte[] file,
			String fileExtension) {
		// 返回状态
		int status = 0;
		SimpleDateFormat fileN = new SimpleDateFormat("HHmmss");
		Date fileNameDate = new Date();
		String fileName = fileN.format(fileNameDate);
		String[] str = new String[3];
		// 保存文件
		FileOutputStream fos = null;
		String URL = "";
		try {
			// 创建流对象
			fos = new FileOutputStream(fileURL + "\\" + fileDate + "\\"
					+ fileName + "." + fileExtension);
			System.out.println(fos);
			// 转换为byte数组
			byte[] b1 = file;
			// 写入文件
			fos.write(b1);
			System.out.println("写入文件......");
			status = 1;
			URL = fileURL + "\\" + fileDate + "\\" + fileName;
			System.out.println("URL为：" + URL);
		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
			System.out.println("写入文件时出错了");
		} finally {
			try {
				fos.close();
				URL = fileURL + "\\" + fileDate + "\\" + fileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 文件保存是否成功，1为成功，0为失败
		// 将整转化成string
		str[0] = String.valueOf(status);
		System.out.println("str[0]为：" + str[0]);
		// 文件在本地的URL
		str[1] = URL;
		// 文件的名字
		str[2] = fileName + "." + fileExtension;
		return str;
	}

}
