package zq.bim.lock;

import net.dreamlu.mica.core.result.R;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 功能：定义一些操作原子锁
 * 作者：刘梓江
 * 时间：2020/9/27  13:34
 */
public class OperatingLock {

    /**
     * 定义操作项目原子锁
     */
    private static ReentrantReadWriteLock projectLock=new ReentrantReadWriteLock();

    /**
     * 定义操作设备原子锁
     */
    private static ReentrantReadWriteLock deviceLock=new ReentrantReadWriteLock();

    /**
     * 定义操作流原子锁
     */
    private static ReentrantReadWriteLock streamLock=new ReentrantReadWriteLock();

    public static ReentrantReadWriteLock getLock(String lockContent){
        if(lockContent.contains("/project/add")||lockContent.contains("/project/add")){
            return projectLock;
        }else if(lockContent.contains("/device/add")||lockContent.contains("/device/update")){
            return deviceLock;
        }else if(lockContent.contains("/stream/add")||lockContent.contains("/stream/update")){
            return streamLock;
        }
        return null;
    }
}
    
    
    