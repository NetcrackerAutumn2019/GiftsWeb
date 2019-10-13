package com.test.repository;

import com.test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
//    @Query(value = "select * from Users", nativeQuery = true)
//    List<Users> findAll();

    @Override
    List<Users> findAll();

    @Override
    Optional<Users> findById(Long aLong);

    @Override
    <S extends Users> S save(S s);

    //void save(String n);

}
