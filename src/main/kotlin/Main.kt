import com.authzee.kotlinguice4.getInstance
import com.google.inject.Guice
import com.google.inject.Inject

fun main(args: Array<String>) {
    val injector = Guice.createInjector(Module())
    val publicRelations = injector.getInstance<PublicRelations>()

    publicRelations.handleUser("Marcel")
}

class PublicRelations @Inject constructor(val greeter: Greeter) {
    fun handleUser(name: String) {
        println(greeter.greet(name))
    }
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
