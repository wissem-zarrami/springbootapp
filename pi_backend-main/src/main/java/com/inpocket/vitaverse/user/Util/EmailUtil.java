package com.inpocket.vitaverse.user.Util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;
    private final Map<String, String> verificationCodes = new HashMap<>();
    public void sendVerificationCodeEmail(String email) throws MessagingException {
        String verificationCode = generateVerificationCode(); // Générer le code de vérification
        verificationCodes.put(email, verificationCode); // Stocker le code de vérification dans la map

        // Construire l'URL avec le code de vérification
        String verificationUrl = "https://vitaverse/reset-password?code=" + verificationCode;

        // Construire le contenu HTML de l'e-mail
        String htmlContent = "<html><body style=\"background-image: url('https://previews.123rf.com/images/wstockstudio/wstockstudio1610/wstockstudio161000165/65224895-fitness-ou-d-un-concept-de-musculation-fond-photographie-de-produit-d-halt%C3%A8res-de-fer-vieux-et.jpg'); background-size: cover; display: flex; justify-content: center; align-items: center;\">"
                + "<div style=\"background-color: rgba(255, 255, 255, 0.8); padding: 20px; border-radius: 10px; text-align: center;\">"
                + "<h2>Verification Code for Password Reset</h2>"
                + "<p>Click the following link to reset your password:</p>"
                + "<p><a href=\"" + verificationUrl + "\">Reset Password</a></p>"
                + "</div>"
                + "</body></html>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verification Code for Password Reset");
        mimeMessageHelper.setText(htmlContent, true); // true indique que le contenu est au format HTML
        javaMailSender.send(mimeMessage);
    }
    public void sendVerification(String email) throws MessagingException {
        String verificationCode = generateVerificationCode(); // Générer le code de vérification
        verificationCodes.put(email, verificationCode); // Stocker le code de vérification dans la map
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verification Code for Password Reset");
        mimeMessageHelper.setText("Your verification code for password reset is: " + verificationCode, false);
        javaMailSender.send(mimeMessage);
    }
  public String getSavedVerificationCode(String email) {
        return verificationCodes.get(email);
    }
    public boolean verifyVerificationCode(String email, String verificationCode) {
        String savedVerificationCode = getSavedVerificationCode(email);
        return savedVerificationCode != null && savedVerificationCode.equals(verificationCode);
    }
    public String generateVerificationCode() {
        // Générer un code aléatoire avec une longueur spécifique
        int length = 6; // Longueur du code de vérification
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10)); // Générer un chiffre aléatoire de 0 à 9
        }
        return code.toString();
    }

}



