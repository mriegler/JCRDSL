import com.authzee.kotlinguice4.KotlinModule
import java.util.*

class Module : KotlinModule() {
    override fun configure() {
        val evenTime = Calendar.getInstance().timeInMillis.rem(2) == 0L
        if (evenTime) {
            bind<Greeter>().to<NiceGreeter>()
        } else {
            bind<Greeter>().to<RudeGreeter>()
        }
    }
}