public class Animation extends Action{

        // variables
        private final int repeatCount;


        // constructor
        public Animation(Entity entity, int repeatCount)
        {
            super(entity);
            this.repeatCount = repeatCount;
        }


        // methods from Action
        public void executeAction(EventScheduler scheduler)
        {
            getEntity().nextImage("left");

            if (repeatCount != 1)
            {
                scheduler.scheduleEvent(getEntity(),
                        getEntity().createAnimationAction(Math.max(repeatCount - 1, 0)),
                        getEntity().getAnimationPeriod());
            }
        }


}


