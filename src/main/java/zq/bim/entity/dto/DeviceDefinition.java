package zq.bim.entity.dto;

/**
 * 功能：封装设备信息属性
 * 作者：刘梓江
 * 时间：2020/9/22  13:13
 */
public class DeviceDefinition {

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备标识编号
     */
    private String deviceNumber;

    /**
     * 设备名称
     */
    private String deviceName;



    /**
     * 设备状态激活还是未激活离线还是在线
     */
    private String deviceStatus;

    /**
     * 设备状态码  1：未激活  2：离线 3：在线
     */
    private String deviceStatusCode;


    public String getDeviceStatusCode() {
        return deviceStatusCode;
    }

    public void setDeviceStatusCode(String deviceStatusCode) {
        this.deviceStatusCode = deviceStatusCode;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
}
    
    
    