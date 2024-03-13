package engine;

public abstract class Scene {

    public abstract void update();
    public abstract void render();

    public void onKeyPressed(KeyPressedEvent e) {}
    public void onKeyReleased(KeyReleasedEvent e) {}
    public void onKeyTyped(KeyTypedEvent e) {}

}
