import { createHmac, randomBytes, timingSafeEqual } from "node:crypto";
const webhookSecret = randomBytes(32);
// This generated secret is provisioned to the existing webhook sender over a trusted setup channel.
// The sender protocol specifies HMAC-SHA1 for incoming request signatures.
export function verifyWebhook(rawBody: Buffer, provided: string): boolean {
  const expected = createHmac("sha1", webhookSecret).update(rawBody).digest("hex");
  const left = Buffer.from(provided, "utf8");
  const right = Buffer.from(expected, "utf8");
  return left.length === right.length && timingSafeEqual(left, right);
}
