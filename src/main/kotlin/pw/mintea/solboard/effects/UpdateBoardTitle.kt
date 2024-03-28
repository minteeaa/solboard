package pw.mintea.solboard.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.entity.Player
import org.bukkit.event.Event
import pw.mintea.solboard.util.Board

class UpdateBoardTitle : Effect() {

    companion object {
        init {
            Skript.registerEffect(UpdateBoardTitle().javaClass, "(update|set) solboard title (of|for) %players% to %string%")
        }
    }

    private lateinit var players: Expression<Player>
    private lateinit var title: Expression<String>

    override fun toString(p0: Event?, p1: Boolean): String {
        return ""
    }

    @SuppressWarnings("unchecked")
    override fun init(expressions: Array<Expression<*>>, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        this.players = expressions[0] as Expression<Player>
        this.title = expressions[1] as Expression<String>
        return true
    }

    override fun execute(evt: Event?) {
        for (p: Player in players.getArray(evt)) {
            Board.updateTitle(p, title.getSingle(evt).toString())
        }
    }
}