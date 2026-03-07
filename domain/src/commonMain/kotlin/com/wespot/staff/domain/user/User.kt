package com.wespot.staff.domain.user

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val schoolName: String,
    val grade: Int,
    val classNumber: Int,
    val withdrawalStatus: String,
)
