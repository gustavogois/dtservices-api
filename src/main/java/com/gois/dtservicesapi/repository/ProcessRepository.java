package com.gois.dtservicesapi.repository;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.model.Requester;
import com.gois.dtservicesapi.repository.process.ProcessDTRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProcessRepository extends JpaRepository<ProcessDT, Long>, ProcessDTRepositoryQuery {

    @Query(" select count(p) " +
            "from ProcessDT p " +
            "where p.id = :idProcess and p.requester = :requester")
    int getQuantityOfRequesterByProcess(@Param("idProcess") Long id
            , @Param("requester") Requester requester);

}
