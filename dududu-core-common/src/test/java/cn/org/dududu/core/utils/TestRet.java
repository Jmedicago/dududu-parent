package cn.org.dududu.core.utils;

import cn.org.dududu.core.consts.Codes;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestRet {

    @Test
    public void testRet() {
        Map<String, Object> data = new HashMap<>();
        data.put("date", new Date());
        System.out.println(Ret.me().setCode(Codes.UN_AUTHORIZE).setData(data));
    }
}
