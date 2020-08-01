package game;

import engine.GameEngine;
import engine.Scene;

public class Main {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            Scene gameLogic = new EngineTest();
            GameEngine gameEng = new GameEngine("GAME", 1024, 768, vSync, gameLogic, true);
            gameEng.run();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
