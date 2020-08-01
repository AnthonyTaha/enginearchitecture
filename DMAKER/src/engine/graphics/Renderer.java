package engine.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.GameObject;
import engine.Utils;
import engine.Window;
import engine.components.Camera;
import engine.components.DirectionalLight;
import engine.components.Mesh;
import engine.components.PointLight;

public class Renderer {

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final Transformation transformation;
    private DirectionalLight mainLight;
    private ShaderProgram shaderProgram;
    private List<PointLight> pointLights;
    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        // Create shader
        shaderProgram = new ShaderProgram();
        pointLights = new ArrayList<>();
        shaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shaderProgram.link();
        
        // Create uniforms for world and projection matrices and texture
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");
        shaderProgram.createUniform("textureSampler");
        shaderProgram.createMaterialUniform("material");
        // Create lighting related uniforms
        shaderProgram.createUniform("specularPower");
        shaderProgram.createUniform("ambientLight");
        
        shaderProgram.createDirectionalLightUniform("directionalLight");
       // shaderProgram.createPointLightListUniform("pointLights",1);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void render(Window window,Camera camera, HashMap<String,GameObject> gameItems) throws Exception {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();
        
        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(camera.FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.SetUniform("projectionMatrix", projectionMatrix);
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        
        shaderProgram.setUniform("ambientLight", new Vector3f(0.5f,0.5f,0.5f));
        shaderProgram.setUniform("specularPower", 10f);
        if(mainLight != null) {
	        DirectionalLight currDirLight = new DirectionalLight(mainLight);
	        currDirLight.parent=mainLight.parent;
	        Vector4f dir = new Vector4f(currDirLight.parent.getPosition(), 0);
	        dir.mul(viewMatrix);
	        currDirLight.parent.setPosition(new Vector3f(dir.x, dir.y, dir.z));
	        shaderProgram.setUniform("directionalLight", currDirLight);
	        
        }

        shaderProgram.setUniform("textureSampler", 0);
        if(pointLights.size() != 0) {
        	try {
				shaderProgram.setUniform("pointLights", pointLights);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        // Render each gameItem
        for (String i : gameItems.keySet()) {
            // Set world matrix for this item
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(
                    gameItems.get(i),viewMatrix);
            shaderProgram.SetUniform("modelViewMatrix", modelViewMatrix);
            
            // Render the mesh for this game item
            if(gameItems.get(i).GetComponent("Mesh")!= null) {
            	Mesh mesh = (Mesh)gameItems.get(i).GetComponent("Mesh");
            	shaderProgram.setUniform("material", mesh.getMaterial());
            	
            }
            if(gameItems.get(i).GetComponent("DirectionalLight")!= null) {
            	DirectionalLight tempLight = (DirectionalLight)gameItems.get(i).GetComponent("DirectionalLight");
            	mainLight = tempLight;
            }
            if(gameItems.get(i).GetComponent("PointLight")!= null) {
            	PointLight tempLight = (PointLight)gameItems.get(i).GetComponent("PointLight");
            	//shaderProgram.setUniform("material", mesh.getMaterial());
            	if(!pointLights.contains(tempLight)) {
    			    Vector3f lightPos = tempLight.parent.getPosition();
    			    Vector4f aux = new Vector4f(lightPos, 1);
    			    aux.mul(viewMatrix);
    			    lightPos.x = aux.x;
    			    lightPos.y = aux.y;
    			    lightPos.z = aux.z;
				    pointLights.add(tempLight);
				    System.out.println(pointLights);
			        shaderProgram.createPointLightListUniform("pointLights",pointLights.size());
            	}
            } 
            
            gameItems.get(i).render(window);
        }
        
        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanUp();
        }
    }
}
