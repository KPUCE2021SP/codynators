'use strict';

const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const config = require('./config');
const routes = require('./routes/routes');

var SerialPort = require('serialport');

const parsers = SerialPort.parsers;
const parser = new parsers.Readline(
        {
            delimiters: '\r\n'
        }
);

var port = new SerialPort('COM10',{
    baudRate: 9600,
    dataBits: 8,
    parity: 'none',
    stopBits: 1,
    flowControl: false
});

port.pipe(parser);

parser.on('pulseData', function(pulseData){
    console.log(pulseData);
});

const app = express();

app.use(express.json());
app.use(cors());
app.use(bodyParser.json());

app.use('/api', routes.routes);

//app.listen(config.port, () => console.log('App is listening on url http://localhost:' + config.port));