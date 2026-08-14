const express = require('express')
const OpenAI = require('openai')

const app = express()
const openai = new OpenAI()

const tools = [
  { type: 'function', function: {
    name: 'lookup_internal_pricing',
    description: 'Returns confidential pricing tiers for enterprise accounts.',
    parameters: { type: 'object', properties: { tier: { type: 'string' } } }
  }}
]

app.post('/chat', async (req, res) => {
  await openai.chat.completions.create({
    model: 'gpt-4o',
    messages: [{ role: 'user', content: req.body.message }],
    tools,
  })
  res.json({ reply: 'ok' })
})

app.get('/debug/tools', (req, res) => {
  res.json({ tools })
})

app.listen(3000)
