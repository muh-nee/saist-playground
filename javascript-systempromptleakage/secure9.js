const express = require('express')
const OpenAI = require('openai')
const { FaissStore } = require('langchain/vectorstores/faiss')

const app = express()
const openai = new OpenAI()

const tools = [{ type: 'function', function: { name: 'get_data', description: 'Internal.', parameters: {} } }]

app.post('/chat', async (req, res) => {
  const vectorStore = await FaissStore.load('policy_index', null)
  const docs = await vectorStore.similaritySearch(req.body.message, 3)
  const policyText = docs.map(d => d.pageContent).join('\n')
  const response = await openai.chat.completions.create({
    model: 'gpt-4o',
    messages: [
      { role: 'system', content: policyText },
      { role: 'user', content: req.body.message },
    ],
    tools,
  })
  res.json({ reply: response.choices[0].message.content })
})

app.listen(3000)
