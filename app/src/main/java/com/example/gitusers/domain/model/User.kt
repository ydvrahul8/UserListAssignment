package com.example.gitusers.domain.model

data class User(
    val avatarUrl: String?,
    val login: String?,
    val nodeId: String?,
    val htmlUrl: String?,
    val type: String?,
    var isExpanded:Boolean = false
)