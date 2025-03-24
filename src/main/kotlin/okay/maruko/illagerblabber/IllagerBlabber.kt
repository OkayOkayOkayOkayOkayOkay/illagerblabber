package okay.maruko.illagerblabber

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object IllagerBlabber : ModInitializer {
	const val MOD_ID = "illagerblabber"
	private val logger = LoggerFactory.getLogger("illagerblabber")

	override fun onInitialize() {
		logger.info("Get ready for some Illagerblabbering..")

		// Simply accessing the IllagerSounds object will register all sounds
		// due to Kotlin's object initialization
		IllagerSounds.registerAll()
	}
}
