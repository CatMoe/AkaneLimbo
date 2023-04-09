package catmoe.fallencrystal.akanelimbo.rule

import net.md_5.bungee.api.connection.ProxiedPlayer
import java.io.*
import java.util.*

class SaveReadUtil {
    private val playerData: HashMap<UUID, Boolean> = HashMap()

    init {
        loadData()
    }

    private fun createFile() {
        val f = File("readed.txt")
        if (!f.exists()) {
            try {
                f.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun loadData() {
        createFile()
        try {
            val reader = BufferedReader(FileReader(file))
            var line: String
            while (reader.readLine().also { line = it } != null) {
                val parts = line.split(".".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val uuid = UUID.fromString(parts[0])
                val read = java.lang.Boolean.parseBoolean(parts[1])
                playerData[uuid] = read
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setData(p: ProxiedPlayer, read: Boolean) {
        playerData[p.uniqueId] = read
        try {
            val writer = BufferedWriter(FileWriter(file, true))
            writer.write(p.uniqueId.toString() + "," + read + "\n")
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getPlayerData(p: ProxiedPlayer): Boolean {
        return playerData.getOrDefault(p.uniqueId, false)
    }

    companion object {
        private const val file = "readed.txt"
    }
}
