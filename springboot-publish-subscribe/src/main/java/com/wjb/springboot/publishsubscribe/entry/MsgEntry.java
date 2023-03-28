package com.wjb.springboot.publishsubscribe.entry;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * <b><code>MsgEntry</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2023/3/3 9:40.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
public class MsgEntry extends ApplicationEvent {

    private String msg;

    @Override
    public String toString() {
        return "MsgEntry{" +
                "msg='" + msg + '\'' +
                ", source=" + source +
                '}';
    }

    public MsgEntry(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public MsgEntry(Object source, Clock clock, String msg) {
        super(source, clock);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
