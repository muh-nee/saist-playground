import * as ort from "onnxruntime-node";
import * as crypto from "crypto";

const EXPECTED_HASH = "a1b2c3d4e5f67890abcdef1234567890a1b2c3d4e5f67890abcdef1234567890";
const TRUSTED_URL = "https://internal.example.com/models/classifier.onnx";

async function loadVerifiedModel(): Promise<ort.InferenceSession> {
  const response = await fetch(TRUSTED_URL);
  const buffer = Buffer.from(await response.arrayBuffer());
  const hash = crypto.createHash("sha256").update(buffer).digest("hex");
  if (hash !== EXPECTED_HASH) {
    throw new Error("integrity check failed");
  }
  return ort.InferenceSession.create(buffer);
}

loadVerifiedModel();
