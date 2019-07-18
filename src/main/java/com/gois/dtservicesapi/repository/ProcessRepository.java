package com.gois.dtservicesapi.repository;

import com.gois.dtservicesapi.model.ProcessDT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<ProcessDT, Long> {

}
