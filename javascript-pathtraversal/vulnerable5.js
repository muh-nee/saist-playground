const fs = require("fs");
const path = require("path");

function readReport(req, res) {
  const reportPath = path.join("/srv/reports", req.query.file);
  fs.readFile(reportPath, "utf8", (error, data) => {
    if (error) return res.status(404).send("not found");
    return res.type("text/plain").send(data);
  });
}

module.exports = { readReport };
