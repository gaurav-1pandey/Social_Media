package com.example.socialmedia.Models

data class UserModel(
    var username :String="the user name",
    var email:String="user email",
    var profession:String="user profession",
    var password:String="user pass",
    var cpic:String?=null,
    var ppic:String?=null,
    var bio:String="your bio",
    var nickname:String="your nick name",
    var dob:String="your dob"
):java.io.Serializable


