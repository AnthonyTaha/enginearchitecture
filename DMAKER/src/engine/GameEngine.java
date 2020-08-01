package engine;

public class GameEngine implements Runnable {
	
    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;
    
	private final Thread gameThread;
	private Window window;
	private Scene game;
	private Timer timer;
	public static float FPS = 0;
	public GameEngine(String title, int width, int height, boolean vsync, Scene startingScene, boolean antialiasing) {
		gameThread = new Thread(this, "Engine");
		window = new Window(title, width, height, vsync, antialiasing);
		this.game = startingScene;
		timer = new Timer();
	}
	public void start() {
	    String osName = System.getProperty("os.name");
	    if ( osName.contains("Mac") ) {
	        gameThread.run();
	    } else {
	    	gameThread.start();
	    }
	}
	public void run() {
		try {
			init();
			gameLoop();
		}catch(Exception exc) {
			exc.printStackTrace();
		}finally {
			cleanup();
		}
	}
	public void init() throws Exception{
		window.init();
		timer.init();
		game.start(window);
	}
	public void gameLoop() throws Exception {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;
		boolean running = true;
		while(running && !window.isCloseRequested()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;
			input();
			if(accumulator >= interval) {
				update(interval);
				
				accumulator -= interval;
			}
			render();
			if(!window.isVsync()) {
				sync();
			}
		}
	}
	private void sync() {
		float loopSlot = 1f /TARGET_FPS;
		double endTime = timer.getLastLoopTime() + loopSlot;
		while(timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			}catch(InterruptedException ie) {}
		}
	}
	protected void input() {
		game.input(window);
	}
	protected void update(float deltaTime) {
		game.update(deltaTime);
	}
	protected void render() throws Exception {
		game.render(window);
		window.update();
	}
	protected void cleanup() {
		game.cleanup();
	}
	
}
