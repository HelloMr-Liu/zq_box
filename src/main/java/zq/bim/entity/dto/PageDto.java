package zq.bim.entity.dto;

import javax.validation.constraints.Min;

import com.alibaba.fastjson.annotation.JSONField;

public class PageDto{
	@Min(value =1,message="页码大于1")
	@JSONField(name="page_no")
//	@NotNull(message = "页码不为空")
	private Integer pageNo;
	
	@Min(value =1,message="页大小大于1")
	@JSONField(name="page_size")
//	@NotNull(message = "页大小不为空")
	private Integer pageSize;
	private Integer pageIndex;
	/**
	 * 获取页码
	 * @return
	 */
	public Integer getPageNo() {
		return pageNo;
	}
	/**
	 * 设置页码（从1开始）
	 * @param pageNo
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * 获取页大小
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * 设置页大小（从1开始）
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 获取页下标
	 * @return
	 */
	public Integer getPageIndex() {
		if(pageNo != null && pageSize != null && pageNo >0 && pageSize >0) {
			pageIndex = (pageNo -1) * pageSize;
		}else {
			pageIndex = null;
		}
		return pageIndex;
	}
	
}
