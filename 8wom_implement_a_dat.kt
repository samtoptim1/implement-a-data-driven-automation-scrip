import java.util.*

data class ScriptConfig(val scriptId: String, val triggers: List<String>, val actions: List<String>)

data class AutomationScript(val id: String, val config: ScriptConfig, val state: ScriptState)

enum class ScriptState { IDLE, RUNNING, ERROR }

class ScriptController(private val scripts: List<AutomationScript>) {

    private val scriptStates: MutableMap<String, ScriptState> = mutableMapOf()

    init {
        scripts.forEach { scriptStates[it.id] = ScriptState.IDLE }
    }

    fun startScript(scriptId: String) {
        val script = scripts.find { it.id == scriptId } ?: throw Exception("Script not found")
        if (scriptStates[scriptId] == ScriptState.RUNNING) {
            println("Script $scriptId is already running")
            return
        }
        scriptStates[scriptId] = ScriptState.RUNNING
        executeScript(script.config)
    }

    private fun executeScript(config: ScriptConfig) {
        // implement script execution logic here
        println("Executing script ${config.scriptId}")
        config.triggers.forEach { println("  Trigger: $it") }
        config.actions.forEach { println("  Action: $it") }
    }
}

fun main() {
    val script1 = AutomationScript("script1", ScriptConfig("script1", listOf("trigger1", "trigger2"), listOf("action1", "action2")), ScriptState.IDLE)
    val script2 = AutomationScript("script2", ScriptConfig("script2", listOf("trigger3"), listOf("action3", "action4")), ScriptState.IDLE)

    val controller = ScriptController(listOf(script1, script2))
    controller.startScript("script1")
    controller.startScript("script2")
}