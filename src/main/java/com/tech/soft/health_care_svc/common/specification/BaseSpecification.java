package com.tech.soft.health_care_svc.common.specification;

import org.springframework.data.jpa.domain.Specification;

public abstract class BaseSpecification<T> {

    public static Specification<Object> active() {
        return null;
    }

    protected Specification<T> isActive() {

        return (root, query, cb) ->
                cb.isTrue(root.get("active"));
    }

    protected Specification<T> isInactive() {

        return (root, query, cb) ->
                cb.isFalse(root.get("active"));
    }
}