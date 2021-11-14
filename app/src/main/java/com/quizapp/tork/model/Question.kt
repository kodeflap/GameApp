package com.quizapp.tork.model

data class Question(var ques :String? = null,
                    var op1 : String? = null,
                    var op2 : String? = null,
                    var op3 : String? = null,
                    var op4 : String? = null,
                    var ans : String? = null)
{
    constructor() : this("","","","","","")
}
