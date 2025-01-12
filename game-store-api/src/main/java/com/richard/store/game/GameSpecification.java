package com.richard.store.game;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GameSpecification {

    public static Specification<Game> byGameTitle(String gameTitle) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("title"), gameTitle);
    }
    public static Specification<Game> bySupportedPlatform(SupportedPlatforms platform) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("platform"), platform);
    }

    public static Specification<Game> byCatName(String catName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("name"), catName);
    }
}
