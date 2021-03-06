'use strict';

const Table = require('../models/Table');
const admin = require('firebase-admin');

// 수정 부분
var SerialPort = require('serialport');

// Firestore 등록
var serviceAccount = require("../path/kpu-summerproject-firebase-adminsdk-1gekz-81fd6cf343.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
const db = admin.firestore();

// 아두이노 데이터 받을 준비


// 아두이노에서 압력 데이터 받아서 Firestore로 넘기기
const addArduinoData = async (req, res, next) => {
    try {
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

        const data = parser.on('pulseData', function(pulseData){
            console.log(pulseData);
            req.body = pulseData;
            console.log(req.body);
            data = req.body;
            console.log(data);
        });
        await db.collection('Table_Use_Information').doc('Table_1').set(data);
        res.send(data);
        console.log(data);
    } catch(error) {
        res.status(400).send(error.message);
        console.log("Failed with error: " + error);
    }
}

// 기존 구현
const addTable = async (req, res, next) => {
    try {
        const data = req.body;
        await db.collection('Table_Use_Information').doc().set(data);
        res.send('Record saved successfuly');
        console.log('Record saved successfuly');
    } catch(error) {
        res.status(400).send(error.message);
        console.log("Failed with error: " + error);
    }
}

const getAllTableInfo = async (req, res, next) => {
    try {
        const tableInfo = await db.collection('Table_Use_Information');
        const data = await tableInfo.get();
        const tableInfoArray = [];
        if(data.empty) {
            res.status(404).send('No Table record found');
        }else {
            data.forEach(doc => {
                const table = new Table(
                    doc.id,
                    doc.data().firstName,
                    doc.data().lastName,
                    doc.data().fatherName,
                    doc.data().class,
                    doc.data().age,
                    doc.data().phoneNumber,
                    doc.data().subject,
                    doc.data().year,
                    doc.data().semester,
                    doc.data().status
                );
                tableInfoArray.push(table);
            });
            res.send(tableInfoArray);
        }
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const getTable = async (req, res, next) => {
    try {
        const id = req.params.id;
        const table = await db.collection('Table_Use_Information').doc(id);
        const data = await table.get();
        if(!data.exists) {
            res.status(404).send('Table with the given ID not found');
        }else {
            res.send(data.data());
        }
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const updateTable = async (req, res, next) => {
    try {
        const id = req.params.id;
        const data = req.body;
        const table =  await db.collection('Table_Use_Information').doc(id);
        await table.update(data);
        res.send('Table record updated successfuly');        
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteTable = async (req, res, next) => {
    try {
        const id = req.params.id;
        await db.collection('Table_Use_Information').doc(id).delete();
        res.send('Record deleted successfuly');
    } catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    addArduinoData,
    addTable,
    getAllTableInfo,
    getTable,
    updateTable,
    deleteTable
}