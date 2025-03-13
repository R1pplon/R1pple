package com.example.r1pple.repository;

import com.example.r1pple.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // 查询所有用户
    @Query("SELECT u FROM User u ORDER BY u.createTime DESC")
    List<User> findAllUsersWithRelations();
}