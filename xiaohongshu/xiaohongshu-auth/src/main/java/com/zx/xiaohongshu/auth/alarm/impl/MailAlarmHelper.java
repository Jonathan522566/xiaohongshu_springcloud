package com.zx.xiaohongshu.auth.alarm.impl;

import com.zx.xiaohongshu.auth.alarm.AlarmInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailAlarmHelper implements AlarmInterface {

    /**
     *发送警告信息
     * @param message
     * @return
     */
    @Override
    public boolean send(String message) {
        log.info("==>【邮件警告】:{}",message);

        //业务逻辑

        return true;
    }
}
