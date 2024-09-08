package io.github.sakurawald.core.config.handler.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import io.github.sakurawald.Fuji;
import io.github.sakurawald.core.auxiliary.LogUtil;
import io.github.sakurawald.core.config.handler.abst.ConfigHandler;
import lombok.Cleanup;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.InvocationTargetException;


public class ObjectConfigHandler<T> extends ConfigHandler<T> {

    final Class<T> configClass;

    public ObjectConfigHandler(File file, Class<T> configClass) {
        super(file);
        this.file = file;
        this.configClass = configClass;
    }

    public ObjectConfigHandler(@NotNull String child, Class<T> configClass) {
        this(new File(Fuji.CONFIG_PATH.toString(), child), configClass);
    }

    public void loadFromDisk() {
        // Does the file exist?
        try {
            if (!file.exists()) {
                saveToDisk();
            } else {
                // read older json from disk
                @Cleanup Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)));
                JsonElement currentJsonElement = JsonParser.parseReader(reader);

                // merge older json with newer json
                T defaultJsonInstance = configClass.getDeclaredConstructor().newInstance();
                JsonElement defaultJsonElement = gson.toJsonTree(defaultJsonInstance, configClass);
                mergeJson(currentJsonElement, defaultJsonElement);

                // read merged json
                model = gson.fromJson(currentJsonElement, configClass);

                this.saveToDisk();
            }

        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            LogUtil.error("Load config failed: ", e);
        }
    }


    public void saveToDisk() {
        try {
            // Should we generate a default config instance ?
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                this.file.getParentFile().mkdirs();
                this.model = configClass.getDeclaredConstructor().newInstance();
            }

            // Save.
            JsonWriter jsonWriter = gson.newJsonWriter(new BufferedWriter(new FileWriter(this.file)));
            gson.toJson(this.model, configClass, jsonWriter);
            jsonWriter.close();
        } catch (IOException | InstantiationException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
