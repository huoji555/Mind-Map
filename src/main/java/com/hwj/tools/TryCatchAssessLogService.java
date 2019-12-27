package com.hwj.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.AssessLog;
import com.hwj.service.IAssessLogService;

@Component
public class TryCatchAssessLogService {

	@Autowired
	private IAssessLogService iAssessLogService;

	public boolean setAssessLog(AssessLog assessLog) {
		try {
			iAssessLogService.setAssessLog(assessLog);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

}
