package com.example.aridagrisolutions
class CropMC
{
    var cropTitle: String ?= null
    var cropDescription: String ?= null
    var cropImage : String ?= null
    var temperature : String ?= null
    var soil : String ?= null
    var variety : String ?= null
    var sowing : String ?= null
    var seedRate : String ?= null
    var fertilizer : String ?= null
    var irrigation : String ?= null
    var harvesting : String ?= null
    var audioLink : String ?= null

    constructor(){}
    constructor(title:String?, description:String?, image:String?,
                temp:String?, soil:String?, variety:String?, sowing:String?, seedRate:String?,
                fertilizer:String?, irrigation:String?, harvesting:String?, audioLink:String?)
    {
        this.cropTitle=title
        this.cropDescription=description
        this.cropImage=image
        this.temperature=temp
        this.soil=soil
        this.variety=variety
        this.sowing=sowing
        this.seedRate=seedRate
        this.fertilizer=fertilizer
        this.irrigation=irrigation
        this.harvesting=harvesting
        this.audioLink=audioLink
    }
}
