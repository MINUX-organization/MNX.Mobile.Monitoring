package com.minux.monitoring.core.network

import com.minux.monitoring.core.network.model.monitoring.RigStateChangeDto
import com.minux.monitoring.core.network.model.monitoring.RigStateOneOf
import org.junit.Test
import org.junit.Assert.*
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

class OneOfTest {

    @Test
    fun `type must match the contents of the newData field`() {
        val testRigStateChangeDto = RigStateChangeDto(
            rigId = "some id",
            type = "GpuState",
            newData = arrayOf("Active", "InActive")
        )

        val expected: KClass<*>
        val actual: KClass<*>

        when (testRigStateChangeDto.type) {
            RigStateOneOf.Name.toString() -> {
                expected = String::class
                actual = (testRigStateChangeDto.newData as String)::class
            }

            RigStateOneOf.GpuState.toString() -> {
                expected = Array<String>::class
                actual = (testRigStateChangeDto.newData as Array<*>)::class
            }

            else -> {
                throw IllegalArgumentException(
                    "type argument in Response must be one of allowed values"
                )
            }
        }

        assertEquals(expected, actual)
    }
}