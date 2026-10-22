package com.awilab.testing

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals

infix fun <A> A.shouldBe(that: A) = assertEquals(that, this)

infix fun <A> A.shouldBeOne(list: List<A>) = assertEquals(true, list.any { this == it })

infix fun <A> LiveData<A>.shouldBe(that: A) = assertEquals(that, this.value)
