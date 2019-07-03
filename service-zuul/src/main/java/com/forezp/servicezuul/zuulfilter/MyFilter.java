package com.forezp.servicezuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class MyFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(MyFilter.class);



    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx= RequestContext.getCurrentContext();
        HttpServletRequest request=ctx.getRequest();
       log.info(String.format("%s >>> %s",request.getMethod(),request.getRequestURL().toString()));
       Object accessToken =request.getParameter("token");
       if(accessToken==null){
           log.warn("token is empty");
           ctx.setSendZuulResponse(false);
           ctx.setResponseStatusCode(401);
           try{
               HttpServletResponse response=ctx.getResponse();
              PrintWriter printWriter= response.getWriter();
              printWriter.write("token is empty");
           }catch (Exception e){

           }
       }
        return null;
    }
}
