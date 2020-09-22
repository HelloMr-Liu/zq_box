package zq.bim.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import zq.bim.entity.dto.validate.NameView;

import javax.validation.constraints.NotBlank;


public class SearchDto extends PageDto {
	//排序常量
	/**
	 * 创建时间排序
	 */
	public static final String ORDER_TIME = "time";
	/**
	 * 名称排序
	 */
	public static final String ORDER_NAME = "name";
	/**
	 * 排序号排序
	 */
	public static final String ORDER_SORT = "sort";
	/**
	 * 大小排序
	 */
	public static final String ORDER_SIZE = "size";	

	@NotBlank(message="名称不能为空",groups= {NameView.class})
	@JSONField(name="name")
	private String name;
	private String order;	//排序，time,name,sort,size
	private Boolean desc;	//是否降序

	/**
	 * 生成排序sql
	 * @param otherSortCondition	额外的排序条件
	 * @return
	 */
	public String createSortSql(String... otherSortCondition) {
		StringBuilder sb = new StringBuilder();
		sb.append("ORDER BY ");
		String orderDesc = "";
		if(this.getDesc()) {
			orderDesc = " DESC";
		}
		for (String str : otherSortCondition) {
			sb.append(str);
			sb.append(",");
		}
		if(ORDER_NAME.equalsIgnoreCase(this.getOrder())) {
			sb.append(" name ");
		}else if(ORDER_SORT.equalsIgnoreCase(this.getOrder())) {
			sb.append(" SORT ");
		}else if(ORDER_TIME.equalsIgnoreCase(this.getOrder())) {
			sb.append(" CREATE_TIME ");
		}else if (ORDER_SIZE.equalsIgnoreCase(this.getOrder())) {
			sb.append(" size ");
		}else {
			sb.append(" UPDATE_TIME ");
		}
		sb.append(orderDesc);
		
		return sb.toString();
	}
	
	/**
	 * 生成分页sql
	 * @return
	 */
	public String createPageSql() {
		if(this.getPageIndex() != null) {
			return "LIMIT "+this.getPageIndex()+","+this.getPageSize();
		}
		return null;
	}
	/**
	 * 获取排序语句
	 * @param otherSortCondition	额外的排序条件
	 * @return
	 */
	public String getSortSql(String... otherSortCondition) {
		return createSortSql(otherSortCondition);
	}
	public String getPageSql() {
		return createPageSql();
	}
	
	public String getName() {
		return name == null ? null:name.trim();
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrder() {
		if(this.order == null) {
			this.order = "time";
		}else {
			return order.trim();
		}
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Boolean getDesc() {
		if(this.desc == null) {
			this.desc = true;
		}
		return desc;
	}
	public void setDesc(Boolean desc) {
		this.desc = desc;
	}

}
