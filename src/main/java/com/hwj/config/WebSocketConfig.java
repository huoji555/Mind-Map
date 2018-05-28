package com.hwj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Ragty
 * @method webSocket配置
 * @serialData 2018.5.28
 */
@Configuration
public class WebSocketConfig {

	@Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    }  
	
	
}
