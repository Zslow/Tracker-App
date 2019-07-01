package com.example.tracker;

public class CoordinateB implements JsonCoordinate{
    private String lng;
    private String updatedAt;

    public CoordinateB(String lat, String updatedAt) {
        this.lng = lng;
        this.updatedAt = updatedAt;
    }

    public String getLng() {
        return lng;
    }
    public String getLat() {
        return lng;
    }

    public void setLat(String lng) {
        this.lng = lng;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "lng='" + lng + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
