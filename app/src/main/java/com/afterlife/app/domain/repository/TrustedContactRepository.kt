package com.afterlife.app.domain.repository

import com.afterlife.app.domain.model.TrustedContact

/**
 * Contract for the single trusted contact. Implementations belong in the data layer.
 */
interface TrustedContactRepository {
    suspend fun getTrustedContact(): TrustedContact?
    suspend fun saveTrustedContact(contact: TrustedContact)
    suspend fun clearTrustedContact()
}
