package com.example.tracker;

public class Coordinate implements  JsonCoordinate{
    private String lat;
    private String updatedAt;

    public Coordinate(String lat, String updatedAt) {
        this.lat = lat;
        this.updatedAt = updatedAt;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
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
                "lat='" + lat + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
