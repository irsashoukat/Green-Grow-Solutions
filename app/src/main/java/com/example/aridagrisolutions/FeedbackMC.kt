package com.example.aridagrisolutions

class FeedbackMC {
    var rating:String?=""
    var message:String?=""
    constructor(){}
    constructor(ratingApp:String, feedbackMessage:String)
    {
        rating=ratingApp
        message=feedbackMessage
    }
}