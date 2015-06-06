package io.hsiao.devops.clib.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

public final class MailTest {
  @Before
  public void start() {
    greenMail.start();
  }

  @Test
  public void send() throws java.lang.Exception {
    final Mail mail = new Mail(Mail.getProperties(smtpHost, smtpPort));

    final String fromAddress = Mail.getMailAddress("from", domain);
    mail.setFrom(fromAddress);

    final String subject = "Yet Another Testing Mail";
    mail.setSubject(subject, null);

    final String content = "Dear Mail Crawler" + "<br><br>" + "No spam to my email, please!";
    final String contentType = "text/html; charset=utf-8";
    mail.setContent(content, contentType);

    final String toAddress01 = "to-01";
    final String toAddress02 = Mail.getMailAddress("to-02", domain);
    final String toAddress03 = Mail.getMailAddress("to-03", domain);
    final String ccAddress = "cc";
    mail.setRecipients(Mail.RecipientTypeTO, Arrays.asList(toAddress01, toAddress02), domain);
    mail.setRecipients(Mail.RecipientTypeCC, Arrays.asList(ccAddress), domain);
    mail.addRecipient(Mail.RecipientTypeTO, toAddress03);

    final Date date = new Date();
    mail.setSentDate(date);

    mail.send(null, null);

    final Message message = greenMail.getReceivedMessages()[0];

    assertEquals(message.getFrom()[0].toString(), fromAddress);
    assertEquals(message.getSubject(), subject);

    assertTrue(message.getContent() instanceof MimeMultipart);
    final Multipart multipart = (MimeMultipart) message.getContent();
    assertEquals(multipart.getCount(), 1);
    assertEquals(multipart.getBodyPart(0).getContent(), content);
    assertEquals(multipart.getBodyPart(0).getContentType(), contentType);

    assertEquals(message.getRecipients(RecipientType.TO).length, 3);
    assertEquals(message.getRecipients(RecipientType.TO)[0].toString(), Mail.getMailAddress(toAddress01, domain));
    assertEquals(message.getRecipients(RecipientType.TO)[1].toString(), toAddress02);
    assertEquals(message.getRecipients(RecipientType.TO)[2].toString(), toAddress03);

    assertEquals(message.getRecipients(RecipientType.CC).length, 1);
    assertEquals(message.getRecipients(RecipientType.CC)[0].toString(), Mail.getMailAddress(ccAddress, domain));

    final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    assertEquals(formatter.format(message.getSentDate()), formatter.format(date));
  }

  @After
  public void stop() {
    greenMail.stop();
  }

  private static final GreenMail greenMail = new GreenMail(ServerSetupTest.SMTP);

  private static final String domain = "hsiao.io";
  private static final String smtpHost = "localhost";
  private static final String smtpPort = "3025";
}
