import processing.core.PImage;

import java.util.List;

public abstract class AnimalEntity extends NPCEntity {
    private List<Point> path = null;

    public AnimalEntity(Point position, List<PImage> images, int animationPeriod,
                        PathingStrategy strategy) {
        super(position, images, animationPeriod, strategy);
    }

    public void setPath(List<Point> path) {
        this.path = path;
    }

    public List<Point> getPath() {
        return path;
    }

    public void nextImage(String directionOfTravel)
    {
        int newImageIndex = 0;
        boolean firstIm = (getImageIndex() % 2) == 0;
        switch(this.getDirectionOfTravel()){
            case "right":
                if (firstIm) newImageIndex = 1;
                else newImageIndex = 0;
                break;
            case "down":
                if (firstIm) newImageIndex = 3;
                else newImageIndex = 2;
                break;
            case "left":
                if (firstIm) newImageIndex = 5;
                else newImageIndex = 4;
                break;
            case "up":
                if (firstIm) newImageIndex = 7;
                else newImageIndex = 6;
                break;
        }
        this.setImageIndex(newImageIndex);
        this.getScheduler().scheduleEvent(this, this.createAnimationAction(1),
                this.getAnimationPeriod());
    }
}
