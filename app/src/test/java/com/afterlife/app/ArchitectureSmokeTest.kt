package com.afterlife.app

import org.junit.Test
import org.junit.Assert.assertEquals

class ArchitectureSmokeTest {
    @Test
    fun applicationIdNamespaceIsStable() {
        assertEquals("com.afterlife.app", BuildConfig.APPLICATION_ID)
    }
}
