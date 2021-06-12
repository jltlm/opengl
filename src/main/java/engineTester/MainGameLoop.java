package engineTester;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

import java.io.File;

public class MainGameLoop {

    public static void main(String[] args) {

        setupLWJGLNative();

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0.f,
                0.5f, 0.5f, 0f,
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2

        };

        RawModel model = loader.loadToVAO(vertices, indices);

        while (!Display.isCloseRequested()) {
            //game logic
            renderer.prepare();
            shader.start();
            renderer.render(model);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static void setupLWJGLNative() {
        File JGLLib;

        switch (LWJGLUtil.getPlatform()) {
            case LWJGLUtil.PLATFORM_WINDOWS: {
                JGLLib = new File("./native/windows/");
            }
            break;

            case LWJGLUtil.PLATFORM_LINUX: {
                JGLLib = new File("./native/linux/");
            }
            break;

            default:

            case LWJGLUtil.PLATFORM_MACOSX: {
                JGLLib = new File("./native/macosx/");
            }
            break;
        }
// https://stackoverflow.com/a/30347873/6622395
        System.setProperty("org.lwjgl.librarypath", JGLLib.getAbsolutePath());

    }
}
