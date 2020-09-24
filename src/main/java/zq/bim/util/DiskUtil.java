package zq.bim.util;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 功能：获取硬盘序列号工具类
 * 作者：刘梓江
 * 时间：2020/8/27  16:06
 */
public class DiskUtil {

    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }


    /**
     * 获取windows下的硬盘序列号信息
     * @return
     * @throws Exception
     */
    private static String getIdentifierByWindows() throws Exception {
        String result = "";
        Process process = Runtime.getRuntime().exec("cmd /c dir C:");
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

        String line;
        while ((line = br.readLine()) != null) {
            if (line.indexOf("卷的序列号是 ") != -1) {
                result = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length(), line.length());
                break;
            }
        }
        //log.info("Windows Identifier is: {}", result);
        br.close();
        return result;
    }

    /**
     * 获取Linux下的硬盘序列号信息
     * @return
     * @throws Exception
     */
    private static String getIdentifierByLinux() throws Exception {
        String[] cmd = {"fdisk", "-l"};

        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        String str1 = sb.toString();
        String str2 = str1.split("identifier:")[1].trim();
        String result = str2.split("Device Boot")[0].trim();
        //log.info("Linux Identifier is: {}", result);
        br.close();

        return result;
    }

    /**
     * 获取本机的硬盘序列号
     * @return
     */
    public static String getDiskId()throws Exception {
        String os = getOSName();
        String diskId = "";
        if (os.startsWith("windows")) {
            return getIdentifierByWindows();
        } else if (os.startsWith("linux")) {
            return getIdentifierByLinux();
        }
        if(!StringUtils.isEmpty(diskId)){
            diskId="null";
        }
        return diskId;
    }
}
    
    
    