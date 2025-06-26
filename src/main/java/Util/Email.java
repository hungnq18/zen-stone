/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class Email {

    static final String username = "ZENSTONE";
    static final String from = "linhnthhe176264@fpt.edu.vn";
    static final String password = "bnbzquklopyllqia";
    
    public static void sendEmailConfirm(String toEmail, String subject, String body) throws MessagingException {
        final String fromEmail = "linhnthhe176264@fpt.edu.vn"; // email bạn dùng gửi
        final String password = "bnbzquklopyllqia"; // mật khẩu ứng dụng hoặc mật khẩu thật

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server Gmail
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
    
    public boolean sendEmail(String to, String title, String content, String reply) {
        // Properties : khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            // Người gửi
            msg.setFrom(new InternetAddress(from, username));
            // Người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            // Tiêu đề email
            msg.setSubject(MimeUtility.encodeText(title, "UTF-8", "B"));
            msg.setHeader("Content-type", "text/HTML; charset=UTF-8");
            // Quy đinh ngày gửi
            msg.setSentDate(new Date());
            // Quy định email nhận phản hồi
            if (reply != null) {
                msg.setReplyTo(new InternetAddress[]{new InternetAddress(reply)});
            }
            // Nội dung
            msg.setContent(content, "text/HTML; charset=UTF-8");
            // Gửi email
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean sendEmailWithPDFAttachment(String to, String title, File pdfFilePath, String reply) {
        // Setup email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Email authentication
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);

        try {
            // Prepare HTML content for email body
            String htmlBody = "Send your bill";

            // Attach PDF file
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlBody, "text/html; charset=UTF-8");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(pdfFilePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("invoice.pdf");

            // Create Multipart email message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Set email properties
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(new InternetAddress(from, username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(MimeUtility.encodeText(title, "UTF-8", "B"));
            msg.setSentDate(new Date());
            msg.setContent(multipart);

            if (reply != null) {
                msg.setReplyTo(new InternetAddress[]{new InternetAddress(reply)});
            }

            Transport.send(msg);

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
