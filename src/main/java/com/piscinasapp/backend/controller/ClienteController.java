package com.piscinasapp.backend.controller;

import com.piscinasapp.backend.model.Cliente;
import com.piscinasapp.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "https://piscinas-app.web.app")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private EmailService emailService;

    // Endpoint 1: Enviar correo por cada mantención realizada
    @PostMapping("/enviarCorreo")
    public ResponseEntity<Map<String, String>> enviarCorreo(@RequestBody Cliente cliente) {
        try {
            System.out.println("Imagen Base64 recibida: " + cliente.getFotoMantencion());

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            String formattedDate = now.format(dateFormatter);
            String formattedTime = now.format(timeFormatter);

            int totalEntero = (int) Math.round(cliente.getTotal());

            String subject = "Mantención Piscinas";
            StringBuilder message = new StringBuilder("Hola " + cliente.getName() + " " + cliente.getApellido() + ",\n\n" +
                    "Tu mantención número " + cliente.getMantenciones() + " del mes se realizó con éxito.\n" +
                    "Fecha: " + formattedDate + ".\n\n" +
                    "Hora: " + formattedTime + ".\n\n" +
                    "Descripción de la mantención: " + cliente.getDescripcion() + "\n\n");

            if (cliente.getFotoMantencion() != null && !cliente.getFotoMantencion().isEmpty()) {
                try {
                    String base64Image = cliente.getFotoMantencion().split(",")[1];
                    byte[] fotoBytes = Base64.getDecoder().decode(base64Image);

                    emailService.sendEmailWithAttachment(
                            cliente.getEmail(),
                            subject,
                            message.toString(),
                            fotoBytes,
                            "foto-mantencion.jpg"
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    emailService.sendEmail(cliente.getEmail(), subject, message.toString());
                }
            } else {
                emailService.sendEmail(cliente.getEmail(), subject, message.toString());
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Correo enviado con éxito.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al enviar el correo.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    // Endpoint 2: Cerrar el mes manualmente y enviar resumen
    @PostMapping("/cerrarMes")
    public ResponseEntity<Map<String, String>> cerrarMes(@RequestBody Cliente cliente) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            String formattedDate = now.format(dateFormatter);
            String formattedTime = now.format(timeFormatter);

            int totalEntero = (int) Math.round(cliente.getTotal());

            String subject = "Resumen del Mes - Mantenciones";
            StringBuilder message = new StringBuilder("Hola " + cliente.getName() + ",\n\n" +
                    "Tu mantención número " + cliente.getMantenciones() + " del mes se realizó con éxito.\n" +
                    "Fecha: " + formattedDate + ". Hora: " + formattedTime + ".\n\n" +
                    "Descripción de la última mantención: " + cliente.getDescripcion() + "\n\n" +
                    "El monto total a cancelar este mes es de $" + totalEntero + ".\n\n");

            if (cliente.getFotoMantencion() != null && !cliente.getFotoMantencion().isEmpty()) {
                try {
                    String base64Image = cliente.getFotoMantencion().split(",")[1];
                    byte[] fotoBytes = Base64.getDecoder().decode(base64Image);

                    emailService.sendEmailWithAttachment(
                            cliente.getEmail(),
                            subject,
                            message.toString(),
                            fotoBytes,
                            "foto-mantencion.jpg"
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    emailService.sendEmail(cliente.getEmail(), subject, message.toString());
                }
            } else {
                emailService.sendEmail(cliente.getEmail(), subject, message.toString());
            }

            cliente.setMantenciones(0);
            cliente.setTotal(0);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Correo de resumen enviado y datos reiniciados.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al cerrar el mes.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    // Endpoint 3: Verificar si el cliente alcanza 5 mantenciones y enviar resumen
    @PostMapping("/verificarMantenciones")
    public ResponseEntity<Map<String, String>> verificarMantenciones(@RequestBody Cliente cliente) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            String formattedDate = now.format(dateFormatter);
            String formattedTime = now.format(timeFormatter);
            int totalEntero = (int) Math.round(cliente.getTotal());

            if (cliente.getMantenciones() == 5) {
                String subject = "Resumen del Mes - Mantenciones";
                StringBuilder message = new StringBuilder("Hola " + cliente.getName() + ",\n\n" +
                        "Tu mantención número " + cliente.getMantenciones() + " del mes se realizó con éxito.\n" +
                        "Fecha: " + formattedDate + ". Hora: " + formattedTime + ".\n\n" +
                        "Descripción de la última mantención: " + cliente.getDescripcion() + "\n\n" +
                        "El monto total a cancelar este mes es de $" + totalEntero + ".\n\n");

                if (cliente.getFotoMantencion() != null && !cliente.getFotoMantencion().isEmpty()) {
                    try {
                        String base64Image = cliente.getFotoMantencion().split(",")[1];
                        byte[] fotoBytes = Base64.getDecoder().decode(base64Image);

                        emailService.sendEmailWithAttachment(
                                cliente.getEmail(),
                                subject,
                                message.toString(),
                                fotoBytes,
                                "foto-mantencion.jpg"
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                        emailService.sendEmail(cliente.getEmail(), subject, message.toString());
                    }
                } else {
                    emailService.sendEmail(cliente.getEmail(), subject, message.toString());
                }

                cliente.setMantenciones(0);
                cliente.setTotal(0);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Correo de resumen enviado automáticamente y datos reiniciados.");
                return ResponseEntity.ok(response);
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Aún no se alcanza el límite para cerrar el mes.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al verificar las mantenciones.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}