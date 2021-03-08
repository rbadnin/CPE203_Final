import processing.core.PImage;

import java.util.List;

public abstract class NPCEntity extends Entity {
    private final PathingStrategy strategy;
    private Point nextPosition;

    public NPCEntity(Point position, List<PImage> images, int animationPeriod,
                     PathingStrategy strategy){
        super(position, images, animationPeriod);
        this.strategy = strategy;
    }

    public Point getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(Point nextPosition) {
        this.nextPosition = nextPosition;
    }

    public PathingStrategy getStrategy() {
        return strategy;
    }

    public boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
    }

    public String getDirectionOfTravel(Point nextPoint)
    {
        if (nextPoint.getY() > this.getPosition().getY())
            return "down";
        else if(nextPoint.getY() < this.getPosition().getY())
            return "up";
        else if (nextPoint.getX() > this.getPosition().getX())
            return "right";
        else
            return "left";
    }

}
