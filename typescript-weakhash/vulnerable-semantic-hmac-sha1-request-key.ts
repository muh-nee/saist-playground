import { createHmac, timingSafeEqual } from "node:crypto";
export function receiveWebhook(req, res) {
  const key = req.headers["x-signing-key"];
  const expected = createHmac("sha1", key).update(req.body).digest("hex");
  const left = Buffer.from(req.headers["x-signature"]);
  const right = Buffer.from(expected);
  if (left.length !== right.length || !timingSafeEqual(left, right)) return res.sendStatus(403);
  return res.sendStatus(200);
}
