package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipSpecification implements Specification<Ship> {
    private final SearchCriteria criteria;

    public ShipSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Specification<Ship> and(Specification<Ship> other) {
        return null;
    }

    @Override
    public Specification<Ship> or(Specification<Ship> other) {
        return null;
    }


    @Nullable
    @Override
    public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(criteria.getName()!=null)
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
        if(criteria.getPlanet()!=null)
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("planet")), "%" + criteria.getPlanet().toLowerCase() + "%"));
        if(criteria.getShipType()!=null)
            predicates.add(criteriaBuilder.equal(root.get("shipType"), criteria.getShipType()));
        if(criteria.getAfter()!=null)
            predicates.add(criteriaBuilder.greaterThan(root.get("prodDate"), new Date(criteria.getAfter())));
        if(criteria.getBefore()!=null)
            predicates.add(criteriaBuilder.lessThan(root.get("prodDate"), new Date(criteria.getBefore()-3600001)));
        if(criteria.getUsed()!=null)
            predicates.add(criteriaBuilder.equal(root.get("isUsed"), criteria.getUsed()));
        if(criteria.getMinSpeed()!=null)
            predicates.add(criteriaBuilder.greaterThan(root.get("speed"), criteria.getMinSpeed()));
        if(criteria.getMaxSpeed()!=null)
            predicates.add(criteriaBuilder.lessThan(root.get("speed"), criteria.getMaxSpeed()));
        if(criteria.getMinCrewSize()!=null)
            predicates.add(criteriaBuilder.greaterThan(root.get("crewSize"), criteria.getMinCrewSize()));
        if(criteria.getMaxCrewSize()!=null)
            predicates.add(criteriaBuilder.lessThan(root.get("crewSize"), criteria.getMaxCrewSize()));
        if(criteria.getMinRating()!=null)
            predicates.add(criteriaBuilder.greaterThan(root.get("rating"), criteria.getMinRating()));
        if(criteria.getMaxRating()!=null)
            predicates.add(criteriaBuilder.lessThan(root.get("rating"), criteria.getMaxRating()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
