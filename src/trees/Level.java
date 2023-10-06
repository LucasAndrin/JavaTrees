package trees;

public abstract class Level {

    int level;

    protected Level(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
