package zq.bim.constant;

/**
 * 功能：用户信息表字段状态信息
 * 作者：刘梓江
 * 时间：2020/9/21  14:18
 */
public enum USER_TABLE_STATE {
    USER_TYPE_T1("T1","普通人员"),
    USER_TYPE_T2("T2","管理人员");

    public String STATE;            //状态值
    public String MESS;             //状态值的描述信息

    USER_TABLE_STATE(String value, String mess){
        this.STATE=value;
        this.MESS=mess;
    }
  
    //获取对应的实例
    public static USER_TABLE_STATE getInstance(String content){
        for(USER_TABLE_STATE instance: USER_TABLE_STATE.values()){
            if(instance.STATE.equals(content)||instance.MESS.equals(content)){
                return instance;
            }
        }
        return null;
    }
}
