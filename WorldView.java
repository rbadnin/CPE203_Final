
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

/*
WorldView ideally mostly controls drawing the current part of the whole world
that we can see based on the viewport
*/

final class WorldView
{
   // variables
   private final PApplet screen;
   private final WorldModel world;
   private final int tileWidth;
   private final int tileHeight;
   private final Viewport viewport;


   // constructor
   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }


   // public methods

   public void drawViewport()
   {
      drawBackground();
      drawEntities();
   }


   // private methods
   private void drawBackground()
   {
      for (int row = 0; row < viewport.getNumRows(); row++)
      {
         for (int col = 0; col < viewport.getNumCols(); col++)
         {
            Point worldPoint = viewport.viewportToWorld(col,
                    row);
            Optional<PImage> image = world.getBackgroundImage(worldPoint);
            if (image.isPresent())
            {
               screen.image(image.get(), col * tileWidth,
                       row * tileHeight);
            }
         }
      }
   }

   private void drawEntities()
   {
      for (Entity entity : world.getEntities())
      {
         Point pos = entity.getPosition();

         if (viewport.contains(pos))
         {
            Point viewPoint = viewport.worldToViewport(pos.getX(),
                    pos.getY());
            screen.image(entity.getCurrentImage(),
                    viewPoint.getX() * tileWidth,
                    viewPoint.getY() * tileHeight);
         }
      }
   }

}
