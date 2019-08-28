package cz.cleverfarm.mrtest.dto;

import com.google.common.base.Preconditions;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public class BoundaryPoint {

    private final double x;

    private final double y;

    public BoundaryPoint(double x, double y) {
        this.x = Preconditions.checkNotNull(x);
        this.y = Preconditions.checkNotNull(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(y)
                .append(" ")
                .append(x)
                .toString();
    }
}
