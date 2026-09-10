const serialize = require("node-serialize");

function restoreSession(req) {
  return serialize.unserialize(req.body.session);
}
