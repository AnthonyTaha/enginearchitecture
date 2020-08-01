package engine;

import java.util.HashMap;

import engine.components.Camera;
import engine.graphics.Hud;
import engine.graphics.Renderer;

public abstract class Scene {
    protected Renderer renderer;
    public static HashMap<String, GameObject> objects;
    public static Camera MainCamera;
    public Hud hud;
	public void start(Window window) throws Exception{
		renderer = new Renderer();
		hud = new Hud();
        renderer.init(window);
        hud.init(window);
        objects = new HashMap<>();
        init(window);
	}
	public void init(Window window) throws Exception{}
	public void input(Window window) {
    	for (String i : objects.keySet()) {
    		objects.get(i).input(window);
		}
	}
	public void update(float deltaTime) {
    	for (String i : objects.keySet()) {
    		objects.get(i).update(deltaTime);
		}
	}
	public void render(Window window) throws Exception {
		if(MainCamera == null)
			return;
		
    	renderer.render(window,MainCamera, objects);
    	hud.render(window);
	}
	public void cleanup() {
		renderer.cleanup();
		objects.clear();
	}
	public void AddGameOject(GameObject obj) {
		objects.put(obj.getName(), obj);
	}
	public void RemoveGameOject(String name) {
		objects.remove(name);
	}
}
