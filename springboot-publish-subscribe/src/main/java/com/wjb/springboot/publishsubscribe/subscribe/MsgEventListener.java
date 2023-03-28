package com.wjb.springboot.publishsubscribe.subscribe;

import com.wjb.springboot.publishsubscribe.entry.MsgEntry;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <b><code>EventListener</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2023/3/3 9:28.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@Component
public class MsgEventListener {

    @Async
    @EventListener
    public void listenEvent(MsgEntry msgEntry){
        System.out.println(msgEntry.toString());
    }
}
