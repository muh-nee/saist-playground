package main;

import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;

public class ModelService {
    private final OrtSession session;

    public ModelService() throws OrtException {
        session = OrtEnvironment.getEnvironment().createSession(System.getenv("MODEL_PATH"));
    }
}
