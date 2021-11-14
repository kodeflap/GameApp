package com.quizapp.tork.model

data class Category(var cat_id: String? = null,
                    var cat_title: String? = null,
                    var image: String? = null)
{
    constructor() : this("", "", "")
}
