package catmoe.fallencrystal.akanelimbo.rule

import net.md_5.bungee.api.connection.ProxiedPlayer
import java.io.*
import java.util.*

class SaveReadUtil {
    private val playerData: HashMap<UUID, Boolean> = HashMap()

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
        try {
            val writer = BufferedWriter(FileWriter(file, true))
            writer.write(p.uniqueId.toString() + "," + read + "\n")
            writer.close()
        } catch (e: IOException) {
            throw IOException()
        }
    }

    fun getPlayerData(p: ProxiedPlayer): Boolean {
        return playerData.getOrDefault(p.uniqueId, false)
    }

    companion object {
        private const val file = "G:/readed.txt"
    }
}
