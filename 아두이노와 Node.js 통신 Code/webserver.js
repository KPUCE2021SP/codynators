var express = require('express');
var http = require('http');
var path = require('path');
var bodyParaser = require('body-parser');
var SerialPort = require('serialport');
var app = express();

const parsers = SerialPort.parsers;
const parser = new parsers.Readline(
    {
        delimiter: '\r\n'
    }
);

var port = new SerialPort('COM3',{
    baudRate: 9600,
    dataBits: 8,
    parity: 'none',
    stopBits: 1,
    flowControl: false
});

port.pipe(parser);


parser.on('data', function(data){
    console.log(data);
});