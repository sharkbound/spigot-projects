package sharkbound.spigot.skyblock.plugin

import org.bukkit.entity.Player
import org.intellij.lang.annotations.Language
import org.sqlite.SQLiteDataSource
import sharkbound.spigot.skyblock.plugin.extensions.strId
import java.sql.PreparedStatement
import java.sql.Statement
import java.util.*
import java.util.zip.CheckedOutputStream

data class PlayerData(val id: Int, val uuid: UUID, val tokens: Int)

object DB {
    val data = SQLiteDataSource().apply { url = "jdbc:sqlite:skyblock.sqlite" }
    val connection
        get() = data.connection

    inline fun statement(func: Statement.() -> Unit) =
        connection.createStatement().apply(func)

    inline fun preparedStatement(@Language("SQLite") query: String, func: PreparedStatement.() -> Unit) =
        connection.prepareStatement(query).apply(func)

    fun init() {
        statement {
            executeUpdate(
                """
                create table if not exists sky_block(
                    id     INTEGER not null primary key autoincrement,
                    uuid   TEXT    not null,
                    tokens int default 0 not null
                );"""
            )
        }
    }

    fun playerData(player: Player): PlayerData? =
        preparedStatement("select * from sky_block where uuid = ?") {
            setString(1, player.strId)
        }.executeQuery()?.run {
            PlayerData(getInt("id"), UUID.fromString(getString("uuid")), getInt("tokens"))
        }

    fun initPlayer(player: Player) {
//        check if the p
//        layers data already exists
        if (preparedStatement("select exists(select 1 from sky_block where uuid = ?)") {
                setString(1, player.strId)
            }.executeQuery().getInt(1) == 0) {
//          found a entry, return out of the function
            return
        }

//      player data does not exists, create a row for it
        preparedStatement("insert into sky_block (uuid, tokens) values (?, ?)") {
            setString(1, player.strId)
            setInt(2, 0)
        }.run {
            executeUpdate()
            close()
        }
    }
}
