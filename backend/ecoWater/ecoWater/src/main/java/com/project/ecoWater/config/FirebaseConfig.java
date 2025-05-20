package com.project.ecoWater.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() {
        try {
            String base64Credentials = System.getenv("FIREBASE_CREDENTIALS_BASE64");

            // 2. Decodifícalo a bytes[]
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);

            // 3. Convierte los bytes a un InputStream
            InputStream serviceAccount = new ByteArrayInputStream(decodedBytes);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options);
            }
            return FirebaseApp.getInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }

}