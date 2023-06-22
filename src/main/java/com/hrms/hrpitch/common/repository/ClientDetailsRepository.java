package com.hrms.hrpitch.common.repository;

import com.hrms.hrpitch.common.dao.ClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, String> {
}
