package com.huehn.initword.core.utils.Log;

import com.huehn.initword.core.module.ILogMethod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 这个类用来写imp一些公用的方法，比如list转string， map转string
 */
public abstract class LogImpl implements ILogMethod {

    public final static String LIST_BEGIN = "[";
    public final static String LIST_END = "]";

    public final static String MODULE_BEGIN = "{";
    public final static String MODULE_END = "}";
    public final static String MODULE_NULL = "{null}";

    public final static String FIELD_NAME = "\"";

    protected StringBuilder listToString(List list, StringBuilder stringBuilder){

        if (stringBuilder == null){
            throw new RuntimeException("LogImpl.listToString.stringBuilder is null");
        }
        if (list == null){
            stringBuilder.append("LogImpl.listToString.list is null");
            return stringBuilder;
        }
        if (list.size() > 200){
            stringBuilder.append("list is more than 200");
        }

        stringBuilder.append(LIST_BEGIN);
        for (int i = 0; i < list.size(); i++){
            if (list.get(i) == null){
                stringBuilder.append(MODULE_NULL);
            }
            if (list.get(i) instanceof List){
                listToString((List) list.get(i), stringBuilder);
            }

            stringBuilder.append(objectToString(list.get(i)));
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
            stringBuilder.append(FIELD_NAME);
            stringBuilder.append(field.getName());
            stringBuilder.append(FIELD_NAME);
            stringBuilder.append(":");
            try {

                if (List.class.isAssignableFrom(field.get(object).getClass())){
                    listToString((List) field.get(object), stringBuilder);
                }else {
                    stringBuilder.append(field.get(object));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("LogImpl.objectToString field IllegalAccessException");
            }
        }
        stringBuilder.append(MODULE_END);
        return stringBuilder;
    }
}
