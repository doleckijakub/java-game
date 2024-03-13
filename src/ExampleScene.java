import engine.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;

public class ExampleScene extends Scene {

    public static class Vertex3p3c extends Vertex {
        public float3 position;
        public float3 color;

        public Vertex3p3c(float3 position, float3 color) {
            this.position = position;
            this.color = color;
        }

        @Override
        public float[] serialize() {
            return new float[] {
                    position.x, position.y, position.z,
                    color.x, color.y, color.z,
            };
        }
    }

    private final Shader shader;
    private final VertexArray vao;

    public ExampleScene() {
        Vertex3p3c[] vertices = new Vertex3p3c[] {
               new Vertex3p3c(new float3(-0.5f, -0.5f * (float) Math.sqrt(3) / 3.f, 0.0f), new float3(0.0f, 0.0f, 0.0f)),
               new Vertex3p3c(new float3( 0.5f, -0.5f * (float) Math.sqrt(3) / 3.f, 0.0f), new float3(0.0f, 0.0f, 1.0f)),
               new Vertex3p3c(new float3( 0.0f,         (float) Math.sqrt(3) / 3.f, 0.0f), new float3(0.0f, 1.0f, 0.0f)),
               new Vertex3p3c(new float3(-.25f,  0.5f * (float) Math.sqrt(3) / 6.f, 0.0f), new float3(0.0f, 1.0f, 1.0f)),
               new Vertex3p3c(new float3( .25f,  0.5f * (float) Math.sqrt(3) / 6.f, 0.0f), new float3(1.0f, 0.0f, 0.0f)),
               new Vertex3p3c(new float3( 0.0f, -0.5f * (float) Math.sqrt(3) / 3.f, 0.0f), new float3(1.0f, 0.0f, 1.0f)),
        };

        int[] indices = new int[] {
                0, 3, 5,
                3, 2, 4,
                5, 4, 1,
        };

        this.shader = new Shader("src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        this.shader.bind();

        this.vao = new VertexArray();
        this.vao.bind();

        VertexBuffer<Vertex3p3c> vbo = new VertexBuffer<>(vertices);
        vbo.bind();

        IndexBuffer ibo = new IndexBuffer(indices);
        ibo.bind();

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, false, 6 * 4, 0);
        vao.linkAttrib(vbo, 1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);
    }

    private float scale = 1.0f;

    @Override
    public void update(float deltaTime) {
        if (Keyboard.isKeyPressed(GLFW_KEY_W)) {
            scale += deltaTime;
        }

        if (Keyboard.isKeyPressed(GLFW_KEY_S)) {
            scale -= deltaTime;
        }
    }

    @Override
    public void render() {
        this.vao.bind();
        this.shader.setUniform("scale", scale);
        glDrawElements(GL_TRIANGLES, 9, GL_UNSIGNED_INT, 0);
    }

}
