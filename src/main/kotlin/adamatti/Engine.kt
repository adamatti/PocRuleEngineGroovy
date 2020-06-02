package adamatti

import groovy.lang.Binding
import groovy.lang.GroovyShell
import groovy.text.SimpleTemplateEngine
import java.lang.Exception

class Engine {
    fun processRule(ruleData: String, jsonString: String): String = try {
        val bindings = Binding(jsonString.toJson())
        val shell = GroovyShell(bindings)
        shell.evaluate(ruleData).toString()
    } catch (error: Exception){
        error.message.toString()
    }

    fun processTemplate(templateData:String, jsonString:String ): String = try {
        processTemplate(templateData, jsonString.toJson())
    } catch (error: Exception){
        error.message.toString()
    }

    fun processTemplate(templateData:String, data: MutableMap<*, *>?): String = try {
        val engine = SimpleTemplateEngine()
        val template = engine.createTemplate(templateData).make(data)
        template.toString()
    }  catch (error: Exception) {
        error.message.toString()
    }
}
