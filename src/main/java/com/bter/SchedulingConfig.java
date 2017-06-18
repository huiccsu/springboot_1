package com.bter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SchedulingConfig {
	static int index=0;
	@Autowired
	SimpMessagingTemplate  template;
    public SchedulingConfig()
    {
    	ExecutorService executorService =	Executors.newFixedThreadPool(1);
		executorService.execute(new Runnable() {
			public void run() {
				while (true)
					scheduler();
			}
		});
    }
   // @Scheduled(fixedRate = 4) 
    public void scheduler() {
    	try {
    		//AppTestAPI appTestAPI = new AppTestAPI();
        	index++;
        	Object json = BterJsonData.getBtc_rmb();
        	if(json==null) return;
        	ResponseMessage responseMessage =new ResponseMessage(json.toString());
        	//ResponseMessage responseMessage =new ResponseMessage(appTestAPI.testsuite().toString());
        	template.convertAndSend("/topic/getResponse",responseMessage);
        	System.out.println(index+":"+System.currentTimeMillis());
        	Thread.sleep(500);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    	
    }
    
}