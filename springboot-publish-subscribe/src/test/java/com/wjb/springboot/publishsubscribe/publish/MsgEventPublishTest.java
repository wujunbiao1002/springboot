package com.wjb.springboot.publishsubscribe.publish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsgEventPublishTest {

    @Autowired
    private MsgEventPublish msgEventPublishUnderTest;

    @Test
    void testPublishEvent() {
        // Setup
        // Run the test
        msgEventPublishUnderTest.publishEvent("msg1");
        msgEventPublishUnderTest.publishEvent("msg2");
        msgEventPublishUnderTest.publishEvent("msg3");
    }
}
