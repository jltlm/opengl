package engineTester;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import javax.xml.soap.Text;
import java.io.File;
import java.time.temporal.Temporal;

public class MainGameLoop {

    public static void main(String[] args) {

        setupLWJGLNative();

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);


        //RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        RawModel model = OBJLoader.loadObjModel("Stall Model and Texture/stall", loader);
//        ModelTexture texture = new ModelTexture(loader.loadTexture("kirishima_low_res"));
        ModelTexture texture = new ModelTexture(loader.loadTexture("Stall Model and Texture/stallTexture"));
        TexturedModel staticModel = new TexturedModel(model, texture);

        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);

        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
//            entity.increasePosition(0, 0, 0);
            entity.increaseRotation(0, 1, 0);
            camera.move();
            //game logic
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
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
