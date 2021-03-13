package com.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import com.radisys.wso2.soap.webClient.usermgt.UserMgtSOAPWebClient;
import com.radisys.wso2.soap.webClient.usermgt.UserMgtSOAPWebClientConfiguration;


public class CreateRole {
	
	
	UserMgtSOAPWebClientConfiguration userMgtSOAPWebClientConfiguration;
	
	private String roleName;
	private String user;
	
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public boolean createRoles()
	{
		ClientInterceptorHelper clientInterceptorHelper = new ClientInterceptorHelper();
		try {
		  UserMgtSOAPWebClientConfiguration userMgtSOAPWebClientConfiguration = new UserMgtSOAPWebClientConfiguration();
		  Jaxb2Marshaller jaxb2Marshaller = userMgtSOAPWebClientConfiguration.userMgmtMarshaller();
		  UserMgtSOAPWebClient userMgtSOAPWebClient=userMgtSOAPWebClientConfiguration.userMgtSOAPWebClient(userMgtSOAPWebClientConfiguration.userMgmtMarshaller());

		  userMgtSOAPWebClient.setDefaultUri("https://localhost:9443/services/RemoteUserStoreManagerService.RemoteUserStoreManagerServiceHttpSoap11Endpoint/");

		  

		  userMgtSOAPWebClient.setInterceptors(new ClientInterceptor[]{ clientInterceptorHelper});
		  userMgtSOAPWebClient.addRole(roleName,user);
		 
		 }catch (Exception e) {
		      e.printStackTrace();
		 }
		
		return (clientInterceptorHelper.getStatus() == 0);
	}
}
