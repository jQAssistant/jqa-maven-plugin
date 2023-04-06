package com.buschmais.jqassistant.scm.maven.provider;

import java.io.File;
import java.util.List;

import com.buschmais.jqassistant.core.runtime.api.configuration.ConfigurationLoader;
import com.buschmais.jqassistant.core.runtime.impl.configuration.ConfigurationLoaderImpl;
import com.buschmais.jqassistant.scm.maven.configuration.MavenConfiguration;

import org.codehaus.plexus.component.annotations.Component;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * Provides the runtime {@link MavenConfiguration} for jQAssistant within a Maven reactor.
 * <p>
 * Declared as singleton to allow caching the {@link ConfigurationLoader} instance.
 */
@Component(role = ConfigurationProvider.class, instantiationStrategy = "singleton")
public class ConfigurationProvider {

    /**
     * Cached {@link ConfigurationLoader} instance.
     */
    private ConfigurationLoader configurationLoader;

    /**
     * Return the Configuration.
     *
     * @param executionRoot
     *     The Session execution root.
     * @param configLocations
     *     The config locations directory.
     * @param configSources
     *     Additional {@link ConfigSource}s.
     * @return The {@link MavenConfiguration}.
     */
    public synchronized MavenConfiguration getConfiguration(File executionRoot, List<String> configLocations, ConfigSource... configSources) {
        if (configurationLoader == null) {
            File userHome = new File(System.getProperty("user.home"));
            configurationLoader = new ConfigurationLoaderImpl(userHome, executionRoot, configLocations);
        }
        return configurationLoader.load(MavenConfiguration.class, configSources);
    }

}
