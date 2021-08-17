package com.example.summerproject

//21.08.18 eemdeeks : RDB에 저장하는 chat model
class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String,
                  val timestamp:Long){
    constructor() : this("","","","",-1)

}