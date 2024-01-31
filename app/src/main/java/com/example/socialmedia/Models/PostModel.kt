package com.example.socialmedia.Models

data class PostModel(
    var username:String,
    var about:String,
    var profile:Int,
    var post:Int,
    var like:Int,
    var comment:Int,
    var share:Int,
    var issaved:Boolean=false,
    var isliked:Boolean=false,
    var isshared:Boolean=false
)