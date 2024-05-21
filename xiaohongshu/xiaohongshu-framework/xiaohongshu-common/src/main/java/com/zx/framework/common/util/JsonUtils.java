package com.zx.framework.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zx.framework.common.constant.DateConstants;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JsonUtils {

    //从Java对象创建JSON. 从Java对象生成JSON的过程也被称为序列化Java对象到JSON
    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    static {
        //OBJECT_MAPPER.configure设置日期格式、设置序列化/反序列化的特性  false
        //在反序列化的过程中，如果遇到未知属性将不报错
        //{"id":null,"username":"你好","passwordXXXX":"123456"}
        //对应字段为id username password
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //让空对象也可以序列化
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        //JavaTimeModule用于指定序列化和反序列化规则
        JavaTimeModule javaTimeModule=new JavaTimeModule();
        //支持LocalDateTime
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateConstants.Y_M_D_H_M_S_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateConstants.Y_M_D_H_M_S_FORMAT)));


        OBJECT_MAPPER.registerModules(new JavaTimeModule()); //解决localdatatime序列化问题
    }

    /**
     * 将对象转成json字符串
     * @param obj
     * @return
     */

   // @SneakyThrows:
    // 这是 Lombok 提供的一个注解，用于简化异常处理。
    // 它会将被标注的方法中的受检异常转换为不受检异常，使得代码看起来更加简洁。
     @SneakyThrows
    public static String toJsonString(Object obj){
        //writeValue（参数，obj）：直接将传入的对象序列化为json，并且返回给客户端
        //writeValueAsString（obj）：将传入的对象序列化为json，返回给调用者
         return OBJECT_MAPPER.writeValueAsString(obj);
     }

}
