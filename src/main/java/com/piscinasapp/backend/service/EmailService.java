package com.piscinasapp.backend.service;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("fvargas.mpiscinas@gmail.com");
        mailSender.send(message);
    }

    // Enviar correo con archivo adjunto
    public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);  // true: para manejar archivos adjuntos

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setFrom("fvargas.mpiscinas@gmail.com");

        // Determinar el tipo MIME adecuado para la imagen (jpeg o png)
        String mimeType = "application/octet-stream"; // Tipo por defecto si no se puede determinar

        // Detectar si la imagen es JPEG o PNG
        if (attachmentName.toLowerCase().endsWith(".jpg") || attachmentName.toLowerCase().endsWith(".jpeg")) {
            mimeType = "image/jpeg";
        } else if (attachmentName.toLowerCase().endsWith(".png")) {
            mimeType = "image/png";
        }

        // Crear DataSource con el tipo MIME adecuado
        DataSource dataSource = new ByteArrayDataSource(attachment, mimeType);
        helper.addAttachment(attachmentName, dataSource);  // Agregar el archivo adjunto

        // Enviar el correo con el adjunto
        mailSender.send(mimeMessage);
    }
}