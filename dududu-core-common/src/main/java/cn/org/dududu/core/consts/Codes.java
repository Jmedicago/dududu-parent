package cn.org.dududu.core.consts;

import cn.org.dududu.core.annotation.Const;

/**
 * 通用消息状态常量
 */
public interface Codes {
    @Const("成功")
    int SUCCESS = 0;
    @Const("失败")
    int FAILED = 1;
    @Const("未授权")
    int UN_AUTHORIZE = 2;
    @Const("非法访问")
    int ILLEGAL_ACCESS = 3;
}
