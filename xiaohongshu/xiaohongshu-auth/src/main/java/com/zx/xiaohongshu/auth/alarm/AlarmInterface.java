package com.zx.xiaohongshu.auth.alarm;

public interface AlarmInterface {

    /**
     * 发送警告信息
     *
     * @param message
     * @return
     */
    boolean send(String message);


}
