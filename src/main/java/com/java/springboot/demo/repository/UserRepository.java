package com.java.springboot.demo.repository;

import com.java.springboot.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameOrSurnameIgnoreCase(@Param("name") String name, @Param("surname") String surname);
    List<User> findByCreditLimitLessThanEqual(@Param("creditLimit") BigDecimal creditLimit);
}
