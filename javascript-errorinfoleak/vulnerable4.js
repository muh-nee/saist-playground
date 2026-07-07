const Koa = require('koa');
const Router = require('@koa/router');

const app = new Koa();
const router = new Router();

router.get('/items', async (ctx) => {
  try {
    ctx.body = await fetchItems(ctx.query.filter);
  } catch (err) {
    ctx.status = 500;
    ctx.body = { error: err.toString() };
  }
});

async function fetchItems(filter) {
  return [];
}

app.use(router.routes());
module.exports = app;
