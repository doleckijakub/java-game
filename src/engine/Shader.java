package engine;

import java.io.IOException;
import java.nio.file.*;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements AutoCloseable {

    public final int id;

    public Shader(String vertFilename, String fragFilename) {
        try {
            String vertCode = Files.readString(Path.of(vertFilename));
            String fragCode = Files.readString(Path.of(fragFilename));

            int vertShader = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(vertShader, vertCode);
            glCompileShader(vertShader);
            if (glGetShaderi(vertShader, GL_COMPILE_STATUS) != GL_TRUE) {
                throw new RuntimeException(glGetShaderInfoLog(vertShader));
            }

            int fragShader = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(fragShader, fragCode);
            glCompileShader(fragShader);
            if (glGetShaderi(fragShader, GL_COMPILE_STATUS) != GL_TRUE) {
                throw new RuntimeException(glGetShaderInfoLog(fragShader));
            }

            this.id = glCreateProgram();
            glAttachShader(this.id, vertShader);
            glAttachShader(this.id, fragShader);

            glLinkProgram(this.id);

            glDeleteShader(vertShader);
            glDeleteShader(fragShader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        glDeleteProgram(this.id);
    }

    public void bind() {
        glUseProgram(this.id);
    }
}
