'use strict';

const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const config = require('./config');
//const routes = require('./routes/routes');

const app = express();

app.use(express.json());
app.use(cors());
app.use(bodyParser.json());

//app.use('/api', routes.routes);

app.listen(config.port, () => console.log('App is listening on url http://localhost:' + config.port));

var SerialPort = require('serialport');

// Firestore 등록
const admin = require('firebase-admin');

var serviceAccount = require("./path/kpu-summerproject-firebase-adminsdk-1gekz-81fd6cf343.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
const db = admin.firestore();


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

const tableData = {
    userId: '',
    useInfo: false
};

parser.on('data', function(data){
    console.log(data);
    const promise = new Promise((resolve, reject)=>{
        if(data.startsWith('T')){
            resolve('자리비움');
        }else{
            reject('이용중');
        }
    });

    promise
        .then((message) => {
            try {
                const table = db.collection('Table_Use_Information').doc(data.substring(0, data.length-1));
                table.update(tableData);
                console.log("Success");        
            } catch (error) {
                console.log("Fail");
            }
        })
        .catch((error) => {
            console.error(`${data}: 자리 이용중`);
        })
        .finally(() => {
            //console.log('promise 작동중');
        })
});


