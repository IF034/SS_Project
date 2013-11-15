package com.springapp.mvc.repository;

import com.springapp.mvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.nickname = ?1")
    public User getUser(String nickname);
    @Query("select u from User u where u.openIdIdentity = ?1")
    public User getUserByOpenIdIdentity(String identity);
}
