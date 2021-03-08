public abstract class Action {

    private final Entity entity;

    public Action(Entity entity){
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public abstract void executeAction(EventScheduler scheduler) throws InterruptedException;

}
