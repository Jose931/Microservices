package com.application.microservice.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTimeFilters extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(PostTimeFilters.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		
		RequestContext ctx = RequestContext.getCurrentContext();
		
		HttpServletRequest request = ctx.getRequest();
		Long initTime = (Long) request.getAttribute("initTime");
		Long finalTime = (Long) System.currentTimeMillis();
		Long totaltime = initTime - finalTime;
		log.info("Comming to post ");
		
		request.setAttribute("totalTime seg", totaltime.doubleValue()/1000.00);
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
