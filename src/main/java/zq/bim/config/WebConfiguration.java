package zq.bim.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zq.bim.Interceptor.RequestInterceptor;
/**
 * 描述：二次修改web相关的配置
 * @author 刘梓江
 * @date   2020/6/9 14:11
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义用户请求拦截器,拦截用户信息
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
    }

//    /**
//     * 重写转换器、换成FastJson转换器
//     * @param converters
//     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        /*
//         先把JackSon的消息转换器删除.
//         备注: (1)源码分析可知，返回json的过程为:
//                Controller调用结束后返回一个数据对象，for循环遍历conventers，找到支持application/json的HttpMessageConverter，然后将返回的数据序列化成json。
//                具体参考org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor的writeWithMessageConverters方法
//               (2)由于是list结构，我们添加的fastJson在最后。因此必须要将jackson的转换器删除，不然会先匹配上jackson，导致没使用fastJson
//        */
//        for (int i = converters.size() - 1; i >= 0; i--) {
//            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
//                converters.remove(i);
//            }
//        }
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//
//        //自定义fastJson配置
//        FastJsonConfig config = new FastJsonConfig();
//        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
//        serializeConfig.put(Long.class , ToStringSerializer.instance);
//        serializeConfig.put(Long.TYPE , ToStringSerializer.instance);
//        config.setSerializeConfig(serializeConfig);         //配置Long转String类型 ， 解决前后端交互， id过长，失去精度的问题
//        config.setSerializerFeatures(
//                SerializerFeature.WriteMapNullValue,        // 是否输出值为null的字段,默认为false,我们将它打开
//                SerializerFeature.WriteNullListAsEmpty,     // 将Collection类型字段的字段空值输出为[]
//                SerializerFeature.WriteNullStringAsEmpty,   // 将字符串类型字段的空值输出为空字符串
//                SerializerFeature.WriteNullNumberAsZero,    // 将数值类型字段的空值输出为0
//                SerializerFeature.WriteDateUseDateFormat,
//                SerializerFeature.DisableCircularReferenceDetect    // 禁用循环引用
//        );
//        fastJsonHttpMessageConverter.setFastJsonConfig(config);
//        // 添加支持的MediaTypes;不添加时默认为*/*,也就是默认支持全部
//        // 但是MappingJackson2HttpMessageConverter里面支持的MediaTypes为application/json
//        // 参考它的做法, fastjson也只添加application/json的MediaType
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
//        converters.add(fastJsonHttpMessageConverter);
//    }
}
