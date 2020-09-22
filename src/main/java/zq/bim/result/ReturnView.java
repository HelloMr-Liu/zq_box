package zq.bim.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Date;


public class ReturnView {
	private Integer code;	//错误编码
	private Integer count;	//数量,分页查询时总数
	private String message;	//错误消息
	private Object data;	//数据
	@JSONField(name="server_time")
	private Long serverTime;//服务器时间


	public static ReturnView success(Integer count, Object data) {
		return new ReturnView(200, count, data);
	}
	public static ReturnView success( Object data) {
		return new ReturnView(200, data);
	}
	public static ReturnView error( String message) {
		return new ReturnView(20004, message);
	}
	public static ReturnView success(String message) {
		return new ReturnView(200, message);
	}

	private ReturnView(Integer code, Integer count, Object data) {
		this.code = code;
		this.count = count;
		this.data = data;
	}
	public ReturnView(Integer code, Object data) {
		this.code = code;
		this.data = data;
	}
	public ReturnView(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	public ReturnView() { 
	}

	public Integer getCode() {
		if(this.code == null) {
			this.code = 200;
		}
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Long getServerTime() {
		return new Date().getTime();
	}
	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	public String toJson() {
		SerializeConfig config = new SerializeConfig();
		// 驼峰转下划线
		config.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
		return JSON.toJSONString(this, config, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
	}
	public <T> String toJson(Class<T> clazz, String... properties) {
		SerializeConfig config = new SerializeConfig();
		// 驼峰转下划线
		config.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz, properties);
		return JSON.toJSONString(this, config, filter, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	
	/**
	 * 枚举序列化
	 * @param enumClass			枚举类
	 * @param indexes			要删除的元素的位置
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toEnumJson(Class<? extends Enum> enumClass, int ... indexes) {
		
		SerializeConfig config = new SerializeConfig();
        config.configEnumAsJavaBean(enumClass);
        Object[] arr = (Object[]) this.getData();
        Object[] newArr = ArrayUtils.removeAll(arr, indexes);
        this.setData(newArr);
		return JSON.toJSONString(this, config, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
	}

}
