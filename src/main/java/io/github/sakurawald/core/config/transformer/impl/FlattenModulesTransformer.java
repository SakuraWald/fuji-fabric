package io.github.sakurawald.core.config.transformer.impl;

import com.google.gson.JsonObject;
import com.jayway.jsonpath.DocumentContext;
import io.github.sakurawald.core.auxiliary.ReflectionUtil;
import io.github.sakurawald.core.config.handler.abst.BaseConfigurationHandler;
import io.github.sakurawald.core.config.transformer.abst.ConfigurationTransformer;

import java.nio.file.Files;


public class FlattenModulesTransformer extends ConfigurationTransformer {

    @Override
    public void apply() {
        if (Files.notExists(getPath())) return;

        String modulesPath = "$.modules";
        DocumentContext context = makeDocumentContext();
        JsonObject modules = (JsonObject) read(context,modulesPath);

        for (String topLevelModule : modules.keySet()) {
            FlattenTreeTransformer flattenTreeTransformer = new FlattenTreeTransformer(
                modulesPath + "." + topLevelModule
                , "enable"
                , topLevelModule, (level) -> ReflectionUtil.getModuleConfigPath(level).resolve(BaseConfigurationHandler.CONFIG_JSON));

            flattenTreeTransformer.configure(this.getPath());
            flattenTreeTransformer.apply();
        }
    }
}
