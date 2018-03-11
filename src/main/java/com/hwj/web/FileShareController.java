package com.hwj.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchFileShareService;

@Controller
public class FileShareController {

	  @Autowired
	  private  StatusMap statusMap;
	  @Autowired
	  private JsonAnalyze jsonAnalyze;
	  @Autowired
	  private TryCatchFileShareService tryCatchFileShareService;
	
	
	
}
