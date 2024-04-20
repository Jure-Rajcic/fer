package com.example.dz02;

public class LocationRange {
    private Location start;
    private Location end;

    public LocationRange(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "LocationRange [start=" + start + ", end=" + end + "]";
    }
}
