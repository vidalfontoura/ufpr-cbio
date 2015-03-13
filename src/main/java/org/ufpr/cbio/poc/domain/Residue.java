/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.domain;

/**
 *
 *
 * @author vfontoura
 */
public class Residue {

    private Residue.Point point;
    private ResidueType residueType;

    // /**
    // * @param point
    // * @param residueType
    // */
    // public Residue(Point point, ResidueType residueType) {
    //
    // super();
    // this.point = point;
    // this.residueType = residueType;
    // }
    //
    // /**
    // * @return the point
    // */
    // public Point getPoint() {
    //
    // return point;
    // }
    //
    // /**
    // * @param point the point to set
    // */
    // public void setPoint(Point point) {
    //
    // this.point = point;
    // }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((point == null) ? 0 : point.hashCode());
        result = prime * result + ((residueType == null) ? 0 : residueType.hashCode());
        return result;
    }

    /**
     * @return the point
     */
    public Residue.Point getPoint() {

        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Residue.Point point) {

        this.point = point;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Residue other = (Residue) obj;
        if (point == null) {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
            return false;
        if (residueType != other.residueType)
            return false;
        return true;
    }

    /**
     * 
     */
    public Residue() {

        // TODO Auto-generated constructor stub
    }

    /**
     * @param point
     * @param residueType
     */
    public Residue(Point point, ResidueType residueType) {

        super();
        this.point = point;
        this.residueType = residueType;
    }

    /**
     * @return the residueType
     */
    public ResidueType getResidueType() {

        return residueType;
    }

    /**
     * @param residueType the residueType to set
     */
    public void setResidueType(ResidueType residueType) {

        this.residueType = residueType;
    }

    public static class Point {

        public int x;
        public int y;

        /**
         * @param x
         * @param y
         */
        public Point(int x, int y) {

            super();
            this.x = x;
            this.y = y;
        }

        /**
         * @return the x
         */
        public int getX() {

            return x;
        }

        /**
         * @param x the x to set
         */
        public void setX(int x) {

            this.x = x;
        }

        /**
         * @return the y
         */
        public int getY() {

            return y;
        }

        /**
         * @param y the y to set
         */
        public void setY(int y) {

            this.y = y;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {

            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point other = (Point) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

    }
}