package com.example.aridagrisolutions
class FaqMC{
    var question: String ?= null
    var answer: String ?= null
    var visibile:Boolean=false
    constructor(){}
    constructor(ques:String?, ans:String?)
    {
        this.question=ques
        this.answer=ans
    }
}