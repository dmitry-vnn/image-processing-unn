package dmitry.metric

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class ImageMSEMetricTest {

    @Test
    fun test1() {
        assertTrue(true)
    }

    @Test
    fun test2() {
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun loadImage(): Unit {
            println("before")
        }
    }
}