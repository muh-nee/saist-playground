package main;

import org.tensorflow.SavedModelBundle;

public class ModelLoader {
    private static final String MODEL_DIR = "./models/classifier";

    public SavedModelBundle load() {
        return SavedModelBundle.load(MODEL_DIR, "serve");
    }
}
