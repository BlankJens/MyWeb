package com.webservlet.MyWeb.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    
    @Value("${cloudinary.upload-preset:}")
    private String uploadPreset;

    public CloudinaryService(
            @Value("${cloudinary.cloud-name:}") String cloudName,
            @Value("${cloudinary.api-key:}") String apiKey,
            @Value("${cloudinary.api-secret:}") String apiSecret) {
        
        if (cloudName.isEmpty() || apiKey.isEmpty() || apiSecret.isEmpty()) {
            throw new IllegalArgumentException("As credenciais do Cloudinary não foram configuradas!");
        }
        
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> uploadOptions = new HashMap<>();

        // Este upload e assinado (api_key/api_secret no Cloudinary), e a
        // documentacao define upload_preset como "optional for signed uploading".
        // So enviamos o preset se ele estiver configurado: um preset vazio ou
        // inexistente faz a API recusar o upload.
        if (uploadPreset != null && !uploadPreset.isBlank()) {
            uploadOptions.put("upload_preset", uploadPreset);
        }

        uploadOptions.put("resource_type", "auto");
        uploadOptions.put("type", "upload");

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
        return uploadResult.get("secure_url").toString();
    }

}
