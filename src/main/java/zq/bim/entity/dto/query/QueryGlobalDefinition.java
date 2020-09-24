package zq.bim.entity.dto.query;

import zq.bim.constant.GLOBAL_STATE;


/**
 * 功能：接收全局分页搜索属性
 * 作者：刘梓江
 * 时间：2020/7/30  14:56
 */
public class QueryGlobalDefinition {

    /**
     * 当前页默认第一页
     */
    private Integer currentPage=1;

    /**
     * 每页显示的条数 默认显示10条
     */
    private Integer pageSize=10;

    /**
     * 删除标志
     */
    private boolean isDel= GLOBAL_STATE.NO_DEL.STATE;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
    
    
    