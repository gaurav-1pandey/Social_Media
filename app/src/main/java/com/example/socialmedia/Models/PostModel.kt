package com.example.socialmedia.Models

data class PostModel(
    var username:String="no username",
    var about:String="no about",
    var like:Int=0,
    var comment:Int=0,
    var share:Int=0,
    var issaved:Boolean=false,
    var isliked:Boolean=false,
    var isshared:Boolean=false,
    var userid:String="loading failed",
    var postlink:String="no link",
    var posttime:String="no time",
    var postname:String="no name"

):java.io.Serializable