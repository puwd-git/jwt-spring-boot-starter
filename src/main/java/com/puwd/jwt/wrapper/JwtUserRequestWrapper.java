package com.puwd.jwt.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @auther puwd
 * @Date 2021-1-21 16:25
 * @Description
 */
public class JwtUserRequestWrapper extends HttpServletRequestWrapper {

    private Map<String , String[]> params = new HashMap<>();

    public JwtUserRequestWrapper(HttpServletRequest request) {
        super(request);

        // 将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
    }

    //重载一个构造方法
    public JwtUserRequestWrapper(HttpServletRequest request , Map<String , Object> extendParams) {
        this(request);
        addAllParameters(extendParams);//这里将扩展参数写入参数表
    }

    @Override
    public String getParameter(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                return strArr[0];
            } else {
                return null;
            }
        } else if (v instanceof String) {
            return (String) v;
        } else {
            return v.toString();
        }
    }

    @Override
    public Enumeration getParameterNames() {
        Vector names = new Vector(params.keySet());
        return names.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            return (String[]) v;
        } else if (v instanceof String) {
            return new String[] { (String) v };
        } else {
            return new String[] { v.toString() };
        }
    }

    public void addAllParameters(Map<String , Object>otherParams) {//增加多个参数
        for(Map.Entry<String , Object>entry : otherParams.entrySet()) {
            addParameter(entry.getKey() , entry.getValue());
        }
    }


    public void addParameter(String name , Object value) {//增加参数
        if(value != null) {
            if(value instanceof String[]) {
                params.put(name , (String[])value);
            }else if(value instanceof String) {
                params.put(name , new String[] {(String)value});
            }else {
                params.put(name , new String[] {String.valueOf(value)});
            }
        }
    }

    /** 简单封装，请根据需求改进 */
    public void addObject(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        try {
            for (Method method : methods) {
                if (!method.getName().startsWith("get")) {
                    continue;
                }
                Object invoke = method.invoke(obj);
                if (invoke == null || "".equals(invoke)) {
                    continue;
                }

                String filedName = method.getName().replace("get", "");
                filedName = lowerFirst(filedName);

                if (invoke instanceof Collection) {
                    Collection collections = (Collection) invoke;
                    if (collections != null && collections.size() > 0) {
                        String[] strings = (String[]) collections.toArray();
                        addParameter(filedName, strings);
                        return;
                    }
                }

                addParameter(filedName, invoke);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String upperFirst(String oldStr){
        char[]chars = oldStr.toCharArray();

        chars[0] -= 32;

        return String.valueOf(chars);
    }

    private String lowerFirst(String oldStr){
        char[]chars = oldStr.toCharArray();

        chars[0] += 32;

        return String.valueOf(chars);
    }
}
