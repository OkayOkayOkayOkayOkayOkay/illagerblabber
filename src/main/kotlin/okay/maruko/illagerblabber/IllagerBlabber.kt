package okay.maruko.illagerblabber

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object IllagerBlabber : ModInitializer {
	const val MOD_ID = "illagerblabber"
	private val logger = LoggerFactory.getLogger("illagerblabber")

	override fun onInitialize() {

		IllagerSounds.registerAll()

		logger.info("Get ready for some Illager yapping..")
	}
}
