package Tests;

import java.io.IOException;

import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;
import views.Setting;

import static org.junit.jupiter.api.Assertions.*;

public class BrightnessColorContrastVolumeTest {
    @Test
    void BrightnessTest() throws IOException {
        GridPane p = new GridPane();
        Setting setting = new Setting(p);
        assertEquals(0, setting.brightness);
        setting.increaseBrightness(p);
        assertEquals(0.1, setting.brightness);
        setting.decreaseBrightness(p);
        assertEquals(0, setting.brightness);
        setting.brightness = 0.7;
        setting.increaseBrightness(p);
        assertEquals(0.7, setting.brightness);
        setting.brightness = -0.7;
        setting.decreaseBrightness(p);
        assertEquals(-0.7, setting.brightness);

    }
    @Test
    void ContrastTest() throws IOException {
        GridPane p = new GridPane();
        Setting setting = new Setting(p);
        assertEquals(0, setting.contrast);
        setting.increaseContrast(p);
        assertEquals(0.1, setting.contrast);
        setting.decreaseContrast(p);
        assertEquals(0, setting.contrast);
        setting.contrast = 0.7;
        setting.increaseContrast(p);
        assertEquals(0.7, setting.contrast);
        setting.contrast = -0.7;
        setting.decreaseContrast(p);
        assertEquals(-0.7, setting.contrast);

    }

    @Test
    void VolumeTest() throws IOException {
        GridPane p = new GridPane();
        Setting setting = new Setting(p);
        assertEquals(0.5, setting.volume);


    }

}
