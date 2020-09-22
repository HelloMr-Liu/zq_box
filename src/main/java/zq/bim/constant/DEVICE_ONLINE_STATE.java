package zq.bim.constant;

/**
 * 功能：设备信息表字段状态信息
 * 作者：刘梓江
 * 时间：2020/9/21  14:18
 */
public enum DEVICE_ONLINE_STATE {
    YES(true,"在线"),
    NO(false,"离线");
    public boolean STATE;           //状态值
    public String MESS;             //状态值的描述信息

    DEVICE_ONLINE_STATE(boolean state, String mess){
        this.STATE=state;
        this.MESS=mess;
    }

    //获取对应的实例
    public static DEVICE_ONLINE_STATE getInstance(boolean content){
        for(DEVICE_ONLINE_STATE instance: DEVICE_ONLINE_STATE.values()){
            if(instance.STATE&&content||(!instance.STATE)&&(!content)){
                return instance;
            }
        }
        return null;
    }
}
