package com.travischenn.platform.domain.VO;

import com.travischenn.platform.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : BaseMessage
 * 功能描述    : 基础消息返回对象
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2017/12/10 20:41
 * Created    : IntelliJ IDEA
 * **************************************************************
 */
@Data
@AllArgsConstructor
public class BaseMessage<T> {

    /**
     * 消息编号
     * 处于保密的角度并不是所有消息都能以文本的形式进行传输，这时候就需要用一个编号来代替。编号和文本的对应关系存在枚举中
     * @see com.travischenn.platform.enums
     */
    private String code;

    /**
     * 消息描述
     */
    private String msg;

    /**
     * 消息内容
     */
    private T data;

    /**
     * 单纯而又不做作的只返回结果相关的内容
     * @param resultEnum 结果类型
     */
    public BaseMessage(ResultEnum resultEnum , T message) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getDescribe();
        this.data = message;
    }

}
