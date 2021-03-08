import processing.core.PImage;

import java.util.List;

public class Bird extends AnimalEntity
{
    public Bird(Point position, List<PImage> images)
    {
        super(position, images, 2, new DFSPathingStrategy());
        setDirectionOfTravel("right");
        setNextPosition(new Point(0,0));
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), 50);
        scheduler.scheduleEvent(this, this.createAnimationAction(1), this.getAnimationPeriod());
        setScheduler(scheduler);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (getPath() == null || getPosition().equals(getNextPosition()) || getPath().size() == 0) {
            setNextPosition(new Point((int) (Math.random() * 29), (int) (Math.random() * 39)));
            setPath(this.getStrategy().computePath(getPosition(), getNextPosition(),
                    p -> (world.backgroundType[p.getX()][p.getY()] == null || !world.backgroundType[p.getX()][p.getY()].equals("Mine")),
                    this::neighbors,
                    PathingStrategy.CARDINAL_NEIGHBORS));
        }
        if (getPath().size() > 0) {
            setDirectionOfTravel(getDirectionOfTravel(getPath().get(0)));
            if (world.withinBounds(getPath().get(0)) && !world.isOccupied(getPath().get(0))) {
                world.moveEntity(this, getPath().get(0));
                getPath().remove(getPath().get(0));
            }
            else if (world.isOccupied(getPath().get(0))){
                System.out.println("ran into something");
                setNextPosition(new Point((int) (Math.random() * 29), (int) (Math.random() * 39)));
                setPath(this.getStrategy().computePath(getPosition(), getNextPosition(),
                        p -> (world.isOccupied(new Point (p.getX(), p.getY())) ||
                                world.backgroundType[p.getX()][p.getY()] == null || !world.backgroundType[p.getX()][p.getY()].equals("Mine")),
                        this::neighbors,
                        PathingStrategy.CARDINAL_NEIGHBORS));
                if (getPath().size() > 0 && world.withinBounds(getPath().get(0)) && !world.isOccupied(getPath().get(0))) {
                    world.moveEntity(this, getPath().get(0));
                    getPath().remove(getPath().get(0));
                }
            }
        }
        scheduleActions(scheduler, world, imageStore);
    }
}
