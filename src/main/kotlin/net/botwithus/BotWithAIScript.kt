package net.botwithus

import net.botwithus.internal.scripts.ScriptDefinition
import net.botwithus.rs3.game.Client
import net.botwithus.rs3.script.Execution
import net.botwithus.rs3.script.LoopingScript
import net.botwithus.rs3.script.config.ScriptConfig
import java.util.*

class BotWithAIScript(name: String, scriptConfig: ScriptConfig, scriptDefinition: ScriptDefinition): LoopingScript(name, scriptConfig, scriptDefinition) {

	private val random: Random = Random()
	var botState: BotState = BotState.IDLE

	enum class BotState {
		//define your bot states here
		IDLE,
		RUNNING,
		CAPTCHA,
	}

	override fun initialize(): Boolean {
		super.initialize()
		// Set the script graphics context to our custom one
		this.sgc = BotWithAIGraphicsContext(this, console)
		return true
	}

	override fun onLoop() {
		val player = Client.getLocalPlayer()
		if (Client.getGameState() != Client.GameState.LOGGED_IN || player == null || botState == BotState.IDLE) {
			Execution.delay(random.nextLong(600, 1200))
			return
		}
		when (botState) {

			BotState.IDLE    -> {
				println("We're idle!")

			}

			BotState.RUNNING -> {

			}

			BotState.CAPTCHA -> {}
		}
		Execution.delay(random.nextLong(600, 1200))
		return
	}
}