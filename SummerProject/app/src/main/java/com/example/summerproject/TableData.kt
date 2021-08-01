package com.example.summerproject

// 2021.08.01 khsexk: RDB와 데이터 연동을 위해 클래스 생성
class TableData {
    var Userid : String = ""
    var useInfo: Int = 0

    constructor(Userid: String, useInfo: Int){
        this.Userid = Userid
        this.useInfo = useInfo
    }
}