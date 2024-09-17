package io.github.sakurawald.core.config;

import io.github.sakurawald.core.config.handler.abst.BaseConfigurationHandler;
import io.github.sakurawald.core.config.handler.impl.ObjectConfigurationHandler;
import io.github.sakurawald.core.config.model.ConfigModel;


public class Configs {

    public static final BaseConfigurationHandler<ConfigModel> configHandler = new ObjectConfigurationHandler<>("config.json", ConfigModel.class);

}
