package com.example.aridagrisolutions

class FertilizerMC {

    var fertTitle: String ?= null
    var fertDescription: String ?= null
    var fertUsage: String ?= null
    var fertImage: String ?= null
    constructor(){}
    constructor(title:String?, description:String?, cultivation:String?, image:String?)
    {
        this.fertTitle=title
        this.fertDescription=description
        this.fertUsage=cultivation
        this.fertImage=image
    }

}