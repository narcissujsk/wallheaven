package wall.util;

import com.google.gson.Gson;

/**
 * @program: cps-serviceapi
 * @description: Gson
 * @author: jiangsk@inspur.com
 * @create: 2018-11-30 10:05
 **/
public class GsonUtil {

    static Gson gson=new Gson();

    public static String toJson(Object object){
        return gson.toJson(object);
    }
}
