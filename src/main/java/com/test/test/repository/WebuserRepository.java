package com.test.test.repository;

import com.test.test.model.Webuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WebuserRepository extends JpaRepository<Webuser, UUID> {

    Optional<Webuser> findByUsername(String username);

}
