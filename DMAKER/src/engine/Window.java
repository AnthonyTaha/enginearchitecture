package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private final String title;
	private int width;
	private int height;
	private long window;
	private boolean resized;
	private boolean vsync;
	private boolean antialiasing;
	public Input input;
	public Window(String title, int width, int height, boolean vsync,boolean antialiasing) {
		super();
		this.title = title;
		this.width = width;
		this.height = height;
		this.resized = false;
		this.vsync = vsync;
		this.setAntialiasing(antialiasing);
		
	}
	public void init() {
		GLFWErrorCallback.createPrint(System.err);
		
		if(!GLFW.glfwInit())
			throw new IllegalStateException("Unavble to initialize GLFW");
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR,3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		if(isAntialiasing())
			GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
		input = new Input();
		window = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup resize callback
		GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });

		

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        // Center our window
        GLFW.glfwSetWindowPos(
                window,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);

        if (isVsync()) {
            // Enable v-sync
        	GLFW.glfwSwapInterval(1);
        }
        createCallbacks();
        // Make the window visible
        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        // Set the clear color
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

	}
	private void createCallbacks() {
		GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
		GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
		GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
	}
	
	public void update() {
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}
    public void restoreState() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

    }
	public void setClearColor(float r, float g, float b, float alpha) {
		GL11.glClearColor(r, g, b, alpha);
    }


    public boolean isCloseRequested() {
        return GLFW.glfwWindowShouldClose(window);
    }
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public long getWindow() {
		return window;
	}
	public void setWindow(long window) {
		this.window = window;
	}
	public boolean isResized() {
		return resized;
	}
	public void setResized(boolean resized) {
		this.resized = resized;
	}
	public boolean isVsync() {
		return vsync;
	}
	public void setVsync(boolean vsync) {
		this.vsync = vsync;
	}
	public String getTitle() {
		return title;
	}
	public boolean isAntialiasing() {
		return antialiasing;
	}
	public void setAntialiasing(boolean antialiasing) {
		this.antialiasing = antialiasing;
	}
	
}
