package zq.bim.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 *  功能：用户访问令牌注解,用作于用过权限校验
 *  作者：刘梓江
 *  时间：2020/7/24 16:18
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessToken {
}
