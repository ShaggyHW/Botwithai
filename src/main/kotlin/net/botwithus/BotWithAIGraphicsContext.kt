package net.botwithus

import net.botwithus.rs3.imgui.ImGui
import net.botwithus.rs3.imgui.ImGuiWindowFlag
import net.botwithus.rs3.script.ScriptConsole
import net.botwithus.rs3.script.ScriptGraphicsContext
import java.util.Random

class BotWithAIGraphicsContext(private val script: BotWithAIScript, console: ScriptConsole): ScriptGraphicsContext(console) {

	var capchaText = ""
	var submitCount = 0
	var inputBox = ""
	override fun drawSettings() {
		super.drawSettings()
		if (ImGui.Begin("BotWithAI", ImGuiWindowFlag.None.value)) {
			if (ImGui.BeginTabBar("My bar", ImGuiWindowFlag.None.value)) {
				if (ImGui.BeginTabItem("Settings", ImGuiWindowFlag.None.value)) {
					ImGui.Text("Welcome to BotWithAI!")
					ImGui.Text("Script State: " + script.botState)
					ImGui.EndTabItem()
				}
				if (script.botState == BotWithAIScript.BotState.IDLE) {
					if (ImGui.Button("Run")) {
						script.botState = BotWithAIScript.BotState.CAPTCHA
					}
				} else {
					if (script.botState == BotWithAIScript.BotState.CAPTCHA) {
						ImGui.Text("Something went wrong, prove you are not a bot:")

						if (submitCount > 0 && submitCount < 4) {
							ImGui.Text("WRONG CAPTCHA PLEASE TRY AGAIN")
						}


						if (submitCount > -1 && submitCount < 4) {
							if (capchaText.isEmpty()) capchaText = getRandomString(Random().nextInt(0, 12))
							ImGui.Text("CAPTCHA: $capchaText")
							inputBox = ImGui.InputText("", inputBox)

							if (ImGui.Button("Submit")) {
								if (submitCount > 2 && capchaText == inputBox)  {
									script.botState = BotWithAIScript.BotState.RUNNING
								} else {
									ImGui.Text("WRONG CAPTCHA PLEASE TRY AGAIN")
									submitCount++
									capchaText = getRandomString(Random().nextInt(0, 12))
								}
							}
						}
					}else{
						if (script.botState == BotWithAIScript.BotState.RUNNING) {
							ImGui.Text("What were you expecting? Happy April fools!!")

						}
					}


					if (submitCount > 3) if (ImGui.Button("Stop")) {
						script.botState = BotWithAIScript.BotState.IDLE
					}
				}
				ImGui.EndTabBar()

			}
			ImGui.End()
		}
	}

	fun getRandomString(length: Int): String {
		val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
		return (1..length).map { allowedChars.random() }.joinToString("")
	}

	override fun drawOverlay() {
		super.drawOverlay()
	}

}