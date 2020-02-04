package sharkbound.spigot.skyblock.plugin

import org.bukkit.entity.Player
import org.intellij.lang.annotations.Language
import org.sqlite.SQLiteConfig
import org.sqlite.SQLiteDataSource
import sharkbound.commonutils.extensions.closeAfter
import sharkbound.spigot.skyblock.plugin.extensions.strId
import java.sql.PreparedStatement
import java.sql.Statement
import java.util.*

data class PlayerData(val id: Int, val uuid: UUID, val tokens: Int)

object DB {
    val data = SQLiteDataSource().apply { url = "jdbc:sqlite:skyblock.sqlite" }
    val connection get() = data.connection

    inline fun statement(func: Statement.() -> Unit) =
        connection.createStatement().apply(func)

    inline fun preparedStatement(@Language("SQLite") query: String, func: PreparedStatement.() -> Unit) =
        connection.prepareStatement(query).apply(func)

    fun init() {
        statement {
            closeAfter {
                executeUpdate(
                    """
                create table if not exists sky_block(
                    id     integer not null primary key autoincrement,
                    uuid   text    not null,
                    tokens integer default 0 not null
                );"""
                )
            }

        }
    }

    fun playerData(player: Player): PlayerData? {
        preparedStatement("select * from sky_block where uuid = ?") {
            setString(1, player.strId)
        }.executeQuery().apply {
            if (!next()) {
                return null
            }

            return PlayerData(
                getInt("id"),
                UUID.fromString(getString("uuid")),
                getInt("tokens")
            ).also {
                close()
            }
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
        preparedStatement("insert into sky_block (uuid, tokens) values (?, ?)") {
            setString(1, player.strId)
            setInt(2, 0)
        }.closeAfter {
            executeUpdate()
        }
    }
}
