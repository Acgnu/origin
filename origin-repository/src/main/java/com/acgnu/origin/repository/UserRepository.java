package com.acgnu.origin.repository;

import com.acgnu.origin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByUname(String uname);

    boolean existsByUname(String name);

//    User updateById(User user);
}
