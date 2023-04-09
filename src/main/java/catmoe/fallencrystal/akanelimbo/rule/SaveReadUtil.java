package catmoe.fallencrystal.akanelimbo.rule;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.*;
import java.util.UUID;
import java.util.HashMap;

@SuppressWarnings(value={"all"})
public class SaveReadUtil {
    private static final String file = ("readed.txt");
    private final HashMap<UUID, Boolean> playerData;

    public SaveReadUtil() {playerData = new HashMap<>(); LoadData();}

    public void CreateFile() {
        File f = new File("readed.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void LoadData() {
        CreateFile();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(".");
                UUID uuid = UUID.fromString(parts[0]);
                boolean read = Boolean.parseBoolean(parts[1]);
                playerData.put(uuid, read);
            }
            reader.close();
        } catch (IOException e) {e.printStackTrace();}}

    public void setData(ProxiedPlayer p, boolean read) {
        playerData.put(p.getUniqueId(), read);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(p.getUniqueId().toString() + "," + read + "\n");
            writer.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public boolean getPlayerData(ProxiedPlayer p) {return playerData.getOrDefault(p.getUniqueId(), false);}
}
