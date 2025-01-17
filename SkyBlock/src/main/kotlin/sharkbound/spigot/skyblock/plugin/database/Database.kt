package sharkbound.spigot.skyblock.plugin.database

import org.bukkit.entity.Player
import org.intellij.lang.annotations.Language
import org.sqlite.SQLiteDataSource
import sharkbound.commonutils.extensions.closeAfter
import sharkbound.spigot.skyblock.plugin.extensions.div
import sharkbound.spigot.skyblock.plugin.extensions.id
import sharkbound.spigot.skyblock.plugin.extensions.strId
import sharkbound.spigot.skyblock.plugin.extensions.toUUID
import sharkbound.spigot.skyblock.plugin.objects.FilePaths
import java.sql.PreparedStatement
import java.sql.Statement
import java.util.*

data class PlayerData(val id: Int, val uuid: UUID, val balance: Int)

enum class BalanceModifyOperation {
    Add, Sub, Set
}

object SkyBlockDatabase {
    val filePath = FilePaths.configFolder / "skyblock.sqlite"
    val data = SQLiteDataSource().apply { url = "jdbc:sqlite:$filePath" }

    val connection get() = data.connection

    inline fun statement(func: Statement.() -> Unit): Statement =
        connection.createStatement().apply(func)

    inline fun preparedStatement(
        @Language("SQLite") query: String,
        func: PreparedStatement.() -> Unit
    ): PreparedStatement =
        connection.prepareStatement(query).apply(func)

    fun init() {
        statement {
            closeAfter {
                executeUpdate(
                    """
                create table if not exists sky_block(
                    id integer not null primary key autoincrement,
                    player_name text not null unique,
                    uuid text not null unique,
                    balance integer default 0 not null
                );"""
                )
            }

        }
    }

    fun modifyBalance(player: Player, newBalance: Int, operation: BalanceModifyOperation) {
        modifyBalance(player.id, newBalance, operation)
    }

    fun modifyBalance(uuid: UUID, newBalance: Int, operation: BalanceModifyOperation) {
        val innerQuery = when (operation) {
            BalanceModifyOperation.Add -> "balance + $newBalance"
            BalanceModifyOperation.Sub -> "balance - $newBalance"
            BalanceModifyOperation.Set -> "$newBalance"
        }

        preparedStatement("update sky_block set balance = $innerQuery where uuid = ?") {
            setString(1, uuid.toString())
        } closeAfter {
            executeUpdate()
        }
    }

    fun dataFor(uuid: UUID): PlayerData? {
        preparedStatement("select * from sky_block where uuid = ?") {
            setString(1, uuid.toString())
        }.executeQuery().apply {
            if (!next()) {
                close()
                return null
            }

            return PlayerData(
                getInt("id"),
                getString("uuid").toUUID,
                getInt("balance")
            ).also {
                close()
            }
        }

    }

    fun balance(uuid: UUID): Int =
        dataFor(uuid)?.balance ?: 0

    fun updatePlayerName(player: Player) {
        // keep player name up to date in the database
        preparedStatement("update sky_block set player_name = ? where uuid = ?") {
            setString(1, player.displayName)
            setString(2, player.strId)
        }.closeAfter {
            executeUpdate()
        }
    }

    fun initPlayer(player: Player) {
//        check if the players data already exists
        preparedStatement("select exists(select 1 from sky_block where uuid = ?)") {
            setString(1, player.strId)
        }.closeAfter {
            if (executeQuery().getInt(1) == 1) {
                // found a entry, return out of the function
                return
            }
        }

        // player data does not exists, create a row for it
        preparedStatement("insert into sky_block (uuid, balance, player_name) values (?, ?, ?)") {
            setString(1, player.strId)
            setInt(2, 0)
            setString(3, player.displayName)
        }.closeAfter {
            executeUpdate()
        }
    }
}
