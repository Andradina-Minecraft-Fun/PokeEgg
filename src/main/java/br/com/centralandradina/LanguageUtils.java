package br.com.centralandradina;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



/**
 * Helper to translate based on Minecraft translate file
 * 
 * idea from https://github.com/LOOHP/InteractiveChat/blob/6a5d9a60df31fe4f24ee36880c65c1855725f8f5/src/main/java/com/loohp/interactivechat/utils/LanguageUtils.java#L66
 */
public class LanguageUtils
{

	protected String language;
	protected JavaPlugin plugin;
	private static final Map<String, Map<String, String>> translations = new HashMap<>();

	public LanguageUtils(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}

	public void loadTranslations(String language)
	{
		this.language = language;
		String languageFilePath = this.plugin.getDataFolder() + "/languages/" + language + ".json";

		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(languageFilePath), StandardCharsets.UTF_8);
			JSONObject json = (JSONObject) new JSONParser().parse(reader);
			reader.close();

			Map<String, String> mapping = new HashMap<>();
			for (Object obj : json.keySet()) {
				try {
					String key = (String) obj;
					mapping.put(key, (String) json.get(key));
				} catch (Exception e) {
				}
			}
			translations.put(language, mapping);
			
		}
		catch (Exception e) {
			plugin.getLogger().info(e.getMessage());
			e.printStackTrace();
		}

		
	}

	public String getTranslation(String translationKey) {
		try {
			Map<String, String> mapping = translations.get(language);
			if(mapping == null) {
				return getTranslation(translationKey);
			}
			else {
				return mapping.getOrDefault(translationKey, translationKey);
			}

		} catch (Exception e) {
			return translationKey;
		}
	}	
}
