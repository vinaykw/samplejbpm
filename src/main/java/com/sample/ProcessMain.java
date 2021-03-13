package com.sample;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


public class ProcessMain {

	/*public static void main(String[] args) {
		SpringApplication.run(ProcessMain.class,args);
		System.out.println();
	}*/

	public static void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");

		RuntimeManager manager = createRuntimeManager(kbase);
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();
		
		Map<String, Object> params = new HashMap<String, Object>();  
		Steps steps = new Steps();
		steps.setSteps(0);
        params.put("tenantFName", "Vinayak");
        params.put("tenantLName", "Kwari");
        params.put("tenantEmail", "vkwari@radisys.com");
        params.put("tenantDomain", "tenant4.com");
        params.put("tenantId", 11);
        params.put("tenantAdminName", "vkwari");
        params.put("tenantAdminPassword", "12345");
        params.put("active", true);
        params.put("roleName", "rolex10");
        params.put("user", "Vinayak");
        params.put("resultStatus",true);
        params.put("steps",steps);
         // start a new process instance  
        
        try{
        
		ProcessInstance pInstance = ksession.startProcess("com.sample.bpmn.hello",params);
		

		manager.disposeRuntimeEngine(engine);
		Steps s = (Steps) ((WorkflowProcessInstance) pInstance).getVariable("steps");
		System.out.println("The steps"+ s.getSteps());
        }
        catch( Exception e)
        {
        	System.out.println("Excpetion occured !!!");
        	e.printStackTrace();
        }
		
		System.out.println("Main method is terminated");
		
		
		System.exit(0);
	}

	private static RuntimeManager createRuntimeManager(KieBase kbase) {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get()
			.newDefaultBuilder().entityManagerFactory(emf)
			.knowledgeBase(kbase);
		return RuntimeManagerFactory.Factory.get()
			.newSingletonRuntimeManager(builder.get(), "com.sample:example:1.0");
	}

}