import com.authzee.kotlinguice4.getInstance
import com.google.inject.Guice
import com.google.inject.Inject
import mu.KLogging
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun main(args: Array<String>) {
    logger.debug { "Starting main with args $args, size: ${args.size}" }
    val injector = Guice.createInjector(Module())
    val publicRelations = injector.getInstance<PublicRelations>()

    publicRelations.handleUser("Marcel")
}

class PublicRelations @Inject constructor(val greeter: Greeter) {
    fun handleUser(name: String) {
        logger.debug { "PR handling user $name" }
        println(greeter.greet(name))
    }

    companion object : KLogging()
}

interface Greeter {
    fun greet(name: String): String
}

class RudeGreeter : Greeter {
    override fun greet(name: String) = "Hi, $name, you fatass!"
}

class NiceGreeter : Greeter {
    override fun greet(name: String): String = "Hello, my dear $name"
}
