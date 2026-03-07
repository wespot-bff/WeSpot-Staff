package com.wespot.staff.data.user.model

import com.wespot.staff.domain.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val schoolName: String,
    val grade: Int,
    val classNumber: Int,
    val withdrawalStatus: String,
) {
    fun toUser(): User = User(
        id = id,
        name = name,
        email = email,
        schoolName = schoolName,
        grade = grade,
        classNumber = classNumber,
        withdrawalStatus = withdrawalStatus,
    )
}
