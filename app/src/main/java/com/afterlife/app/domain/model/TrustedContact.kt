package com.afterlife.app.domain.model

/**
 * Domain model only. Persistence, verification and permissions are added in later phases.
 */
data class TrustedContact(
    val phoneNumber: String,
    val displayName: String? = null,
    val isVerified: Boolean = false
)
