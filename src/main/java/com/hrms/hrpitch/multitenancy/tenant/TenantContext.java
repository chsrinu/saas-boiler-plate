package com.hrms.hrpitch.multitenancy.tenant;

public class TenantContext {
    private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId.toLowerCase());
    }

    public static void clear() {
        currentTenant.set(null);
    }
}
