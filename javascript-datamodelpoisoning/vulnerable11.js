const ort = require("onnxruntime-node");
const Koa = require("koa");
const Router = require("@koa/router");
const bodyParser = require("koa-bodyparser");

const app = new Koa();
const router = new Router();

router.post("/load", async (ctx) => {
  const modelPath = ctx.request.body.model_path;
  const session = await ort.InferenceSession.create(modelPath);
  ctx.body = { status: "loaded", inputNames: session.inputNames };
});

app.use(bodyParser()).use(router.routes());
app.listen(3000);
