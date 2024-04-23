package com.clx.common;

/**
 * encapsulated tool class based on ThreadLocal to store and get current user ID
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * set id
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * get id
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
