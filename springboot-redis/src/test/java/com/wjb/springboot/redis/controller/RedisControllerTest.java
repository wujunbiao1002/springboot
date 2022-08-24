package com.wjb.springboot.redis.controller;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>RedisControllerTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/20 11:52.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class RedisControllerTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void test1() {
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    @Test
    void test2() {
        redisTemplate.opsForList().leftPushAll("list", "1", "2", "3");
    }

    @Test
    void test3() {
        Object o = redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set("name1", "lucy1");
                operations.opsForValue().setIfAbsent("name", "lucy2");
                operations.opsForValue().set("name3", "lucy3");
                List exec = operations.exec();
                return exec;
            }
        });
        System.out.println(o.toString());
    }

    @Test
    void test4() {
//        System.out.println(redisTemplate.opsForValue().get("v1"));
        redisTemplate.opsForValue().set("sk1000:qt", "500");
//        redisTemplate.opsForValue().set("v10","spring-boot");
        System.out.println(redisTemplate.opsForValue().get("sk1000:qt"));
    }

    @Test
    void test5() {
        ArrayList<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId("10000");
        student.setName("lucy");
        student.setAge(12);
        student.setFriends(new ArrayList<String>() {{
            add("徐磊");
            add("赵敏");
        }});
        students.add(student);

        Student student2 = new Student();
        student2.setId("10001");
        student2.setName("Arjun");
        student2.setAge(26);
        student2.setFriends(new ArrayList<String>() {{
            add("lucy");
            add("王五");
        }});

        students.add(student2);
        for (Student student1 : students) {
            redisTemplate.opsForValue().set("student:info:" + student1.getId(), JSON.toJSONString(student1));
        }
    }

}

class Student {
    private String id;
    private String name;
    private int age;
    private List<String> friends;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}