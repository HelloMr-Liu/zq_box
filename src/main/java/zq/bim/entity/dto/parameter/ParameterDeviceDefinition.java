package zq.bim.entity.dto.parameter;

import org.hibernate.validator.constraints.Length;
import zq.bim.valid.AddValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能：接收设备属性
 * 作者：刘梓江
 * 时间：2020/9/21  16:01
 */
public class ParameterDeviceDefinition {

    /**
     * 设备id
     */
    @NotNull(message = "设备id为空",groups = {QueryValid.class,UpdateValid.class})
    private Long deviceId;

    /**
     * 项目id
     */
    @NotNull(message = "项目id为空",groups = {AddValid.class})
    private Long projectId;

    /**
     * 设备标识
     */
//    @Length(max =100,message = "设备标识长度超过100",groups = {AddValid.class,UpdateValid.class})
//    @NotBlank(message = "设备标识为空",groups = {AddValid.class,UpdateValid.class})
//    private String deviceNumber;

    /**
     * 设备名称
     */
    @Length(max =30,message = "设备名称长度超过30",groups = {AddValid.class,UpdateValid.class})
    @NotBlank(message = "设备名称为空",groups = {AddValid.class,UpdateValid.class})
    private String deviceName;


    /**
     * 设备描述
     */
//    @Length(max =30,message = "设备描述长度超过200",groups = {AddValid.class,UpdateValid.class})
//    @NotBlank(message = "设备描述为空",groups = {AddValid.class,UpdateValid.class})
//    private String deviceDesc;


    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }





}
    
    
    