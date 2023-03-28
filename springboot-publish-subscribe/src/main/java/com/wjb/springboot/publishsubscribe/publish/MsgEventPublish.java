package com.wjb.springboot.publishsubscribe.publish;

import com.wjb.springboot.publishsubscribe.entry.MsgEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * <b><code>MsgEventPublish</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2023/3/3 9:40.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Service
public class MsgEventPublish {
    @Autowired
    private ApplicationContext applicationContext;

    public void publishEvent(String msg) {
        MsgEntry msgEntry = new MsgEntry("publishEvent",msg);
        applicationContext.publishEvent(msgEntry);
    }
}
