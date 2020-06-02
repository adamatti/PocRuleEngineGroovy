@file:JvmName("Main")
package adamatti

import com.beust.klaxon.Klaxon
import groovy.text.SimpleTemplateEngine
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spark.ModelAndView
import spark.kotlin.Http
import spark.kotlin.ignite
import spark.template.handlebars.HandlebarsTemplateEngine
import java.lang.Exception

class Main {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(Main::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val http: Http = ignite().port(8080)

            http.get("/") {
                render(mapOf("name" to "World"),"index.html")
            }

            registerTemplates(http)

            log.info("Started")
        }

        private fun registerTemplates(http: Http) {
            http.get("/template") {
                val model = mapOf(
                    "template" to "Hello \${name} \${lastName}",
                    "data" to "{\"name\":\"Marcelo\",\"lastName\":\"Adamatti\"}",
                    "result" to ""
                )
                render(model, "template.html")
            }

            http.post("/template") {
                val templateData = request.queryMap("txtTemplate").value()
                val data = request.queryMap("txtData").value()
                val result = Engine().processTemplate(templateData,data)

                val model = mapOf(
                    "template" to templateData,
                    "data" to data,
                    "result" to result
                )
                render(model, "template.html")
            }
        }

        private fun render(model: Any, templatePath:String) = HandlebarsTemplateEngine()
            .render(ModelAndView(model, templatePath))
    }
}

class Engine {
    fun processTemplate(templateData:String, jsonString:String ): String{
        try {
            val json = Klaxon().parse<MutableMap<*, *>>(jsonString)
            return processTemplate(templateData, json)
        } catch (error: Exception){
            return error.message.toString()
        }
    }

    fun processTemplate(templateData:String, data: MutableMap<*, *>?): String{
        try {
            val engine = SimpleTemplateEngine()
            val template = engine.createTemplate(templateData).make(data)
            return template.toString()
        }  catch (error: Exception) {
            return error.message.toString()
        }
    }
}
