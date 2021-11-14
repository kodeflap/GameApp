package com.quizapp.tork.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Withdraw(var userId: String? = null,
                    var emailAdd: String? = null,
                    var reqBy: String? =null,
                    @ServerTimestamp val date: Date? = null)
{
    constructor() : this("", "", "",Date())
}