package myUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:封装基础的工具方法，如加载配置文件、json序列化等
 * @Author:Anan
 * @Date:2019/8/19
 */
public class CommUtils {
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        // 将配置文件转为输入流
        InputStream in = CommUtils.class.getClassLoader()
                .getResourceAsStream(fileName);
        // 加载配置信息
        try {
            properties.load(in);
        } catch (IOException e) {
            System.out.println("资源文件加载失败");
            e.printStackTrace();
        }
        return properties;
    }
    private static final Gson GSON = new GsonBuilder().create();

    /**
     * 将一个对象序列化为json字符串
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj){
        return GSON.toJson(obj);
    }

    /**
     * 将一个json字符串反序列化为对象
     * @param jsonStr
     * @param objClazz
     * @return
     */
    public static Object jsonToObject(String jsonStr, Class objClazz){
        return GSON.fromJson(jsonStr,objClazz);
    }

    public static boolean strIsNull(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }

}
