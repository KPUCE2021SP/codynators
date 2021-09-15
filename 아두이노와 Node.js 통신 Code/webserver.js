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

const tableData = {
    number: `${table}`
};

parser.on('data', function(data){
    console.log(data);
    // const promise = new Promise((resolve, reject)=>{
    //     if(data){
    //         resolve('사람 이용중');
    //     }else{
    //         reject('실패');
    //     }
    // });

    // promise
    //     .then((message) => {
    //         // Firebase로 전송
    //         console.log(message);
    //     })
    //     .catch((error) => {
    //         console.error(error);
    //     })
    //     .finally(() => {
    //         //console.log('promise 작동중');
    //     })
});




app.get('/check', (req,res) => {
    res.send('Hello');
    console.log('Hello response 했음');
});




app.listen(3000);