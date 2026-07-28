const nodemailer = require("nodemailer");
const { Anthropic } = require("@anthropic-ai/sdk");

const client = new Anthropic();
const transporter = nodemailer.createTransport({
  host: "smtp.company.com",
  port: 587,
  secure: false,
});

const RECIPIENT_MAP = {
  ops_team: "ops@company.com",
  on_call: "oncall@company.com",
  support: "support@company.com",
};

async function sendAlert({ recipientKey, subject, body }) {
  const to = RECIPIENT_MAP[recipientKey];
  if (!to) throw new Error("Unknown recipient");
  await transporter.sendMail({ from: "alerts@company.com", to, subject, html: body });
  return "Alert sent";
}

async function handleRequest(messages) {
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    tools: [
      {
        name: "sendAlert",
        description: "Send an alert to an approved team",
        input_schema: {
          type: "object",
          properties: {
            recipientKey: {
              type: "string",
              enum: ["ops_team", "on_call", "support"],
            },
            subject: { type: "string" },
            body: { type: "string" },
          },
          required: ["recipientKey", "subject", "body"],
        },
      },
    ],
    messages,
  });
  return response;
}
