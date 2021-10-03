package com.example.summerproject

// 2021.08.01 khsexk: RDB와 데이터 연동을 위해 클래스 생성
class TableData {
    var userId : String = ""
    var useInfo: Boolean = false

    constructor(userId: String, useInfo: Boolean){
        this.userId = userId
        this.useInfo = useInfo
    }
}