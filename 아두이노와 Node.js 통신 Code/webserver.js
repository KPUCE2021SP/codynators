var SerialPort = require('serialport');
<<<<<<< HEAD
var fs = require('fs');
var app = express();
=======
>>>>>>> b2b7a32a856a60043aac1860713d78d1561bc7d1

const parsers = SerialPort.parsers;
const parser = new parsers.Readline(
    {
        delimiter: '\r\n' // 한 줄 씩 읽어오기 위해서
    }
);

<<<<<<< HEAD

var port = new SerialPort('COM3',{ // 사용하는 아두이노 Serial Port COM3 -> 아두이노에서 모니터 꺼야 노드에서 로그 출력됨
    baudRate: 9600, // 9600bps
=======
var port = new SerialPort('COM10',{
    baudRate: 9600,
>>>>>>> b2b7a32a856a60043aac1860713d78d1561bc7d1
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