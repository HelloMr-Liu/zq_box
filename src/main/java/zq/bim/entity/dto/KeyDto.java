package zq.bim.entity.dto;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;

import zq.bim.entity.dto.validate.DeviceNumberView;


public class KeyDto {
	
	@JSONField(name="equipment_id")
	@NotNull(message = "设备标识不能为空",groups = DeviceNumberView.class)
	private String deviceNumber;

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
}
