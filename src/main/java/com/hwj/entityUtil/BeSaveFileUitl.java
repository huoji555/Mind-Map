package com.hwj.entityUtil;

import java.util.Arrays;

public class BeSaveFileUitl {
	private String fileURL;
	private String fileExtension;
	private byte[] filesByte;

	public String getFileURL() {
		return this.fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getFileExtension() {
		return this.fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public byte[] getFilesByte() {
		return this.filesByte;
	}

	public void setFilesByte(byte[] filesByte) {
		this.filesByte = filesByte;
	}

	public String toString() {
		return

		"BeSaveFileUitl [fileURL=" + this.fileURL + ", fileExtension="
				+ this.fileExtension + ", filesByte="
				+ Arrays.toString(this.filesByte) + "]";
	}
}
