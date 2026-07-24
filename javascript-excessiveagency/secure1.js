const fs = require("fs/promises");
const { OpenAI } = require("openai");

const openai = new OpenAI();

const REPORT_FILES = {
  q1_sales: "/var/app/reports/q1_sales.csv",
  q2_sales: "/var/app/reports/q2_sales.csv",
  q3_sales: "/var/app/reports/q3_sales.csv",
  q4_sales: "/var/app/reports/q4_sales.csv",
};

async function readReport({ reportName }) {
  const filePath = REPORT_FILES[reportName];
  if (!filePath) throw new Error("Unknown report");
  return fs.readFile(filePath, "utf8");
}

async function handleRequest(messages) {
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
