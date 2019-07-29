package com.gois.dtservicesapi.repository.process;

import com.gois.dtservicesapi.model.ProcessDT;
import com.gois.dtservicesapi.repository.filter.ProcessDTFilter;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProcessDTRepositoryQueryImpl implements ProcessDTRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<ProcessDT> filter(ProcessDTFilter processDTFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ProcessDT> criteria = builder.createQuery(ProcessDT.class);
        Root<ProcessDT> root = criteria.from(ProcessDT.class);

        Predicate[] predicates = createRestrictions(processDTFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<ProcessDT> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    private Predicate[] createRestrictions(ProcessDTFilter processDTFilter, CriteriaBuilder builder,
                                           Root<ProcessDT> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(processDTFilter.getInternalCode())) {
            predicates.add(
                    builder.like(builder.lower(root.get("internalCode")),
                            "%" + processDTFilter.getInternalCode().toLowerCase() + "%"));
        }

        if (!StringUtils.isEmpty(processDTFilter.getExternalCode())) {
            predicates.add(
                    builder.like(builder.lower(root.get("externalCode")),
                            "%" + processDTFilter.getExternalCode().toLowerCase() + "%"));
        }

        if (processDTFilter.getDataCreationFrom() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("dataCreationFrom"),
                            processDTFilter.getDataCreationFrom()));
        }

        if (processDTFilter.getDataCreationUntil() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("dataCreationUntil"),
                            processDTFilter.getDataCreationUntil()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
