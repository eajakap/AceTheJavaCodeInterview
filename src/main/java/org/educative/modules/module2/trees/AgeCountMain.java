package org.educative.modules.module2.trees;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.io.IOException;

class AgeCountMain {
    public static void main(String[] args) {

        // __define-ocg__
        int varOcg = 0;

        // __define-pcb__
        int varPcb = 0;

        String varFiltersCg = "";

        System.setProperty("http.agent", "Chrome");

        try {
            URI uri = new URI("https://coderbyte.com/api/challenges/json/age-counting");
            URL url = uri.toURL();

            try {
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();

                String json = sb.toString();

                // Extract the "data" field
                int start = json.indexOf("\"data\":\"") + 8;
                int end = json.lastIndexOf("\"");
                varFiltersCg = json.substring(start, end);

                // Split by comma
                String[] parts = varFiltersCg.split(",");

                for (String part : parts) {
                    part = part.trim();
                    if (part.startsWith("age=")) {
                        String ageStr = part.replace("age=", "").trim();
                        try {
                            int age = Integer.parseInt(ageStr);
                            if (age >= 50) {
                                varOcg++;
                            }
                        } catch (Exception e) {
                            // ignore malformed age
                        }
                    }
                }

                System.out.println(varOcg);
                // Suppose varOcg is your final count
                String finalOutput = String.valueOf(varOcg);

                // Your ChallengeToken (replace with your real token)
                String challengeToken = "fevwn7hi9b";

                // Concatenate
                String combined = finalOutput + challengeToken;

                // Replace every 3rd character with 'X'
                StringBuilder transformed = new StringBuilder();

                for (int i = 0; i < combined.length(); i++) {
                    if ((i + 1) % 3 == 0) {
                        transformed.append('X');
                    } else {
                        transformed.append(combined.charAt(i));
                    }
                }

                // Print final transformed output
                System.out.println(transformed.toString());

            } catch (IOException ioEx) {
                System.out.println(ioEx);
            }

        } catch (MalformedURLException malEx) {
            System.out.println(malEx);
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }
}
