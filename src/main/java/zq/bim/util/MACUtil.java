package zq.bim.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：MAC地址工具类
 * 作者：刘梓江
 * 时间：2020/8/27  16:00
 */
public class MACUtil {


    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();//获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
    }

    /**
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取. 如果有特殊系统请继续扩充新的取mac地址方法.
     * @return mac地址
     */
    public static String getMAC_linux() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令，一般取eth0作为本地主网卡
            process = Runtime.getRuntime().exec("ifconfig eth0");
            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //("获取MAC信息错误",e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                //("获取MAC信息错误",e);
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
     * 获取widnows网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getMAC_windows() {
        InetAddress ip = null;
        NetworkInterface ni = null;
        List<String> macList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
                    .getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                ni = (NetworkInterface) netInterfaces.nextElement();
                // ----------特定情况，可以考虑用ni.getName判断
                // 遍历所有ip
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = (InetAddress) ips.nextElement();
                    if (!ip.isLoopbackAddress() // 非127.0.0.1
                            && ip.getHostAddress().matches(
                            "(\\d{1,3}\\.){3}\\d{1,3}")) {
                        macList.add(getMacFromBytes(ni.getHardwareAddress()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //("获取MAC信息错误",e);
        }
        if (macList.size() > 0) {
            return macList.get(0);
        } else {
            return "";
        }

    }

    private static String getMacFromBytes(byte[] bytes) {
        StringBuffer mac = new StringBuffer();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }

    public static String getMAC() throws Exception {
        String os = getOSName();
        String mac = "";
        if (os.startsWith("windows")) {
            mac = getMAC_windows();
        } else if (os.startsWith("linux")) {
            mac = getMAC_linux();
        }
        if(StringUtils.isEmpty(mac)){
            mac="null";
        }
        return mac;
    }


    /**
     * ==================================下面这种方式事获取对方的主机的MAC地址=======================================
     */

    /**
     * Linux执行的cmd获取的MAC命令
     * @param cmd
     * @return
     */
    public static String callCmd(String[] cmd) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine ()) != null) {
                result += line;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * windows执行的cmd获取的MAC命令
     * @param cmd  第一个命令
     * @param another 第二个命令
     * @return   第二个命令的执行结果
     */
    public static String callCmd(String[] cmd, String[] another) {
        String result = "";
        String line = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.waitFor();  //已经执行完第一个命令，准备执行第二个命令
            proc = rt.exec(another);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine ()) != null) {
                result += line;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    /**
     *
     * @param ip  目标ip,一般在局域网内
     * @param sourceString 命令处理的结果字符串
     * @param macSeparator mac分隔符号
     * @return  mac地址，用上面的分隔符号表示
     */
    public static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
        String result = "";
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while(matcher.find()){
            result = matcher.group(1);
            if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                break;  //如果有多个IP,只匹配本IP对应的Mac.
            }
        }
        return result.toUpperCase();
    }



    /**
     *
     * @param ip 目标ip
     * @return   Mac Address
     *
     */
    public static String getMacInWindows(final String ip){
        String result = "";
        String[] cmd = {
                "cmd",
                "/c",
                "ping " +  ip
        };
        String[] another = {
                "cmd",
                "/c",
                "arp -a"
        };

        String cmdResult = callCmd(cmd,another);
        result = filterMacAddress(ip,cmdResult,"-");
        return result;
    }

    /**
     *
     * @param ip 目标ip
     * @return   Mac Address
     *
     */
    public static String getMacInLinux(final String ip){
        String result = "";
        String[] cmd = {
                "/bin/sh",
                "-c",
                "ping " +  ip + " -c 2 && arp -a"
        };
        String cmdResult = callCmd(cmd);
        result = filterMacAddress(ip,cmdResult,":");
        return result;
    }


    /**
     * 获取请求方的MAC地址
     * @return 返回MAC地址
     */
    public static String getMAC(HttpServletRequest request) throws Exception {
        //获取请求方IP
        String requestIp=NetworkUtil.getHostIpAddress(request);

        //获取本机的IP地址
        String ip= InetAddress.getLocalHost().getHostAddress();
        if(requestIp.equals(ip)){
            return getMAC();
        }

        String os = getOSName();
        String mac = "";
        if (os.startsWith("windows")) {
            mac = getMacInWindows(requestIp).trim();
        } else if (os.startsWith("linux")) {
            mac = getMacInLinux(requestIp).trim();
        }
        if(StringUtils.isEmpty(mac)){
            mac="null";
        }
        return mac;
    }


}
    
    
    