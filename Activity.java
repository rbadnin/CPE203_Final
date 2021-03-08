public class Activity extends Action{

        // variables
        private final ImageStore imageStore;
        private final WorldModel world;


        // constructor
        public Activity(Entity entity, WorldModel world, ImageStore imageStore)
        {
            super(entity);
            this.world = world;
            this.imageStore = imageStore;
        }


        // methods from Action
        public void executeAction(EventScheduler scheduler) {
            getEntity().executeActivity(world, imageStore, scheduler);
        }

    }

