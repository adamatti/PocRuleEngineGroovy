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

            log.info("Started")
        }

        private fun render(model: Map<Any,Any>, templatePath:String) = HandlebarsTemplateEngine()
            .render(ModelAndView(model, templatePath))
    }
}
