package com.example.dz02;

public class Location implements Comparable<Location>{

    private int row;
    private int column;

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location))
            return false;
        Location other = (Location) obj;
        return this.row == other.row && this.column == other.column;
    }


    @Override
    public int compareTo(Location o) {
        if (this.row == o.row) {
            return this.column - o.column;
        }
        return this.row - o.row;
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

}
