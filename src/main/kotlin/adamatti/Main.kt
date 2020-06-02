@file:JvmName("Main")
package adamatti

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spark.ModelAndView
import spark.kotlin.Http
import spark.kotlin.ignite
import spark.template.handlebars.HandlebarsTemplateEngine

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
            registerRules(http)

            log.info("Started")
        }

        private fun registerTemplates(http: Http) {
            http.get("/template") {
                val model = mapOf(
                    "template" to "Hello \${name} \${lastName}",
                    "data" to """{"name":"Marcelo","lastName":"Adamatti","age":"37"}""",
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

        private fun registerRules(http: Http) {
            http.get("/rule") {
                val model = mapOf(
                    "rule" to """(name=="Marcelo")""",
                    "data" to """{"name":"Marcelo","lastName":"Adamatti","age":"37"}""",
                    "result" to ""
                )
                render(model, "rule.html")
            }

            http.post("/rule") {
                val ruleData = request.queryMap("txtRule").value()
                val data = request.queryMap("txtData").value()
                val result = Engine().processRule(ruleData,data)

                val model = mapOf(
                    "rule" to ruleData,
                    "data" to data,
                    "result" to result
                )
                render(model, "rule.html")
            }
        }

        private fun render(model: Any, templatePath:String) = HandlebarsTemplateEngine()
            .render(ModelAndView(model, templatePath))
    }
}
