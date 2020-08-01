package game;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.GameObject;
import engine.Scene;
import engine.Window;
import engine.components.Camera;
import engine.components.DirectionalLight;
import engine.components.Mesh;
import engine.components.PointLight;
import engine.graphics.Material;
import engine.graphics.OBJLoader;
import engine.graphics.Texture;

public class EngineTest extends Scene{
    
    @Override
    public void init(Window window) throws Exception {
    	
    	Mesh mesh = OBJLoader.loadMesh("/scene.obj");
        Material mat = new Material(new Vector4f(.5f,.5f,.5f,1), 1.0f);
        
        mat.setTexture(new Texture("resources/grassblock.png"));
        Material mat2 = new Material(new Vector4f(.5f,.5f,.5f,1f), 1.0f);
        mesh.setMaterial(mat2);
        GameObject obj = new GameObject("Test");
        obj.AddComponent(mesh);
       // AddGameOject(obj); 
        obj.setPosition(0, -5, 0);
        obj.setScale(new Vector3f(1f));
        GameObject obj2 = new GameObject("Test2");
        Mesh mesh2 = OBJLoader.loadMesh("/cube.obj");
        mesh2.setMaterial(mat);
        obj2.AddComponent(mesh2);
        AddGameOject(obj2); 
        obj2.setPosition(1, -1, -4);
        obj2.setScale(new Vector3f(.5f));
        obj2.AddComponent(new Rotate());
        GameObject dirLight = new GameObject("Light");
        dirLight.AddComponent(new DirectionalLight(new Vector3f(1f,1f,1f), new Vector3f(1,1,1), .5f));
        AddGameOject(dirLight);
        GameObject player = new GameObject("Player");
        player.AddComponent(new Camera());
        AddGameOject(player);

        Vector3f lightColour = new Vector3f(255, 204, 153);
        Vector3f lightPosition = new Vector3f(0, -1, -2);
        float lightIntensity = .04f;
        PointLight light;
        light = new PointLight(lightColour, lightIntensity);
        GameObject pointLight = new GameObject("PointLight");
        pointLight.setPosition(lightPosition);
        pointLight.AddComponent(light);
        AddGameOject(pointLight);
        
        PointLight light1;
        Vector3f lightColour1 = new Vector3f(0, 0, 204);
        Vector3f lightPosition1 = new Vector3f(0, -1, -3);
        float lightIntensity1 = .004f;
        light1 = new PointLight(lightColour1, lightIntensity1);
        GameObject pointLight2 = new GameObject("PointLight1");
        pointLight.setPosition(lightPosition1);
        pointLight.AddComponent(light1);
        System.out.println(light1);
        System.out.println(light);
        AddGameOject(pointLight2);
        
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        light.setAttenuation(att);
        light1.setAttenuation(att);
        
    }


}
