package com.gois.dtservicesapi.repository;

import com.gois.dtservicesapi.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}
