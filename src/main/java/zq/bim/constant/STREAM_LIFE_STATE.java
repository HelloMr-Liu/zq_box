package zq.bim.constant;

/**
 * 功能：设备流信息表生命字段状态信息
 * 作者：刘梓江
 * 时间：2020/9/21  14:18
 */
public enum STREAM_LIFE_STATE {

    NORMAL(true,"正常"),
    ABNORMAL(false,"异常");
    public boolean STATE;           //状态值
    public String MESS;             //状态值的描述信息

    STREAM_LIFE_STATE(boolean state, String mess){
        this.STATE=state;
        this.MESS=mess;
    }

    //获取对应的实例
    public static STREAM_LIFE_STATE getInstance(boolean content){
        for(STREAM_LIFE_STATE instance: STREAM_LIFE_STATE.values()){
            if(instance.STATE&&content||(!instance.STATE)&&(!content)){
                return instance;
            }
        }
        return null;
    }
}
