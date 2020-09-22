package zq.bim.constant;

/**
 * 功能：设备信息表关联字段状态信息
 * 作者：刘梓江
 * 时间：2020/9/21  14:18
 */
public enum DEVICE_RELEVANCE_STATE {
    YES(true,"已激活"),
    NO(false,"未激活");
    public boolean STATE;           //状态值
    public String MESS;             //状态值的描述信息

    DEVICE_RELEVANCE_STATE(boolean state, String mess){
        this.STATE=state;
        this.MESS=mess;
    }

    //获取对应的实例
    public static DEVICE_RELEVANCE_STATE getInstance(boolean content){
        for(DEVICE_RELEVANCE_STATE instance: DEVICE_RELEVANCE_STATE.values()){
            if(instance.STATE&&content||(!instance.STATE)&&(!content)){
                return instance;
            }
        }
        return null;
    }
}
