package com.sample;

import java.io.IOException;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;


public class ClientInterceptorHelper implements ClientInterceptor {

	private int status;
	  @Override
	  public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
	    ByteArrayTransportOutputStream os = new ByteArrayTransportOutputStream();
	    try {
	      messageContext.getRequest().writeTo(os);
	    } catch (IOException e) {
	      throw new WebServiceIOException(e.getMessage(), e);
	    }

	    String request = new String(os.toByteArray());

	    System.out.println("Request Envelope: " + request);
	    return true;
	  }

	  @Override
	  public boolean handleResponse(MessageContext messageContext)
	      throws WebServiceClientException {

	  
	    ByteArrayTransportOutputStream os = new ByteArrayTransportOutputStream();
	    try {
	      messageContext.getResponse().writeTo(os);
	    } catch (IOException e) {
	      throw new WebServiceIOException(e.getMessage(), e);
	    }

	    String response = new String(os.toByteArray());
	    System.out.println("Response Envelope: " + response);
	    return true;
	  }

	  @Override
	  public boolean handleFault(MessageContext messageContext)  {
	    status = -1;
	    return false;
	  }

	  @Override
	  public void afterCompletion(MessageContext messageContext, Exception e)
	      throws WebServiceClientException {

	  }
	  int getStatus()
	  {
	    return status;
	  }
}
