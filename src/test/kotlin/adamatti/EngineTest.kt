package adamatti

import org.amshove.kluent.`should be equal to`
import org.junit.Test

class EngineTest {
    @Test
    fun `test template as Map`(){
        // Given
        val template = "Hello \${name}"
        val data = mutableMapOf(
            "name" to "Adamatti"
        )

        // When
        val result = Engine().processTemplate(template,data)

        // Then
        result `should be equal to` "Hello Adamatti"
    }

    @Test
    fun `test template as String`(){
        // Given
        val template = "Hello \${name}"
        val data = """{"name":"Adamatti"}"""

        // When
        val result = Engine().processTemplate(template,data)

        // Then
        result `should be equal to` "Hello Adamatti"
    }

    @Test
    fun `test rule`(){
        // Given
        val rule = "name == 'Adamatti' "
        val data = """{"name":"Adamatti"}"""

        // When
        val result = Engine().processRule(rule,data)

        // Then
        result `should be equal to` "true"
    }
}
