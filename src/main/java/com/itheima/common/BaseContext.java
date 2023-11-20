package com.itheima.common;


public class BaseContext {
    // 设置局部线程变量
    private static  ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
