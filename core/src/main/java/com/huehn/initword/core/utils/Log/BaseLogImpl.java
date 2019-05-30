package com.huehn.initword.core.utils.Log;

import com.huehn.initword.core.module.Log.ILogMethod;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 这个类用来写imp一些公用的方法，比如list转string， map转string
 */
public abstract class BaseLogImpl implements ILogMethod {

    public final static String LIST_BEGIN = "[";
    public final static String LIST_END = "]";

    public final static String MODULE_BEGIN = "{";
    public final static String MODULE_END = "}";
    public final static String MODULE_NULL = "{null}";

    public final static String FIELD_NAME = "\"";

    /**
     * list转字符串
     * @param list
     * @param stringBuilder
     * @return
     */
    protected StringBuilder listToString(List list, StringBuilder stringBuilder){

        if (stringBuilder == null){
            throw new RuntimeException("BaseLogImpl.listToString.stringBuilder is null");
        }
        if (list == null){
            stringBuilder.append("BaseLogImpl.listToString.list is null");
            return stringBuilder;
        }
        if (list.size() > 200){
            stringBuilder.append("list is more than 200");
            return stringBuilder;
        }

        stringBuilder.append(LIST_BEGIN);
        for (int i = 0; i < list.size(); i++){
            if (list.get(i) == null){
                stringBuilder.append(MODULE_NULL);
            }
            if (list.get(i) instanceof List){
                listToString((List) list.get(i), stringBuilder);
            }else if (list.get(i) instanceof Map){
                mapToString((Map) list.get(i), stringBuilder);
            }else{
                stringBuilder.append(objectToString(list.get(i)));
            }
            if (i <= list.size() - 2){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(LIST_END);


        return stringBuilder;
    }


    /**
     * map转成string
     * @param map
     * @param stringBuilder
     * @return
     */
    protected String mapToString(Map map, StringBuilder stringBuilder){
        String result = null;
        if (map == null){
            result = "map is null";
        }
        return result;
    }


    /**
     * 对象转成string
     * @param object
     * @return
     */
    protected StringBuilder objectToString(Object object){
        StringBuilder stringBuilder = new StringBuilder();
        if (object == null){
            stringBuilder.append(MODULE_NULL);
            return stringBuilder;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        stringBuilder.append(MODULE_BEGIN);
        for (Field field : fields){
            //setAccessible功能是启用或禁用安全检查 ，public的方法 Accessible仍为false
            //使用了method.setAccessible(true)后 性能有了20倍的提升
            field.setAccessible(true);
            try {
                if (field.getName() == null || field.get(object) == null){
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
            stringBuilder.append(FIELD_NAME);
            stringBuilder.append(field.getName());
            stringBuilder.append(FIELD_NAME);
            stringBuilder.append(":");
            //https://stackoverflow.com/questions/36549129/android-java-objmodelclass-getclass-getdeclaredfields-returns-change-as-o
            try {

                
                if (List.class.isAssignableFrom(field.get(object).getClass())){
                    listToString((List) field.get(object), stringBuilder);
                }else if (String.class.isAssignableFrom(field.get(object).getClass())){
                    stringBuilder.append((String)field.get(object));
                }else if (Number.class.isAssignableFrom(field.get(object).getClass())){//Integer,Long
                    stringBuilder.append("" + (field.get(object)));
                }else {
                    stringBuilder.append(objectToString(field.get(object)));
                }


                
                stringBuilder.append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("BaseLogImpl.objectToString field IllegalAccessException");
            }
        }
        stringBuilder.append(MODULE_END);
        return stringBuilder;
    }
    /**
     * 打印堆栈日志，进行日志跳转
     * @param clazz
     * @return
     */
    protected String printTargetStack(Class clazz){
        StackTraceElement targetStackTraceElement = getTargetStackTraceElement(clazz);
        return "(" + targetStackTraceElement.getFileName() + ":" + targetStackTraceElement.getLineNumber() + ") ";
    }

    /**
     * 获取日志在代码中的位置堆栈
     * @param clazz
     * @return
     */
    private StackTraceElement getTargetStackTraceElement(Class clazz) {
        // find the target invoked method
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(clazz.getName());
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            //如果isLogMethod为true，则是找到clazz对应的堆栈，然后继续往下遍历看是否还有符合的，如果没有（isLogMethod为false），则把上一次符合的返回堆栈。
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }

    protected abstract StringBuilder writeLog(Object object);
//    public abstract StringBuilder writeLog(int logType, Object object);
}
