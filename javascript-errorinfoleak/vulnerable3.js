const fastify = require('fastify')({ logger: true });

fastify.get('/data', async (request, reply) => {
  try {
    const data = await fetchData(request.query.source);
    return data;
  } catch (err) {
    reply.status(500).send({ error: err.message });
  }
});

async function fetchData(source) {
  throw new Error('Database connection failed at db.internal:5432 for source ' + source);
}

module.exports = fastify;
