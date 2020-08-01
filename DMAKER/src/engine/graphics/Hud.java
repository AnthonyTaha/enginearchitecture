package engine.graphics;


import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import org.lwjgl.nanovg.NVGColor;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.system.MemoryUtil.NULL;
import engine.Utils;
import engine.Window;

public class Hud {
	private static final String FONT_NAME = "BOLD";

    private long vg;

    private NVGColor colour;

    private ByteBuffer fontBuffer;

    private DoubleBuffer posx;

    private DoubleBuffer posy;


	public void init(Window window) throws Exception {
	    this.vg = window.isAntialiasing() ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
	    if (this.vg == NULL) {
	        throw new Exception("Could not init nanovg");
	    }

	    fontBuffer = Utils.ioResourceToByteBuffer("/fonts/OpenSans-Bold.ttf", 150 * 1024);
	    int font = nvgCreateFontMem(vg, FONT_NAME, fontBuffer, 0);
	    if (font == -1) {
	        throw new Exception("Could not add font");
	    }
	    colour = NVGColor.create();

	    posx = MemoryUtil.memAllocDouble(1);
	    posy = MemoryUtil.memAllocDouble(1);

	}
	public void render(Window window) {
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);

       /* glfwGetCursorPos(window.getWindow(), posx, posy);
        int xcenter = 50;
        int ycenter = window.getHeight() - 75;
        int radius = 20;
        int x = (int) posx.get(0);
        int y = (int) posy.get(0);
        boolean hover = Math.pow(x - xcenter, 2) + Math.pow(y - ycenter, 2) < Math.pow(radius, 2);*/

        nvgFontSize(vg, 20.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, rgba(255, 255, 255, 255, colour));

        nvgText(vg, 50, 10, "THE RENDERATOR");

        nvgEndFrame(vg);
        
        window.restoreState();
    }

    private NVGColor rgba(int r, int g, int b, int a, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(a / 255.0f);

        return colour;
    }

    public void cleanup() {
        nvgDelete(vg);
        if (posx != null) {
            MemoryUtil.memFree(posx);
        }
        if (posy != null) {
            MemoryUtil.memFree(posy);
        }
    }
}
