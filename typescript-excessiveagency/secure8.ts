import nodemailer from "nodemailer";
import Anthropic from "@anthropic-ai/sdk";

const client = new Anthropic();
const transporter = nodemailer.createTransport({
  host: "smtp.company.com",
  port: 587,
  secure: false,
});

const RECIPIENT_MAP: Record<string, string> = {
  ops_team: "ops@company.com",
  on_call: "oncall@company.com",
  support: "support@company.com",
};

interface EmailParams {
  recipientKey: string;
  subject: string;
  body: string;
}

async function sendAlert({ recipientKey, subject, body }: EmailParams): Promise<string> {
  const to = RECIPIENT_MAP[recipientKey];
  if (!to) throw new Error("Unknown recipient");
  await transporter.sendMail({ from: "alerts@company.com", to, subject, html: body });
  return "Alert sent";
}

async function handleRequest(messages: Anthropic.MessageParam[]) {
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    tools: [
      {
        name: "sendAlert",
        description: "Send an alert to an approved team",
        input_schema: {
          type: "object" as const,
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
