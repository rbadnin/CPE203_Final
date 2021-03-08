import processing.core.PImage;

import java.util.List;

public class Scorpion extends AnimalEntity{
    private Point startingPos = new Point(0,0);
    private int scorpionRandomCount = 0;

    public Scorpion(Point position, List<PImage> images)
    {
        super(position, images, 2, new AStarPathingStrategy());
        setDirectionOfTravel("up");
    }


    public int getScorpionRandomCount() {
        return scorpionRandomCount;
    }

    public void setStartingPos(Point startingPos) {
        this.startingPos = startingPos;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), 50);
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
        setScheduler(scheduler);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (this.getPosition().equals(startingPos)){
            this.setPosition(new Point(this.getPosition().getX() + 1, this.getPosition().getY()));
            world.tryAddEntity(this);
            startingPos = new Point(0,0);
            int randomX = (int) (Math.random() * 29 + 1) + world.manager.XOFFSET;
            int randomY = (int) (Math.random() * 20) + world.manager.XOFFSET;
            setNextPosition(new Point(randomY, randomX));
            setPath(this.getStrategy().computePath(getPosition(), getNextPosition(),
                    p -> (world.canMove(p) && (world.backgroundType[p.getX()][p.getY()] == null || !world.backgroundType[p.getX()][p.getY()].equals("Mine"))),
                    this::neighbors,
                    PathingStrategy.CARDINAL_NEIGHBORS));
        }

        else if (getPosition().equals(getNextPosition()) || getPath().size() == 0) {
            if (scorpionRandomCount < 10)
                setNextPosition(new Point((int) (Math.random() * 29), (int) (Math.random() * 39)));
            scorpionRandomCount++;
            setPath(this.getStrategy().computePath(getPosition(), getNextPosition(),
                    p -> (world.canMove(p) && (world.backgroundType[p.getX()][p.getY()] == null || !world.backgroundType[p.getX()][p.getY()].equals("Mine"))),
                    this::neighbors,
                    PathingStrategy.CARDINAL_NEIGHBORS));
        }
        else if (getPath().size() > 0){
            setDirectionOfTravel(getDirectionOfTravel(getPath().get(0)));
            world.moveEntity(this, getPath().get(0));
            getPath().remove(getPath().get(0));


        }
        scheduleActions(scheduler, world, imageStore);
    }


}
