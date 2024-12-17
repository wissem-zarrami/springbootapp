package com.inpocket.vitaverse.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        // Initialize Cloudinary with your configuration
        Cloudinary cloudinary = new Cloudinary();
        cloudinary.config.cloudName = "dtrlnz4ze";
        cloudinary.config.apiKey = "384736573257249";
        cloudinary.config.apiSecret = "RViuGTYFaGm-lDzkqJsAh5DvPbw";
        return cloudinary;
    }
}
