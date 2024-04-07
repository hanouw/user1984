package com.jpa.user1984.repository;

import com.jpa.user1984.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmsRepository extends JpaRepository<Store, String> {
}
