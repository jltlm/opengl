package shaders;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/resources/vertexShader.shader";
    private static final String FRAGMENT_FILE = "src/main/resources/fragmentShader.shader";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
