package zq.bim.constant;

/**
 * 描述：全局表字段状态信息
 * @author 刘梓江
 * @date 2020/6/9  14:13
 */
public enum GLOBAL_STATE {
    NO_DEL(false,  "未删除"),
    YES_DEL(true,"已删除");

    public boolean STATE;        //状态值
    public String MESS;         //状态值的描述信息
    GLOBAL_STATE(boolean state, String mess){
        this.STATE=state;
        this.MESS=mess;
    }
}