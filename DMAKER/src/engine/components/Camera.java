package engine.components;

import engine.Scene;
import engine.Window;

public class Camera extends Component {
	public float FOV = (float) Math.toRadians(60.0f);
	public boolean isMainCamera = true;
	public Camera() {
		setTag("Camera");
		if(isMainCamera && Scene.MainCamera != this) {
			Scene.MainCamera = this;
		}
	}
	public void input(Window window) {
	}
	public void update(float deltaTime) {
		if(isMainCamera && Scene.MainCamera != this) {
			Scene.MainCamera = this;
		}
	}
    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            parent.addPosition((float)Math.sin(Math.toRadians(parent.getRotation().y)) * -1.0f * offsetZ, 0, (float)Math.cos(Math.toRadians(parent.getRotation().y)) * offsetZ);
        }
        if ( offsetX != 0) {
        	parent.addPosition((float)Math.sin(Math.toRadians(parent.getRotation().y - 90)) * -1.0f * offsetX, 0, (float)Math.cos(Math.toRadians(parent.getRotation().y - 90)) * offsetX);
        }
        parent.addPosition(0, offsetY, 0);
    }
}
