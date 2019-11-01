package cn.org.dududu.core.utils;

import cn.org.dududu.core.consts.Codes;
import org.junit.Test;

public class TestRet {

    @Test
    public void testRet() {
        System.out.println(Ret.me().setCode(Codes.UN_AUTHORIZE));
    }
}
