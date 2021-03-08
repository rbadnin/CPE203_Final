import processing.core.PImage;

import java.util.List;


public class DirtFiller extends NPCEntity
{

    private int shoutCount;

    public DirtFiller(Point position, List<PImage> images)
    {
        super(position, images, 2, new SingleStepPathingStrategy());
        this.shoutCount = 0;
        setDirectionOfTravel("crouch");
        setNextPosition(new Point(33, 24));
    }

    public int getShoutCount() {
        return shoutCount;
    }

    public void setShoutCount(int shoutCount) {
        this.shoutCount = shoutCount;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        if (this.getPosition().equals(new Point(39, 29)))
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), 10000);
        else
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), 200);
        scheduler.scheduleEvent(this,
                this.createAnimationAction(1),
                this.getAnimationPeriod());
        setScheduler(scheduler);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (this.shoutCount >= 20) {
            setNextPosition(new Point(39, 29));
            this.shoutCount = 0;
        }
        Point startPoint = new Point(33, 24);
        List<Point> path;
        if (!world.canMove(this.getPosition())) {
            path = this.getStrategy().computePath(getPosition(), startPoint,
                    p -> true,
                    this::neighbors,
                    PathingStrategy.CARDINAL_NEIGHBORS);
            if (path.size() != 0) {
                setDirectionOfTravel(getDirectionOfTravel(path.get(0)));
                if (!world.isOccupied(path.get(0)))
                    world.moveEntity(this, path.get(0));
            }
        } else {
            path = this.getStrategy().computePath(getPosition(), getNextPosition(),
                    world::canMove,
                    this::neighbors,
                    PathingStrategy.CARDINAL_NEIGHBORS);
            if (path.size() == 0 || path.get(0).equals(getNextPosition())) {
                setDirectionOfTravel("crouch");
                world.moveEntity(this, getNextPosition());
                world.background[getNextPosition().getY()][getNextPosition().getX()] = new Background("grass",
                        imageStore.getImageList(
                        "grass"));
                world.manager.grassCount += 1;
                world.backgroundType[getNextPosition().getX()][getNextPosition().getY()] =
                        world.manager.mineField[getNextPosition().getX()][getNextPosition().getY()];
                setNextPosition(world.findNextDirt(imageStore));
            } else {
                setDirectionOfTravel(getDirectionOfTravel(path.get(0)));
                if (!world.isOccupied(path.get(0)))
                    world.moveEntity(this, path.get(0));
            }
        }
        scheduleActions(scheduler, world, imageStore);
    }

    public void nextImage(String directionOfTravel)
    {
        int newImageIndex = 0;
        boolean closeLegs = (this.getImageIndex() % 2) == 0;
        switch(this.getDirectionOfTravel()){
            case "right":
                if (closeLegs) newImageIndex = 1;
                else newImageIndex = 0;
                break;
            case "down":
                if (closeLegs) newImageIndex = 3;
                else newImageIndex = 2;
                break;
            case "left":
                if (closeLegs) newImageIndex = 5;
                else newImageIndex = 4;
                break;
            case "up":
                if (closeLegs) newImageIndex = 7;
                else newImageIndex = 6;
                break;
            case "crouch":
                newImageIndex = 8;
                break;
        }
        this.setImageIndex(newImageIndex % this.getImages().size());
        this.getScheduler().scheduleEvent(this,
                this.createAnimationAction(1),
                this.getAnimationPeriod());
    }
}