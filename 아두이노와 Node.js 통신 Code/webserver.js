var express = require('express');
var http = require('http');
var path = require('path');
var bodyParaser = require('body-parser');
var SerialPort = require('serialport');
var fs = require('fs');
var app = express();

const parsers = SerialPort.parsers;
const parser = new parsers.Readline(
    {
        delimiter: '\r\n' // 한 줄 씩 읽어오기 위해서
    }
);


var port = new SerialPort('COM3',{ // 사용하는 아두이노 Serial Port COM3 -> 아두이노에서 모니터 꺼야 노드에서 로그 출력됨
    baudRate: 9600, // 9600bps
    dataBits: 8,
    parity: 'none',
    stopBits: 1,
    flowControl: false
});

port.pipe(parser); // parser 연결



var table = 0;
parser.on('data', function(data){
    table = data;
    console.log(table);
});

const tableData = {
    number: `${table}`
};

app.get('/', (req,res) => {
    tableData.number = `${table}`
    res.send(JSON.stringify(tableData))
});




app.listen(3000);