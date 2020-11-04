package cn.jtool.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

/**
 * 应用配置工具类
 *
 * @author Jarvan
 */
public class ConfigUtils extends PropertyPlaceholderConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
    private static Properties properties;
    private static final String MyConfigPath = "myconf";
    private String configPath;

    public ConfigUtils() {
        String value = System.getProperty("myconf");
        if (StringUtils.isNullOrEmpty(value)) {
            File file = new File(System.getProperty("user.dir"));
            String parentPath = file.getParent();
            if (parentPath == null) {
                parentPath = file.getPath();
            }
            value = parentPath + File.separator + MyConfigPath;
            ;
        }
        configPath = value;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        super.processProperties(beanFactory, props);
        Properties oldProperties = properties;
        properties = props;
        if (oldProperties != null) {
            for (String key : oldProperties.stringPropertyNames()) {
                properties.put(key, oldProperties.getProperty(key));
            }
        }
    }

    private Map<String, String> listMyconf() {
        Map<String, String> map = new HashMap<>();
        File file = new File(configPath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return map;
            }
            for (File item : files) {
                if (item.isDirectory()) {
                    map.putAll(listSub(item));
                } else {
                    map.put(item.getName(), item.getPath());
                }
            }
        }
        return map;
    }

    private Map<String, String> listSub(File file) {
        Map<String, String> map = new HashMap<>();
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return map;
            }
            String prefix = file.getName();
            for (File item : files) {
                if (item.isFile()) {
                    map.put(prefix + "/" + item.getName(), item.getPath());
                }
            }
        }
        return map;
    }

    @Override
    public void setLocations(Resource[] locations) {
        Map<String, String> fileNameMap = listMyconf();
        List<Resource> list = new LinkedList<>();
        for (Resource resource : locations) {
            if (resource instanceof ClassPathResource) {
                String path = ((ClassPathResource) resource).getPath();
                String filePath = fileNameMap.get(path);
                if (StringUtils.isNotNullOrEmpty(filePath)) {
                    list.add(resource);
                    //后配置覆盖前配置
                    try {
                        UrlResource urlResource = new UrlResource("file:" + filePath);
                        list.add(urlResource);
                    } catch (MalformedURLException e) {
                        logger.error("加载配置", e);
                    }
                } else {
                    list.add(resource);
                }
            } else {
                list.add(resource);
            }
        }
        Resource[] argsList = new Resource[list.size()];
        list.toArray(argsList);
        super.setLocations(argsList);
    }

    @Override
    public void setLocation(Resource location) {
        if (location instanceof ClassPathResource) {
            List<Resource> list = new LinkedList<>();
            list.add(location);
            Map<String, String> fileNameMap = listMyconf();
            String path = ((ClassPathResource) location).getPath();
            String filePath = fileNameMap.get(path);
            if (StringUtils.isNotNullOrEmpty(filePath)) {
                try {
                    UrlResource urlResource = new UrlResource("file:" + filePath);
                    list.add(urlResource);
                } catch (MalformedURLException e) {
                    logger.error("加载配置2", e);
                }
            }
            Resource[] argsList = new Resource[list.size()];
            list.toArray(argsList);
            super.setLocations(argsList);
        } else {
            super.setLocation(location);
        }
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    public static void setProperty(String key, String value) {
        if (properties == null) {
            properties = new Properties();
        }
        properties.setProperty(key, value);
    }
}