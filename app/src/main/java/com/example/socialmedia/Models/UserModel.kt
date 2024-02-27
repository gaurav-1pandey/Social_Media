package com.example.socialmedia.Models

data class UserModel(
    var username :String="the user name",
    var email:String="user email",
    var profession:String="user profession",
    var password:String="user pass",
    var cpic:String?=null,
    var userid:String="the user id",
    var ppic:String?=null,
    var bio:String="your bio",
    var nickname:String="your nick name",
    var dob:String="your dob",
    var followerCount:String="0",
    var followingCount:String="0"
):java.io.Serializable


