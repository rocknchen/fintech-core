package com.hthk.common.internet.email.service.impl;

import com.hthk.common.exception.ServiceException;
import com.hthk.common.internet.email.service.EmailService;
import com.hthk.common.model.Internet.message.email.MessageEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
public class EmailServiceImpl implements EmailService {

    private final static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void send(MessageEmail msg) throws ServiceException {

        logger.info("send emails");
        try {
            Session session = buildSession();

            List<String> receiverList = msg.getReceiverList();
            List<String> ccList = msg.getCcList();
            String title = msg.getTitle();
            String content = msg.getContent();
            String sign = msg.getSign();
            List<String> attachmentList = msg.getAttachmentList();

            Message message = buildMessage(session, receiverList, ccList, title, content, sign, attachmentList);

            Transport.send(message);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    private Message buildMessage(Session session, List<String> receiverList, List<String> ccList, String title, String content, String sign, List<String> attachmentList) throws MessagingException, UnsupportedEncodingException {

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("rockchen@htsc.com"));

        List<InternetAddress> addressList = buildAddress(receiverList);
        InternetAddress[] addresses = new InternetAddress[addressList.size()];
        for (int i = 0; i < addressList.size(); i++) {
            addresses[i] = addressList.get(i);
        }
        message.setRecipients(Message.RecipientType.TO, addresses);

        List<InternetAddress> ccAddList = buildAddress(ccList);
        InternetAddress[] ccAdd = new InternetAddress[ccAddList.size()];
        for (int i = 0; i < ccAddList.size(); i++) {
            ccAdd[i] = ccAddList.get(i);
        }
        message.setRecipients(Message.RecipientType.CC, ccAdd);

        message.setSubject(title);

        String contentWithSign = content + Optional.ofNullable(sign).map(t -> "<br/>" + sign).orElse("");

        setContent(message, contentWithSign);
        setContentWithAttachmentList(message, contentWithSign, attachmentList);

        return message;
    }

    private void setContent(Message message, String content) throws MessagingException {
        message.setContent(content, "text/html;charset=utf-8");
    }

    private void setContentWithAttachmentList(Message message, String content, List<String> attachmentList) throws MessagingException, UnsupportedEncodingException {

        MimeMultipart mimeMultipart = new MimeMultipart("mixed");//指定为混合关系
        message.setContent(mimeMultipart);

        for (String attachment : attachmentList) {
            addFile(mimeMultipart, attachment);
        }

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(content, "text/html;charset=UTF-8");
        mimeMultipart.addBodyPart(htmlPart);
    }

    private void addFile(MimeMultipart mimeMultipart, String attachment) throws MessagingException, UnsupportedEncodingException {

        MimeBodyPart file = new MimeBodyPart();
        FileDataSource file_datasource = new FileDataSource(attachment);
        DataHandler dh = new DataHandler(file_datasource);
        file.setDataHandler(dh);
        file.setFileName(MimeUtility.encodeText(dh.getName()));
        mimeMultipart.addBodyPart(file);
    }

    private List<InternetAddress> buildAddress(List<String> receiverList) {
        if (receiverList == null) {
            return new ArrayList<>();
        }
        return receiverList.stream().map(t -> {
            try {
                return new InternetAddress(t);
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private Session buildSession() {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.smtp.host", "168.8.250.75");
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.auth", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rockchen@htsc.com", "zengqian827");
            }
        };

        Session session = Session.getInstance(props, auth);
        return session;
    }

}
