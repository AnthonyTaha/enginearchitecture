package engine.components;

import engine.GameObject;
import engine.Window;

public abstract class Component {
	private String tag = "Def";
	private boolean isEnabled = true;
	public GameObject parent;
	public void init(GameObject parent) {
		this.parent = parent;
		start();
	}
	public void start() {
		
	}
	public void input(Window window) {}
	public void update(float deltaTime) {}
	public void render(Window window) {}
	public void cleanUp() {}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
}
