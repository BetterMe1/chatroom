package config;

import freemarker.template.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Author:Anan
 * @Date:2019/8/19
 */
@WebListener
public class FreeMarkerListener implements ServletContextListener {
    public static final String TEMPLATE_KEY = "_template_";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);//配置版本
        //配置加载ftl的路径
        try {
            cfg.setDirectoryForTemplateLoading(new File
                    ("E:\\workspace2Eclipse\\untitled\\chatroom\\src\\main\\webapp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //配置页面编码
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        sce.getServletContext().setAttribute(TEMPLATE_KEY,cfg);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
