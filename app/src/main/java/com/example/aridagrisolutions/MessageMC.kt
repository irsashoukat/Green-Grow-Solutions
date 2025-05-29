package com.example.aridagrisolutions
class MessageMC {
    var message:String?=null
    var senderId:String?=null
    var receiverId:String?=null
    var timeStamp:Long?=null
    constructor(){}
    constructor(message:String?, senderId:String?, receiverId:String?, timeStamp:Long?){
        this.message=message
        this.senderId=senderId
        this.receiverId=receiverId
        this.timeStamp=timeStamp
    }
}