package com.thinkgem.jeesite.modules.sys.interceptor;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUploadInterceptor extends HandlerInterceptorAdapter {
    private long maxUploadSize;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request!=null && ServletFileUpload.isMultipartContent(request)) {
            ServletRequestContext ctx = new ServletRequestContext(request);
            long requestSize = ctx.contentLength();
            if (requestSize > maxUploadSize) {
            	
                throw new MaxUploadSizeExceededException(maxUploadSize);
                //response.sendRedirect("enterprise/overSize");
               // return false;
                
            }
        }
        return true;
    }
    
    


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }




	public void setMaxUploadSize(long maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

   
}