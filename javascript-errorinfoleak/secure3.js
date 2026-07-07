const fastify = require('fastify')({ logger: true });

fastify.get('/data', async (request, reply) => {
  try {
    const data = await fetchData(request.query.source);
    return data;
  } catch (err) {
    fastify.log.error(err);
    reply.status(500).send({ error: 'internal server error' });
  }
});

async function fetchData(source) {
  return { source };
}

module.exports = fastify;
