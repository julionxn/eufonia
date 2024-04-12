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

/**
 * Clase abstracta que representa una configuración básica que es serializada a un json.
 * Todos los parámetros que se quieran ser serializados deberán de estar marcados con la
 * anotación @Expose.
 * @param <T> Clase a ser serializada.
 * @see Expose
 */
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

    /**
     * @param path El path del archivo. La raíz es la carpeta "config/jueguitos/" de fabric
     * @param version La versión actual de serialización.
     * @param clazz La clase que será serializada.
     */
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

    /**
     * Cargar la configuración.
     * En caso de que no exista el archivo, este será creado desde 0.
     * Si la versión actual no corresponde con la versión del archivo de configuración,
     * una nueva configuración será creada desde 0.
     */
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

    /**
     * @return this;
     */
    protected abstract T getCurrentInstance();

    /**
     * Ejecutado después de que la configuración haya sido cargada.
     */
    protected abstract void afterLoad();

    /**
     * Guardar el estado actual de la instancia al archivo.
     */
    public void save() {
        try (FileWriter fileWriter = new FileWriter(configFile)) {
            GSON.toJson(getCurrentInstance(), fileWriter);
        } catch (IOException e) {
            Jueguitos.LOGGER.error("Something went wrong while saving the config.");
        }
    }
}
