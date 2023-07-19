package com.example.mvc.repositorys;

import com.example.mvc.entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Component
@Scope("singleton")
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    int deleteByEmailAndLogin(String email, String login);


    @Transactional
    @Modifying
    @Query("update User u set u.email = :newEmail where u.login = :login")
    int updateUserEmailByLogin(@Param("newEmail") String newEmail, @Param("login") String login);
}