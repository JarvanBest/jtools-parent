package cn.jtool.generator.util;


import cn.jtool.generator.GeneratorProperties;

import java.io.File;

public class SystemHelper {
    public static boolean isWindowsOS = System.getProperty("os.name").toLowerCase().indexOf("windows")>= 0;

    public static String getOutRootDir(){
        String s = GeneratorProperties.getProperty("outRoot");
        File file =new File(s);
        if(!file.exists() && !file .isDirectory()){
            s=new File(s).getAbsolutePath();
        }
        return s;
    }
}
