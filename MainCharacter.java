import processing.core.PImage;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class MainCharacter extends Entity
{

    public MainCharacter(Point position, List<PImage> images)
    {
        super(position, images, 1);
        setDirectionOfTravel("right");
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                this.createAnimationAction(1),
                this.getAnimationPeriod());
        setScheduler(scheduler);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        world.moveEntity(this, new Point(this.getPosition().getX() - 1, this.getPosition().getY()));
        if (this.getPosition().getX() == 0) {
            world.removeEntity(this);
            setDirectionOfTravel("left");
        }
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), 100);
    }

    public void nextImage(String directionOfTravel) {
        int newImageIndex = 0;
        boolean closeLegs = (this.getImageIndex() % 2) == 0;
        switch (this.getDirectionOfTravel()) {
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
