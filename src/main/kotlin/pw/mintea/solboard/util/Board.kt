package pw.mintea.solboard.util

import fr.mrmicky.fastboard.FastBoard
import org.bukkit.entity.Player
import java.util.*

class Board {
    companion object {
        private var boards: MutableMap<UUID, FastBoard> = HashMap()

        fun createBoard(player: Player) {
            val board = object : FastBoard(player) {
                override fun hasLinesMaxLength(): Boolean { return true }
            }
            boards[player.uniqueId] = board
        }

        fun deleteBoard(player: Player) {
            val board: FastBoard = getBoard(player)
            boards.remove(player.uniqueId)
            board.delete()
        }

        private fun getBoard(player: Player): FastBoard {
            return boards[player.uniqueId]!!
        }

        fun updateTitle(player: Player, title: String) {
            getBoard(player).updateTitle(title)
        }

        fun updateBoard(player: Player, lines: List<String>) {
            getBoard(player).updateLines(lines)
        }

        fun updateBoardLine(player: Player, line: String, id: Int) {
            getBoard(player).updateLine(id, line)
        }

        fun deleteBoardLine(player: Player, id: Int) {
            getBoard(player).removeLine(id)
        }

        fun clearBoard(player: Player) {
            val board: FastBoard = getBoard(player)
            board.updateLines("")
            board.updateTitle("")
            board.removeLine(0)
        }
    }
}