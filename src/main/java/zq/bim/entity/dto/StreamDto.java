package zq.bim.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class StreamDto {
	
	private Integer pid=0;
	
	@JSONField(name="pull_url")
    private String pullStreamAddress;

	@JSONField(name="push_url")
    private String pushStreamAddress;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPullStreamAddress() {
		return pullStreamAddress;
	}

	public void setPullStreamAddress(String pullStreamAddress) {
		this.pullStreamAddress = pullStreamAddress;
	}

	public String getPushStreamAddress() {
		return pushStreamAddress;
	}

	public void setPushStreamAddress(String pushStreamAddress) {
		this.pushStreamAddress = pushStreamAddress;
	}
}
