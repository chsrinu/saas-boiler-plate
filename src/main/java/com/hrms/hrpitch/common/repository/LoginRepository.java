package com.hrms.hrpitch.common.repository;

import com.hrms.hrpitch.common.dao.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

}
