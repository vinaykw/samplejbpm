package com.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import com.sample.ClientInterceptorHelper;

import com.radisys.wso2.soap.webClient.tenantmgt.TenantMgtSOAPWebClient;
import com.radisys.wso2.soap.webClient.tenantmgt.TenantMgtSOAPWebClientConfiguration;


public class CreateTenant {

	private String tenantFName;
	private String tenantLName; 
	private String tenantEmail;
	private String tenantDomain;
	
	private int tenantId;
	private String tenantAdminName;
	private String tenantAdminPassword;
	private boolean active;
	
	
	private TenantMgtSOAPWebClientConfiguration tenantMgtSOAPWebClientConfiguration;

	public String getTenantFName() {
		return tenantFName;
	}

	public void setTenantFName(String tenantFName) {
		this.tenantFName = tenantFName;
	}

	public String getTenantLName() {
		return tenantLName;
	}

	public void setTenantLName(String tenantLName) {
		this.tenantLName = tenantLName;
	}

	public String getTenantEmail() {
		return tenantEmail;
	}

	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}

	public String getTenantDomain() {
		return tenantDomain;
	}

	public void setTenantDomain(String tenantDomain) {
		this.tenantDomain = tenantDomain;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantAdminName() {
		return tenantAdminName;
	}

	public void setTenantAdminName(String tenantAdminName) {
		this.tenantAdminName = tenantAdminName;
	}

	public String getTenantAdminPassword() {
		return tenantAdminPassword;
	}

	public void setTenantAdminPassword(String tenantAdminPassword) {
		this.tenantAdminPassword = tenantAdminPassword;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean createTenants()
	{
		ClientInterceptorHelper clientInterceptorHelper = new ClientInterceptorHelper();
		try {
			TenantMgtSOAPWebClientConfiguration tenantMgtSOAPWebClientConfiguration = new TenantMgtSOAPWebClientConfiguration();
			Jaxb2Marshaller jaxb2Marshaller = tenantMgtSOAPWebClientConfiguration.tenantMgmtMarshaller();
			
			TenantMgtSOAPWebClient tenantMgtSOAPWebClient = tenantMgtSOAPWebClientConfiguration.tenantMgtSOAPWebClient(jaxb2Marshaller);
			tenantMgtSOAPWebClient.setDefaultUri("https://localhost:9443/services/TenantMgtAdminService.TenantMgtAdminServiceHttpsSoap11Endpoint");
			
		    tenantMgtSOAPWebClient.setInterceptors(new ClientInterceptor[]{clientInterceptorHelper});
			tenantMgtSOAPWebClient.addTenant(tenantFName, tenantLName, tenantEmail, tenantDomain, tenantId, tenantAdminName, tenantAdminPassword, active);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Caught exception while adding tenant");
			e.printStackTrace();
		}
		
		return (clientInterceptorHelper.getStatus() == 0);
	}
	
	
}
