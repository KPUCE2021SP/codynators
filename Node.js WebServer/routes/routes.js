const express = require('express');
const {
    addArduinoData,
    addTable, 
    getAllTableInfo, 
    getTable,
    updateTable,
    deleteTable
    } = require('../controllers/dataController');

const router = express.Router();


router.post('/table', addArduinoData);
router.post('/table', addTable);
router.get('/Table_Use_Information', getAllTableInfo);
router.get('/table/:id', getTable);
router.put('/table/:id', updateTable);
router.delete('/table/:id', deleteTable);



module.exports = {
    routes: router
}