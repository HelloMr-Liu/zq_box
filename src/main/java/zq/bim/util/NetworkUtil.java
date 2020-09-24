package zq.bim.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 功能：常用获取客户端信息的工具
 * 作者：刘梓江
 * 时间：2020/8/27  15:58
 */
public class NetworkUtil {
    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     */
    public final static String getHostIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ipAddress = null;
        //ipAddress = request.getRemoteAddr();
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取当前本机的MAC地址表示
     *
     * @return
     */
    public static String getHostMacAddress() throws Exception {
        return MACUtil.getMAC();
    }

    /**
     * 获取请求方的MAC地址表示
     *
     * @return
     */
    public static String getHostMacAddress(HttpServletRequest request) throws Exception {
        return MACUtil.getMAC(request);
    }

    /**
     * 获取当前本机主板的序列号
     *
     * @return
     */
    public static String getHostMainBordNumber() throws Exception {
        return MainBordUtil.getMainBordId();
    }

    /**
     * 获取当前本机CPU的序列号
     *
     * @return
     */
    public static String getHostCPUNumber() throws Exception {
        return CPUUtil.getCPUId();
    }


    public static String getHostDiskNumber() throws Exception {
        return DiskUtil.getDiskId();
    }

    /**
     * 获取当前本机CPU的线程数
     *
     * @returnhread
     */
    public static Integer getHostCPUTheadNumber() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取当前本机CPU的核心数
     *
     * @returnhread
     */
    public static Integer getHostCPUCoreNumber() {
        return OSValidator.getNumberOfCPUCores();
    }
}

class OSValidator {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

    public static String getOS() {
        if (isWindows()) {
            return "win";
        } else if (isMac()) {
            return "osx";
        } else if (isUnix()) {
            return "uni";
        } else if (isSolaris()) {
            return "sol";
        } else {
            return "err";
        }
    }

    public static int getNumberOfCPUCores() {
        OSValidator osValidator = new OSValidator();
        String command = "";
        if (osValidator.isMac()) {
            command = "sysctl -n machdep.cpu.core_count";
        } else if (osValidator.isUnix()) {
            command = "lscpu";
        } else if (osValidator.isWindows()) {
            command = "cmd /C WMIC CPU Get /Format:List";
        }
        Process process = null;
        int numberOfCores = 0;
        int sockets = 0;
        try {
            if (osValidator.isMac()) {
                String[] cmd = {"/bin/sh", "-c", command};
                process = Runtime.getRuntime().exec(cmd);
            } else {
                process = Runtime.getRuntime().exec(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                if (osValidator.isMac()) {
                    numberOfCores = line.length() > 0 ? Integer.parseInt(line) : 0;
                } else if (osValidator.isUnix()) {
                    if (line.contains("Core(s) per socket:")) {
                        numberOfCores = Integer.parseInt(line.split("\\s+")[line.split("\\s+").length - 1]);
                    }
                    if (line.contains("Socket(s):")) {
                        sockets = Integer.parseInt(line.split("\\s+")[line.split("\\s+").length - 1]);
                    }
                } else if (osValidator.isWindows()) {
                    if (line.contains("NumberOfCores")) {
                        numberOfCores = Integer.parseInt(line.split("=")[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (osValidator.isUnix()) {
            return numberOfCores * sockets;
        }
        return numberOfCores;
    }
}
    
    
    