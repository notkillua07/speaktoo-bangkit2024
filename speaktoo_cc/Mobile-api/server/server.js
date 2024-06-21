const express = require('express')
const app = express()
const port = 3000
const router = require('./router')
const bodyParser = require('body-parser')
require('dotenv').config()

app.use(bodyParser.json())
app.use(router)

app.listen(port, () => {
  console.log(`listening on port ${port}`)
})

