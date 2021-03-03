package com.wjb.springboot.datajpa.repository;

import com.wjb.springboot.datajpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <b><code>UserRepository</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/4 0:10.
 *
 * @author Wu Junbiao
 * @version 1.0.0
 * @since springboot 0.0.1
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
