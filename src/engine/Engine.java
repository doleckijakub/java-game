package engine;

import static org.lwjgl.opengl.GL11.*;

public final class Engine {

    private static int windowWidth, windowHeight;
    private static String windowTitle;
    static Window window;

    public static void setWindowSize(int width, int height) {
        windowWidth = width;
        windowHeight = height;
    }

    public static void setWindowTitle(String title) {
        windowTitle = title;
    }

    static Scene scene;

    public static void setScene(Scene scene) {
        Engine.scene = scene;
    }

    public static void init() {
        Window.init(windowWidth, windowHeight, windowTitle);
    }

    public static void run() {
        long lastTime = System.nanoTime(), now;

        while (!window.shouldClose()) {
            window.clear();

            now  = System.nanoTime();

            scene.update((now - lastTime) / 1_000_000_000.f);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            scene.render();

            window.swapBuffers();
            window.pollEvents();

            lastTime = now;
        }

        cleanUp();
    }

    public static void onKeyPressed(KeyPressedEvent e) {
        Keyboard.onKeyPressed(e);
        scene.onKeyPressed(e);
    }

    public static void onKeyReleased(KeyReleasedEvent e) {
        Keyboard.onKeyReleased(e);
        scene.onKeyReleased(e);
    }

    public static void onKeyTyped(KeyTypedEvent e) {
        scene.onKeyTyped(e);
    }

    static void cleanUp() {

    }
}
