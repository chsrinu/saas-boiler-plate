package com.hrms.hrpitch.multitenancy.interceptor;

import com.hrms.hrpitch.multitenancy.tenant.TenantContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String tenantID = request.getHeader("X-TenantID");
        System.out.println("Search for X-TenantID  :: " + tenantID);
        TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}
