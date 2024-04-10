package me.julionxn.jueguitos.core.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import me.julionxn.jueguitos.Jueguitos;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Path;

public abstract class SerializableJsonManager<T extends SerializableJsonManager<T>> {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .create();
    private final File configFile;
    private final Class<T> clazz;
    @Expose
    public final float version;

    protected SerializableJsonManager(String path, float version, Class<T> clazz) {
        Path folder = FabricLoader.getInstance().getConfigDir().resolve("jueguitos");
        File file = folder.toFile();
        if (!file.exists()){
            boolean made = file.mkdir();
            if (made) Jueguitos.LOGGER.info("Jueguitos config folder created.");
        }
        this.configFile = folder.resolve(path).toFile();
        this.version = version;
        this.clazz = clazz;
    }

    public void load() {
        try {
            if (!configFile.exists()) {
                save();
                afterLoad();
                return;
            }
            T data = GSON.fromJson(new FileReader(configFile), clazz);
            if (data == null) {
                afterLoad();
                return;
            }
            if (data.version != version) {
                Jueguitos.LOGGER.error("Mismatching version with current version of file " + configFile.getPath() + ". Restarting file.");
                save();
            } else {
                Jueguitos.LOGGER.info("Loading " + configFile.getPath());
                setValues(data);
            }
        } catch (IOException | IllegalAccessException e) {
            Jueguitos.LOGGER.error("Something went wrong while loading the config.", e);
        }
        afterLoad();
    }

    private void setValues(T data) throws IllegalAccessException {
        T instance = getCurrentInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Expose.class)) continue;
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
            field.setAccessible(true);
            field.set(instance, field.get(data));
        }
    }

    protected abstract T getCurrentInstance();
    protected abstract void afterLoad();

    public void save() {
        try (FileWriter fileWriter = new FileWriter(configFile)) {
            GSON.toJson(this, fileWriter);
        } catch (IOException e) {
            Jueguitos.LOGGER.error("Something went wrong while saving the config.");
        }
    }
}
