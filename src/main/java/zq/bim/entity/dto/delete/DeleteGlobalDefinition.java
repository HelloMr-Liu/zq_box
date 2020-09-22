package zq.bim.entity.dto.delete;



import zq.bim.constant.GLOBAL_STATE;
import zq.bim.valid.DeleteValid;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 功能：接收全局删除属性
 * 作者：刘梓江
 * 时间：2020/8/11  10:16
 */
public class DeleteGlobalDefinition {

    /**
     * 存储唯一id集合
     */
    @NotEmpty(message = "id列表集为空",groups = {DeleteValid.class})
    private List<Long> deleteIds;

    /**
     * 删除标志
     */
    private boolean isDel= GLOBAL_STATE.YES_DEL.STATE;


    public DeleteGlobalDefinition() {
    }
    public DeleteGlobalDefinition(@NotEmpty(message = "id列表集不能为空") List<Long> deleteIds) {
        this.deleteIds = deleteIds;
        this.isDel =  GLOBAL_STATE.YES_DEL.STATE;
    }


    public List<Long> getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(List<Long> deleteIds) {
        this.deleteIds = deleteIds;
    }

    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean del) {
        isDel = del;
    }
}
    
    
    