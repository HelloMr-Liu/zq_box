package zq.bim.constant;

/**
 * 功能：设备流信息表操作字段状态信息
 * 作者：刘梓江
 * 时间：2020/9/21  14:18
 */
public enum STREAM_OP_STATE {
	OPERATION_NONE(0,"无状态"),
    OPERATION_INSERT(1,"添加"),
    OPERATION_DELETE(2,"删除"),
    operation_UPDATE(3,"修改"),
    operation_USEING(4,"正在处理中");

    public Integer STATE;           //状态值
    public String MESS;             //状态值的描述信息

    STREAM_OP_STATE(Integer state, String mess){
        this.STATE=state;
        this.MESS=mess;
    }

    //获取对应的实例
    public static STREAM_OP_STATE getInstance(Integer content){
        for(STREAM_OP_STATE instance: STREAM_OP_STATE.values()){
            if(instance.STATE.intValue()==content.intValue()){
                return instance;
            }
        }
        return null;
    }
}
