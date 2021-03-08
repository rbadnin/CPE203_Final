import processing.core.PImage;

import java.util.List;

public abstract class Entity
{
    // variables
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int animationPeriod;
    private EventScheduler scheduler;
    private String directionOfTravel = "right";


    // constructor
    public Entity(Point position, List<PImage> images, int animationPeriod) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.animationPeriod = animationPeriod;
    }


    public String getDirectionOfTravel() {
        return directionOfTravel;
    }

    public void setDirectionOfTravel(String directionOfTravel) {
        this.directionOfTravel = directionOfTravel;
    }

    public EventScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(EventScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public List<PImage> getImages() {
        return images;
    }

    public Point getPosition(){
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getAnimationPeriod()
    {
        return animationPeriod;
    }

    public Animation createAnimationAction(int repeatCount){ return new Animation(this, repeatCount);}

    public PImage getCurrentImage() {return images.get(imageIndex);}



    // abstract methods
    public abstract void nextImage(String a);

    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
