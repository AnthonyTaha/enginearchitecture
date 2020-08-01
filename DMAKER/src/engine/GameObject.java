package engine;

import java.util.HashMap;

import org.joml.Vector3f;

import engine.components.Component;

public class GameObject {

    private Vector3f position;

    private Vector3f scale;

    private Vector3f rotation;
    private String name;
    private HashMap<String, Component> components;
    
	public GameObject(String name) {
    	this.name = name;
    	components = new HashMap<>();
        position = new Vector3f();
        scale = new Vector3f(1,1,1);
        rotation = new Vector3f();
    }
    public void render(Window window) {
    	for(String i : components.keySet()) {
    		components.get(i).render(window);
    	}
    }
    public void input(Window window) {
    	for(String i : components.keySet()) {
    		components.get(i).input(window);
    	}
    }
    public void update(float deltaTime) {
    	for(String i : components.keySet()) {
    		components.get(i).update(deltaTime);
    	}
    }
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

	public void addPosition(float offsetX, float offsetY, float offsetZ) {
		position.x += offsetX;
		position.y += offsetY;
		position.z += offsetZ;
	}
	public void setPosition(Vector3f pos) {
		this.position.x = pos.x;
        this.position.y = pos.y;
        this.position.z = pos.z;
		
	}
    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    public void addRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
    public void cleanUp() {
    	for(String i : components.keySet()) {
    		components.get(i).cleanUp();
    	}
    }
    public String getName() {
		return name;
	}
	public Component GetComponent(String tag) {
		return (Component)components.get(tag);
	}
	public void AddComponent(Component comp) {
		components.put(comp.getTag(), comp);
		comp.init(this);
	}
	public void RemoveComponent(String tag) {
		components.get(tag).cleanUp();
		components.remove(tag);
	}

}
