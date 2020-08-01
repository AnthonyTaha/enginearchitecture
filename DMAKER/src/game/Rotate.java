package game;

import org.lwjgl.glfw.GLFW;

import engine.Input;
import engine.Window;
import engine.components.Component;

public class Rotate extends Component{
	float direction = 1;
	public Rotate() {
		setTag("Rotate");
	}
	public void input(Window window) {
		if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			direction = direction * -1;
			System.out.println("ETST");
		}
	}
	public void update(float deltaTime) {
		parent.addRotation(0,  direction*6 * .1f, 0);
	}
}
