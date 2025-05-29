package com.example.aridagrisolutions
class ExpertMC
{
    var name:String?=null
    var imageLink:String?=null
    var email:String ?=null
    var phNo:String?=null
    var password:String ?=null
    var authId:String ?=null
    var recordId:String ?=null
    constructor(){}
    constructor(name:String?, imagelink:String?, email:String?, phno:String?, pass:String?, auth:String?, record:String?)
    {
        this.name=name
        this.imageLink=imagelink
        this.email=email
        this.phNo=phno
        this.password=pass
        this.authId=auth
        this.recordId=record

    }
}



