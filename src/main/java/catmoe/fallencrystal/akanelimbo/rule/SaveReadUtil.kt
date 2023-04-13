package catmoe.fallencrystal.akanelimbo.rule

import net.md_5.bungee.api.connection.ProxiedPlayer
import java.io.*
import java.util.*

class SaveReadUtil {
    private val playerData: MutableMap<UUID, Boolean> = mutableMapOf()

    init {
        createFile()
        loadData()
    }

    private fun createFile() {
        val f = File(file)
        if (!f.exists()) {
            try {
                f.createNewFile()
            } catch (e: IOException) {
                throw IOException()
            }
        }
    }

    fun loadData() {
        try {
            val reader = BufferedReader(FileReader(file))
            var line: String? = reader.readLine()
            while (line != null) {
                val parts = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val uuid = UUID.fromString(parts[0])
                val read = java.lang.Boolean.parseBoolean(parts[1])
                playerData[uuid] = read
                line = reader.readLine()
            }
            reader.close()
        } catch (e: IOException) {
            throw IOException()
        } catch (e: NullPointerException) {
            throw NullPointerException("File is null")
        }
    }

    fun setData(p: ProxiedPlayer, read: Boolean) {
        playerData[p.uniqueId] = read
        val uuid = p.uniqueId
        val current = getPlayerData(p)
        if (current == read) return
        if (current != null) {
            deleteLine(uuid.toString())
        }
        try {
            val writer = BufferedWriter(FileWriter(file, true))
            writer.write("$uuid,$read\n")
            writer.close()
        } catch (ignore: IOException) {
        }
    }

    fun getPlayerData(p: ProxiedPlayer): Boolean? {
        return playerData[p.uniqueId]
    }

    private fun deleteLine(lineToRemove: String) {
        val tempFile = File("$file.temp")
        val reader = BufferedReader(FileReader(file))
        val writer = BufferedWriter(FileWriter(tempFile))
        var currentLine: String?
        while (reader.readLine().also { currentLine = it } != null) {
            val trimmedLine = currentLine?.trim()
            if (trimmedLine != lineToRemove) {
                writer.write("$trimmedLine\n")
            }
        }
        writer.close()
        reader.close()
        val deleted = File(file).delete()
        val renamed = tempFile.renameTo(File(file))
        if (!deleted || !renamed) {
            throw IOException("Failed to delete or rename file")
        }
    }

    companion object {
        private const val file = "G:/readed.txt"
    }
}

