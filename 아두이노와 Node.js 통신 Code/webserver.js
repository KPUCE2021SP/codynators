var SerialPort = require('serialport');

const parsers = SerialPort.parsers;
const parser = new parsers.Readline(
    {
        delimiter: '\r\n'
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


parser.on('data', function(data){
    console.log(data);
});