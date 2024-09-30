package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {
    // TODO Task: pick appropriate instance variables for this class

    private final Map<String, Map<String, String>> countryTranslations = new HashMap<>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length(); i++) {
                // Get each country object
                JSONObject country = jsonArray.getJSONObject(i);

                // Get country code and add it to the map
                String countryCode = country.getString("alpha3");
                if (!countryTranslations.containsKey(countryCode)) {
                    // Haven't seen this country yet
                    countryTranslations.put(countryCode, new HashMap<>());
                }
                
                // Get list of languages for that country
                // The JSON is a flat map containing things that are not languages,
                // which are ignored when looking for languages.
                List<String> forbiddenKeys = List.of("id", "alpha2", "alpha3");
                
                for (String languageCode : country.keySet()) {
                    if (!forbiddenKeys.contains(languageCode)) {
                        Map<String, String> countryLanguages = countryTranslations.get(countryCode);
                        if (!countryLanguages.containsKey(languageCode)) {
                            // Haven't seen this language yet for this country
                            countryLanguages.put(languageCode, country.getString(languageCode));
                        }
                    }
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryTranslations.get(country).keySet());
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryTranslations.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        return countryTranslations.get(country).get(language);
    }
}
