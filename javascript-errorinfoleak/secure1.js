const express = require('express');
const { Pool } = require('pg');

const app = express();
const pool = new Pool();

app.get('/user/:id', async (req, res) => {
  try {
    const result = await pool.query('SELECT name FROM users WHERE id = $1', [req.params.id]);
    res.json(result.rows[0]);
  } catch (err) {
    console.error('DB query error:', err);
    res.status(500).json({ error: 'internal server error' });
  }
});

module.exports = app;
