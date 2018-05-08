package com.mobilesmashers;

import com.mobilesmashers.HelpClasses.Point;

public class Line {

    private Point initialPosition;
    private Point target;

    public Line() {
    }

    public Line(Point initialPosition, Point target) {
        this.initialPosition = initialPosition;
        this.target = target;
    }

    public Point getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Point value) {
        initialPosition = value;
    }

    public Point getTarget() {
        return target;
    }

    public void setTarget(Point value) {
        target = value;
    }
}

