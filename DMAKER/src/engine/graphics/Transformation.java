package engine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.GameObject;
import engine.components.Camera;

public class Transformation {

    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    private final Matrix4f modelViewMatrix;
    private final Matrix4f modelMatrix;
    public Transformation() {
    	modelViewMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        return projectionMatrix.setPerspective(fov, width / height, zNear, zFar);
    }

    public Matrix4f getModelViewMatrix(GameObject gameItem, Matrix4f viewMatrix) {
        Vector3f rotation = gameItem.getRotation();
        modelViewMatrix.set(viewMatrix).translate(gameItem.getPosition()).
            rotateX((float)Math.toRadians(-rotation.x)).
            rotateY((float)Math.toRadians(-rotation.y)).
            rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameItem.getScale());
        return modelViewMatrix;
    }
    public Matrix4f getModelMatrix(GameObject gameItem) {
        Vector3f rotation = gameItem.getRotation();
        modelMatrix.translate(gameItem.getPosition()).
            rotateX((float)Math.toRadians(-rotation.x)).
            rotateY((float)Math.toRadians(-rotation.y)).
            rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameItem.getScale());
        return modelViewMatrix;
    }
    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.parent.getPosition();
        Vector3f rotation = camera.parent.getRotation();

        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
            .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }
}
