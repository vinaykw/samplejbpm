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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public KieBase getKnowledgeBase()
	{
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");
		return kbase;
	}
	
	@Bean
	public KieSession getKnowledgeSession()
	{
		
		RuntimeEngine engine = getRunTimeManager().getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		
		return ksession;
	}
	
	@Bean
	public RuntimeManager getRunTimeManager()
	{
	   RuntimeManager manager = createRuntimeManager(getKnowledgeBase());
	   return manager;
	}
		
	@Bean 
	public void startProcess()
	{
		
		 Map<String, Object> params = new HashMap<String, Object>();  
         params.put("tenantFname", "Vinayak");
         params.put("tenantLName", "Kwari");
         params.put("tenantEmail", "vkwari@radisys.com");
         params.put("tenantDomain", "tenant1.com");
         params.put("tenantId", 8);
         params.put("tenantAdminName", "vkwari");
         params.put("tenantAdminPassword", "12345");
         params.put("active", true);
         params.put("roleName", "rolex10");
         params.put("user", "Vinayak");
         getKnowledgeSession().startProcess("com.sample.bpmn.hello",params);
 		getRunTimeManager().disposeRuntimeEngine( getRunTimeManager().getRuntimeEngine(null));
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
