package cn.org.dududu.core.utils;

import cn.org.dududu.core.consts.Codes;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Ret implements Serializable {
    public static final String QUOTE = "\"";

    private boolean success = true;

    private int code = Codes.SUCCESS;

    private String message = "";

    private String info = "";

    private Object data = null;

    public static Ret me() {
        return new Ret();
    }

    public Ret() {
        this.code = Codes.SUCCESS;
    }

    public Ret(int code) {
        this.code = code;
    }

    public Ret(boolean isSuccess, int code) {
        this.success = isSuccess;
        this.code = code;
    }

    public Ret(boolean success, int code, Object data) {
        this.data = data;
        this.code = code;
        this.success = success;
    }

    public Ret setSuccess(boolean isSuccess) {
        this.success = isSuccess;
        if (!this.success)
            this.code = Codes.FAILED;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Ret setCode(int code) {
        this.code = code;
        if (Codes.SUCCESS == code) {
            this.success = true;
        } else if (Codes.FAILED == code) {
            this.success = false;
        } else if (Codes.ILLEGAL_ACCESS == code) {
            this.success = false;
        } else if (Codes.UN_AUTHORIZE == code) {
            this.success = false;
        }
        return this;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        String _msg = ConstUtils.getMsgConstName(this.code);
        if (_msg == null)
            _msg = "";
        this.message = _msg;
        return this.message;
    }

    public Ret setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getInfo() {
        return this.info;
    }

    public Ret setData(Object data) {
        this.data = data;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append(QUOTE + "code" + QUOTE + ":");
        sb.append(code);
        sb.append("," + QUOTE + "success" + QUOTE + ":");
        sb.append(success);
        sb.append("," + QUOTE + "message" + QUOTE + ":");
        sb.append(QUOTE + encode(this.getMessage()) + QUOTE);
        if (StringUtils.isNotBlank(info)) {
            sb.append("," + QUOTE + "info" + QUOTE + ":");
            sb.append(QUOTE + encode(info) + QUOTE);
        }
        if (null != this.data) {
            sb.append("," + QUOTE + "data" + QUOTE + ":");
            if (data instanceof String) {
                sb.append(data);
            } else {
                sb.append(JSON.toJSONString(data));
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private String encode(String text) {
        if (null == text)
            return "";
        String _text = text.replace("\"", "“");
        return _text;
    }
}
