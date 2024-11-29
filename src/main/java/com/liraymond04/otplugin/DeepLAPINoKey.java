package com.liraymond04.otplugin;

import java.awt.Window;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

import org.omegat.core.CoreEvents;
import org.omegat.core.events.IApplicationEventListener;
import org.omegat.core.machinetranslators.BaseCachedTranslate;
import org.omegat.core.machinetranslators.MachineTranslators;
import org.omegat.util.Language;

import com.liraymond04.deepl.*;

public class DeepLAPINoKey extends BaseCachedTranslate {
    private static final String ALLOW_DEEPL_API_NOKEY = "allow_deep_api_nokey";
    protected static final ResourceBundle res = ResourceBundle.getBundle("DeepLAPINoKey", Locale.getDefault());

    @Override
    protected String getPreferenceName() {
        return ALLOW_DEEPL_API_NOKEY;
    }

    @Override
    public String getName() {
        return res.getString("DEEPL_NAME");
    }

    // Plugin setup
    public static void loadPlugins() {
        CoreEvents.registerApplicationEventListener(new IApplicationEventListener() {
            @Override
            public void onApplicationStartup() {
                MachineTranslators.add(new DeepLAPINoKey());
            }

            @Override
            public void onApplicationShutdown() {
                /* empty */
            }
        });
    }

    public static void unloadPlugins() {
        /* empty */
    }

    @Override
    protected String translate(Language sLang, Language tLang, String text) throws Exception {
        String cachedResult = getCachedTranslation(sLang, tLang, text);
        if (cachedResult != null) {
            return cachedResult;
        }

        try {
            return DeeplAPI.translate(sLang.getLanguageCode(), tLang.getLanguageCode(), text);
        } catch (Exception e) {
            return res.getString("ERROR_CONTACT") + e.getMessage();
        }
    }

    @Override
    public boolean isConfigurable() {
        return false; // Configuration not needed for deepl-cli
    }

    @Override
    public void showConfigurationUI(Window parent) {
        // No configuration UI for deepl-cli
    }
}
