package pw.mintea.solboard.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.entity.Player
import org.bukkit.event.Event
import pw.mintea.solboard.util.Board

class UpdateBoard : Effect() {

    companion object {
        init {
            Skript.registerEffect(UpdateBoard().javaClass, "(update|set) solboard (of|for) %players% to %strings%")
        }
    }

    private lateinit var players: Expression<Player>
    private lateinit var lines: Expression<String>

    override fun toString(p0: Event?, p1: Boolean): String {
        return ""
    }

    @SuppressWarnings("unchecked")
    override fun init(expressions: Array<Expression<*>>, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        this.players = expressions[0] as Expression<Player>
        this.lines = expressions[1] as Expression<String>
        return true
    }

    override fun execute(evt: Event?) {
        val scoreboard: MutableList<String> = arrayListOf()
        for (line: String in lines.getArray(evt)) {
            scoreboard.add(line)
        }
        for (p: Player in players.getArray(evt)) {
            Board.updateBoard(p, scoreboard)
        }
    }
}