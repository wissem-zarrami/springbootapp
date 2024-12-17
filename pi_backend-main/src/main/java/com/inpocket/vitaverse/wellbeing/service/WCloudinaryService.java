package com.inpocket.vitaverse.wellbeing.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class WCloudinaryService {
    Cloudinary cloudinary;

    public WCloudinaryService() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "dr0qtqovb");
        valuesMap.put("api_key", "388622796126293");
        valuesMap.put("api_secret", "pOqCPFqUoE7c5DrYV9CVxnww9Dk");
        cloudinary = new Cloudinary(valuesMap);
    }
    public Map<String, String> uploadVideoFromMultipartFile(MultipartFile videoFile) {
        try {
            // Utilisation de cloudinary.uploader().upload pour télécharger le fichier
            Map<String, Object> uploadResult = cloudinary.uploader().upload(videoFile.getBytes(),
                    ObjectUtils.asMap("resource_type", "video"));

            // Conversion du résultat en Map<String, String>
            Map<String, String> result = new HashMap<>();
            result.put("url", (String) uploadResult.get("url"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }






    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if (!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
        }
        return result;
    }

    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

}
