package com.awilab.testing

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

infix fun <A> A.shouldBe(that: A) = assertEquals(that, this)

infix fun <A> A.shouldBeOne(list: List<A>) = assertEquals(true, list.any { this == it })

infix fun <A> LiveData<A>.shouldBe(that: A) = assertEquals(that, this.value)

inline fun <reified T> Any?.shouldBeInstanceOf(): T {
    assertTrue("expected ${T::class.java.name} but was ${this?.let { it::class.java.name }}", this is T)
    return this as T
}
