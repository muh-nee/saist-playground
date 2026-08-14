const Fastify = require('fastify')
const OpenAI = require('openai')
const { FaissStore } = require('langchain/vectorstores/faiss')

const fastify = Fastify()
const openai = new OpenAI()

fastify.get('/context', async (request, reply) => {
  const vectorStore = await FaissStore.load('policy_index', null)
  const docs = await vectorStore.similaritySearch(request.query.q, 3)
  const policyText = docs.map(d => d.pageContent).join('\n')
  await openai.chat.completions.create({
    model: 'gpt-4o',
    messages: [
      { role: 'system', content: policyText },
      { role: 'user', content: request.query.q },
    ],
  })
  return { policy: policyText }
})

fastify.listen({ port: 3000 })
