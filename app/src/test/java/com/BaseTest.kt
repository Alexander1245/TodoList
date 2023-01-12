package com

import org.junit.Assert
import org.junit.Before

fun assertCollectionsEquals(expected: Collection<*>, actual: Collection<*>) {
    Assert.assertArrayEquals(expected.toTypedArray(), actual.toTypedArray())
}

interface BaseTest {
    abstract class Default : BaseTest {
        @Before
        abstract fun beforeEach()
    }
}