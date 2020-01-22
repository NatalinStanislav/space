package com.space.repository;

import com.space.model.ShipType;

public class SearchCriteria {
    private String name;
    private String planet;
    private ShipType shipType;
    private Long after;
    private Long before;
    private Boolean isUsed;
    private Double minSpeed;
    private Double maxSpeed;
    private Integer minCrewSize;
    private Integer maxCrewSize;
    private Double minRating;
    private Double maxRating;


    public String getName() {
        return name;
    }

    public SearchCriteria setName(String name) {
        this.name = name;
        return this;
    }

    public String getPlanet() {
        return planet;
    }

    public SearchCriteria setPlanet(String planet) {
        this.planet = planet;
        return this;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public SearchCriteria setShipType(ShipType shipType) {
        this.shipType = shipType;
        return this;
    }

    public Long getAfter() {
        return after;
    }

    public SearchCriteria setAfter(Long after) {
        this.after = after;
        return this;
    }

    public Long getBefore() {
        return before;
    }

    public SearchCriteria setBefore(Long before) {
        this.before = before;
        return this;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public SearchCriteria setUsed(Boolean used) {
        isUsed = used;
        return this;
    }

    public Double getMinSpeed() {
        return minSpeed;
    }

    public SearchCriteria setMinSpeed(Double minSpeed) {
        this.minSpeed = minSpeed;
        return this;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public SearchCriteria setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
        return this;
    }

    public Integer getMinCrewSize() {
        return minCrewSize;
    }

    public SearchCriteria setMinCrewSize(Integer minCrewSize) {
        this.minCrewSize = minCrewSize;
        return this;
    }

    public Integer getMaxCrewSize() {
        return maxCrewSize;
    }

    public SearchCriteria setMaxCrewSize(Integer maxCrewSize) {
        this.maxCrewSize = maxCrewSize;
        return this;
    }

    public Double getMinRating() {
        return minRating;
    }

    public SearchCriteria setMinRating(Double minRating) {
        this.minRating = minRating;
        return this;
    }

    public Double getMaxRating() {
        return maxRating;
    }

    public SearchCriteria setMaxRating(Double maxRating) {
        this.maxRating = maxRating;
        return this;
    }


}
