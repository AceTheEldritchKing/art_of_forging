package net.acetheeldritchking.art_of_forging;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.acetheeldritchking.art_of_forging.network.SSyncConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class Config {
    private static final Logger LOGGER = LogManager.getLogger();

    public static File jsonFile;
    public static Map<String, Map<String, WeaponSchema>> JSON_MAP = new HashMap<>();

    public static void init(Path folder) {
        jsonFile = new File(FileUtils.getOrCreateDirectory(folder, "serverconfig").toFile(), "bettercombatnbt.json");
        try {
            if (jsonFile.createNewFile()) {
                Path defaultConfigPath = FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).resolve("bettercombatnbt.json");
                InputStreamReader defaults = new InputStreamReader(Files.exists(defaultConfigPath)? Files.newInputStream(defaultConfigPath) :
                        Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/fightnbtintegration/bettercombatnbt.json")));
                FileOutputStream writer = new FileOutputStream(jsonFile, false);
                int read;
                while ((read = defaults.read()) != -1) {
                    writer.write(read);
                }
                writer.close();
                defaults.close();
            }
        } catch (IOException error) {
            LOGGER.warn(error.getMessage());
        }

        readConfig(jsonFile);
    }

    public static SSyncConfig configFileToSSyncConfig() {
        try {
            return new SSyncConfig(new String(Files.readAllBytes(jsonFile.toPath())));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void readConfig(String config) {
        JSON_MAP = new Gson().fromJson(config, new TypeToken<Map<String, Map<String, WeaponSchema>>>(){}.getType());
    }

    public static void readConfig(File path) {
        try (Reader reader = new FileReader(path)) {
            JSON_MAP = new Gson().fromJson(reader, new TypeToken<Map<String, Map<String, WeaponSchema>>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            JSON_MAP = new HashMap<>();
        }
    }

    static class WeaponSchema {
        public String parent;
        @Override
        public String toString(){
            return parent;
        }
    }

    public static String findWeaponByNBT(ItemStack stack) {
        if(JSON_MAP==null){
            System.out.println("NULL");
            return "";
        }
        if(stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            for (String key : tag.getAllKeys()) {
                if(JSON_MAP.containsKey(key)){
                    Map<String, WeaponSchema> map1 =  JSON_MAP.get(key);
                    if(map1.containsKey(tag.getString(key))){
                        return map1.get(tag.getString(key)).parent;
                    }
                }
            }
        }
        return "";
    }
}
