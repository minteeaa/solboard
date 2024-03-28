package pw.mintea.solboard.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.entity.Player
import org.bukkit.event.Event
import pw.mintea.solboard.util.Board

class DeleteBoard : Effect() {

    companion object {
        init {
            Skript.registerEffect(DeleteBoard().javaClass, "(delete|remove|clear|wipe) solboard (of|for) %players%")
        }
    }

    private lateinit var players: Expression<Player>

    override fun toString(p0: Event?, p1: Boolean): String {
        return ""
    }

    @SuppressWarnings("unchecked")
    override fun init(expressions: Array<Expression<*>>, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        this.players = expressions[0] as Expression<Player>
        return true
    }

    override fun execute(evt: Event?) {
        for (p: Player in players.getArray(evt)) {
            Board.clearBoard(p)
        }
    }
}