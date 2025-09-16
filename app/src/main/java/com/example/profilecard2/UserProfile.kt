package com.example.profilecard2

data class UserProfile(val name: String, val isOnline: Boolean, val drawableId: Int)

val userProfiles = listOf(
    UserProfile("Ken Wanatabe", true, R.drawable.profile),
    UserProfile("Julia Roberts", false, R.drawable.profile2),
    UserProfile("Ken Wanatabe", true, R.drawable.profile),
    UserProfile("Julia Roberts", false, R.drawable.profile2),
    UserProfile("Ken Wanatabe", true, R.drawable.profile),
    UserProfile("Julia Roberts", false, R.drawable.profile2),
    UserProfile("Ken Wanatabe", true, R.drawable.profile),
    UserProfile("Julia Roberts", false, R.drawable.profile2),
    UserProfile("Ken Wanatabe", true, R.drawable.profile),
    UserProfile("Julia Roberts", false, R.drawable.profile2),
)