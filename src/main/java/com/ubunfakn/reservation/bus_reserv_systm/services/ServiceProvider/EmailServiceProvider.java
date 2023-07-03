package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServiceProvider {
    
    public static void sendEmail(String message,String subject, String to) throws Exception{
        String host = "smtp.googlemail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("ankitnashine12@gmail.com", "liywpprgdugiybxx");
            }
        });
        session.setDebug(true);

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom("ankitnashine12@gmail.com");
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);

        Transport.send(mimeMessage);
        System.out.println("Email sent Successfully");
    }
}
