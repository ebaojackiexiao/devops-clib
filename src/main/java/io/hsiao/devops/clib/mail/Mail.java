package io.hsiao.devops.clib.mail;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public final class Mail {
  public Mail(final Properties props) {
    if (props == null) {
      throw new RuntimeException("argument 'props' is null");
    }

    session = Session.getInstance(props, null);
    message = new MimeMessage(session);
    messageBodyPart = new MimeBodyPart();
    attachsBodyPart = new LinkedList<>();
  }

  public static Properties getProperties(final String smtpHost, final String smtpPort) {
    if (smtpHost == null) {
      throw new RuntimeException("argument 'smtpHost' is null");
    }

    if (smtpPort == null) {
      throw new RuntimeException("argument 'smtpPort' is null");
    }

    final Properties props = new Properties();

    props.put("mail.smtp.host", smtpHost);
    props.put("mail.smtp.port", smtpPort);

    return props;
  }

  public void setContent(final Object object, final String type) throws Exception {
    if (object == null) {
      throw new RuntimeException("argument 'object' is null");
    }

    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    try {
      messageBodyPart.setContent(object, type);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message content [" + object + "] [" + type + "]", ex);
      logger.log(Level.INFO, "failed to set message content [" + object + "] [" + type + "]", exception);
      throw exception;
    }
  }

  public void setFrom(final String address) throws Exception {
    try {
      message.setFrom(address);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message from address [" + address + "]", ex);
      logger.log(Level.INFO, "failed to set message from address [" + address + "]", exception);
      throw exception;
    }
  }

  public void addRecipient(final String type, final String address) throws Exception {
    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    if (address == null) {
      throw new RuntimeException("argument 'address' is null");
    }

    final Message.RecipientType recipientType;
    try {
      recipientType = (Message.RecipientType) Message.RecipientType.class.getField(type).get(null);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("invalid message recipient type [" + type + "]", ex);
      logger.log(Level.INFO, "invalid message recipient type [" + type + "]", exception);
      throw exception;
    }

    try {
      final Address internetAddress = new InternetAddress(address, true);
      message.addRecipient(recipientType, internetAddress);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to add message recipient [" + type + "] [" + address + "]", ex);
      logger.log(Level.INFO, "failed to add message recipient [" + type + "] [" + address + "]", exception);
      throw exception;
    }
  }

  public void setRecipients(final String type, final String addresses) throws Exception {
    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    final Message.RecipientType recipientType;
    try {
      recipientType = (Message.RecipientType) Message.RecipientType.class.getField(type).get(null);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("invalid message recipient type [" + type + "]", ex);
      logger.log(Level.INFO, "invalid message recipient type [" + type + "]", exception);
      throw exception;
    }

    try {
      message.setRecipients(recipientType, addresses);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message recipients [" + type + "] [" + addresses + "]", ex);
      logger.log(Level.INFO, "failed to set message recipients [" + type + "] [" + addresses + "]", exception);
      throw exception;
    }
  }

  public void setRecipients(final String type, final List<String> addresses) throws Exception {
    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    final Message.RecipientType recipientType;
    try {
      recipientType = (Message.RecipientType) Message.RecipientType.class.getField(type).get(null);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("invalid message recipient type [" + type + "]", ex);
      logger.log(Level.INFO, "invalid message recipient type [" + type + "]", exception);
      throw exception;
    }

    final String recipientAddresses;
    if (addresses == null) {
      recipientAddresses = null;
    }
    else {
      final StringBuilder sb = new StringBuilder();

      for (String address: addresses) {
        address = address.trim();
        if (!address.isEmpty()) {
          sb.append(address);
          sb.append(",");
        }
      }

      recipientAddresses = sb.toString();
    }

    try {
      message.setRecipients(recipientType, recipientAddresses);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message recipients [" + type + "] [" + addresses + "]", ex);
      logger.log(Level.INFO, "failed to set message recipients [" + type + "] [" + addresses + "]", exception);
      throw exception;
    }
  }

  public void setRecipients(final String type, final List<String> addresses, final String domain) throws Exception {
    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    final Message.RecipientType recipientType;
    try {
      recipientType = (Message.RecipientType) Message.RecipientType.class.getField(type).get(null);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("invalid message recipient type [" + type + "]", ex);
      logger.log(Level.INFO, "invalid message recipient type [" + type + "]", exception);
      throw exception;
    }

    final String recipientAddresses;
    if (addresses == null) {
      recipientAddresses = null;
    }
    else {
      final StringBuilder sb = new StringBuilder();

      for (String address: addresses) {
        address = address.trim();
        if (address.isEmpty()) {
          continue;
        }

        if (!address.contains("@")) {
          if (domain == null) {
            throw new RuntimeException("argument 'domain' is null");
          }
          sb.append(address + "@" + domain);
        }
        else {
          sb.append(address);
        }

        sb.append(",");
      }

      recipientAddresses = sb.toString();
    }

    try {
      message.setRecipients(recipientType, recipientAddresses);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message recipients [" + type + "] [" + addresses + "] [" + domain + "]", ex);
      logger.log(Level.INFO, "failed to set message recipients [" + type + "] [" + addresses + "] [" + domain + "]", exception);
      throw exception;
    }
  }

  public void setSentDate(final Date date) throws Exception {
    try {
      message.setSentDate(date);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message sent date [" + date + "]", ex);
      logger.log(Level.INFO, "failed to set message sent date [" + date + "]", exception);
      throw exception;
    }
  }

  public void setSubject(final String subject, final String charset) throws Exception {
    try {
      message.setSubject(subject, charset);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message subject [" + subject + "] [" + charset + "]", ex);
      logger.log(Level.INFO, "failed to set message subject [" + subject + "] [" + charset + "]", exception);
      throw exception;
    }
  }

  public void attachFile(final String file) throws Exception {
    if (file == null) {
      throw new RuntimeException("argument 'file' is null");
    }

    try {
      final MimeBodyPart attachBodyPart = new MimeBodyPart();
      attachBodyPart.attachFile(file);
      attachsBodyPart.add(attachBodyPart);
    }
    catch (IOException | MessagingException ex) {
      final Exception exception = new Exception("failed to attach file [" + file + "]", ex);
      logger.log(Level.INFO, "failed to attach file [" + file + "]", exception);
      throw exception;
    }
  }

  public void send(final String username, final String password) throws Exception {
    try {
      final Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      for (final MimeBodyPart attachBodyPart: attachsBodyPart) {
        multipart.addBodyPart(attachBodyPart);
      }

      message.setContent(multipart);

      Transport.send(message, username, password);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to send message", ex);
      logger.log(Level.INFO, "failed to send message", exception);
      throw exception;
    }
  }

  public static String getMailAddress(final String local, final String domain) {
    return String.format("%s@%s", local, domain);
  }

  private static final Logger logger = LoggerFactory.getLogger(Mail.class);

  private final Session session;
  private final MimeMessage message;
  private final MimeBodyPart messageBodyPart;
  private final List<MimeBodyPart> attachsBodyPart;

  public static final String RecipientTypeBCC = "BCC";
  public static final String RecipientTypeCC = "CC";
  public static final String RecipientTypeTO = "TO";
}
