package cn.org.dududu.core.exception;

import cn.org.dududu.core.consts.Codes;
import cn.org.dududu.core.utils.Ret;

public class BisException extends RuntimeException {
    private int code = Codes.FAILED;

    private String message = "";

    private String info = "";

    private Object data = null;

    private Ret ret = null;

    public static BisException me() {
        return new BisException();
    }

    public BisException() {

    }

}
