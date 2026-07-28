import fs from "fs/promises";
import OpenAI from "openai";

const openai = new OpenAI();

const REPORT_FILES = {
  q1_sales: "/var/app/reports/q1_sales.csv",
  q2_sales: "/var/app/reports/q2_sales.csv",
  q3_sales: "/var/app/reports/q3_sales.csv",
  q4_sales: "/var/app/reports/q4_sales.csv",
} as const;

type ReportName = keyof typeof REPORT_FILES;

async function readReport({ reportName }: { reportName: ReportName }): Promise<string> {
  const filePath = REPORT_FILES[reportName];
  return fs.readFile(filePath, "utf8");
}

async function handleRequest(messages: OpenAI.ChatCompletionMessageParam[]) {
  const response = await openai.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 2048,
    messages,
    tools: [
      {
        type: "function",
        function: {
          name: "readReport",
          description: "Read a quarterly sales report",
          parameters: {
            type: "object",
            properties: {
              reportName: {
                type: "string",
                enum: ["q1_sales", "q2_sales", "q3_sales", "q4_sales"],
              },
            },
            required: ["reportName"],
          },
        },
      },
    ],
  });
  return response;
}
