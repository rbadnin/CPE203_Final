import processing.core.PImage;

import java.util.List;

public class EntityFactory {


    public static MainCharacter createMainCharacter(Point position, List<PImage> images)
    {
        return new MainCharacter(position, images);
    }

    public static DirtFiller createDirtFiller(Point position, List<PImage> images)
    {
        return new DirtFiller(position, images);
    }

    public static Bird createBird(Point position, List<PImage> images)
    {
        return new Bird(position, images);
    }

    public static Scorpion createScorpion(Point position, List<PImage> images)
    {
        return new Scorpion(position, images);
    }

}
