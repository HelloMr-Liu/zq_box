package zq.bim.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import net.dreamlu.mica.core.utils.BeanUtil;
import zq.bim.exception.BimErrorException;

@Component
public class UserUtil {
	
	private static ThreadLocal<Long> userIdLocal = new ThreadLocal<Long>(); //userid保存
	
	private static ThreadLocal<Long> companyIdLocal=new ThreadLocal<Long>();
	private static ThreadLocal<Long> projectIdLocal=new ThreadLocal<Long>();
	private static ThreadLocal<String> projectCodeLocal = new ThreadLocal<>(); 			//projectCode保存
	
	private static StringRedisTemplate redisTemplate;
	
	@Autowired
	public UserUtil(StringRedisTemplate redisTemplate) {
		UserUtil.redisTemplate=redisTemplate;
	}
	
	/**
	 * 获取当前登录账号
	 * @return
	 */
	public static Long getUserId() {
		return userIdLocal.get();
	}
	public static void setUserId(Long userId) {
		userIdLocal.set(userId);
	}
	public static void removeUserId() {
		userIdLocal.remove();
	}
	
	public static void remove() {
		userIdLocal.remove();
		companyIdLocal.remove();
		projectIdLocal.remove();
		projectCodeLocal.remove();
	}
	
	public static void setProjectId(String projectIdStr) {
		if (!isEmpty(projectIdStr)) {
			try {
				projectIdLocal.set(Long.parseLong(projectIdStr));
			} catch (Exception e) {
			}
		}
	}
	public static Long getProjectId() {
		return projectIdLocal.get();
	}
	
	
	
	public static void validateXM(Long xmId) {
		if(xmId == null) {
			throw new BimErrorException("请重新选择登录项目");
		}
	}
	
	
	/**
	 * 拷贝属性
	 */
	public static<T> T copyProperties(Object source,Class<T> target) {
		return BeanUtil.copyProperties(source, target);
	}
	
	/**
	 * 判断是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if(obj == null || "".equals(obj)) {
			return true;
		}else {
			return false;
		}
	}

	
	/**
	 * 验证数组
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @param groups
	 * @return
	 */
	public static<T> List<T> validateArray(String json,Class<T> clazz, Class<?>... groups) {
		if(json == null || "".equals(json) || "null".equals(json)) {
			json ="[]";
		}
		List<T> object = null;
		try {
			object = JSON.parseArray(json, clazz);
		} catch (Exception e) {
			throw new BimErrorException("传递参数的json格式不正确");
		}
		if(object == null) {
			throw new BimErrorException("传递参数的json格式不正确");
		}
		if (object != null) {
			for (T t : object) {
				validate(t,groups);
			}
		}
		
		
		return object;
	}
	
	/**
	 * 验证json参数
	 * @param json
	 * @param clazz
	 * @param groups
	 * @return
	 */
	public static<T> T validate(String json,Class<T> clazz, Class<?>... groups) {
		if(json == null || "".equals(json) || "null".equals(json)) {
			json ="{}";
//			throw new BimNullException("参数不能为空");
		}
		T object = null;
		try {
			object = JSON.parseObject(json, clazz);
		} catch (Exception e) {
			throw new BimErrorException("传递参数的json格式不正确");
		}
		if(object == null) {
			throw new BimErrorException("传递参数的json格式不正确");
		}
		validate(object,groups);
		
		return object;
	}

	/**
	 * 验证对象参数
	 * @param <T>
	 * @param object
	 * @param groups
	 */
    public static <T> void validate(T object, Class<?>... groups) {
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object,groups);
        //如果有验证信息，则取出来包装成异常返回
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return;
        }
        throw new BimErrorException(convertErrorMsg(constraintViolations));
    }

    private static <T> String convertErrorMsg(Set<ConstraintViolation<T>> set) {
    	
        Map<String, StringBuilder> errorMap = new HashMap<>();
        String property;
        for (ConstraintViolation<T> cv : set) {
            //这里循环获取错误信息，可以自定义格式
            property = cv.getPropertyPath().toString();
            if (errorMap.get(property) != null) {
                errorMap.get(property).append("," + cv.getMessage());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(cv.getMessage());
                errorMap.put(property, sb);
            }
        }
        Collection<StringBuilder> values = errorMap.values();
        StringBuilder s = new StringBuilder();
        boolean flag = false;
        for (StringBuilder sb : values) {
        	if (flag) {
				s.append(";");
			}
			s.append(sb.toString());
			flag=true;
		}
        return s.toString();
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
