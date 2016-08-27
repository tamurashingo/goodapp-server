/*-
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 tamura shingo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.tamurashingo.goodapp.goodappserver.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tamurashingo.goodapp.goodappserver.GoodappServerConfiguration.MailProperties;

/**
 * メール送信ユーティリティ
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public class MailUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

    private static MailProperties prop;

    public static void init(MailProperties prop) {
        MailUtil.prop = prop;
        LOGGER.info("メールサーバ ホスト名:{}", prop.getHost());
        LOGGER.info("メールサーバ ポート番号:{}", prop.getPort());
        if (prop.getGmail() == null) {
            LOGGER.info("gmailなし");
        }
        else {
            LOGGER.info("gmailあり");
        }
    }

    public static MailUtil getInstance() {
        return new MailUtil();
    }

    private MailUtil() {
    }

    public void sendMail(String toAddress, String userId, String password) throws MailUtilException {
        LOGGER.info("sendMail:開始");
        String subject = createSubject(userId, password);
        String content = createContent(userId, password);

        try {
            createMail(toAddress, subject, content);
            Transport.send(msg);
        }
        catch (AddressException ex) {
            throw new MailUtilException("送信先アドレスが不正です", ex);
        }
        catch (MessagingException ex) {
            throw new MailUtilException("メール送信に失敗しました", ex);
        }
        LOGGER.info("sendMail:終了");
    }

    private String createSubject(String userId, String password) {
        return String.format(prop.getSubject(), userId, password);
    }

    private String createContent(String userId, String password) {
        return String.format(prop.getContent(), userId, password);
    }


    private MimeMessage msg;

    private void createMail(String toAddress, String subject, String content) throws AddressException, MessagingException {
        Properties mail = new Properties();
        mail.put("mail.smtp.host", prop.getHost());
        mail.put("mail.smtp.port", prop.getPort());
        Session session = Session.getInstance(mail, createAuthenticator());
        this.msg = new MimeMessage(session);

        setFromAddress(prop.getFromAddress());
        setToAddress(toAddress);
        setSentDate(new Date());
        setSubject(subject);
        setContent(content);
    }

    private Authenticator createAuthenticator() {
        if (prop.getGmail() == null) {
            return null;
        }
        else {
            return new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(prop.getGmail().getUsername(), prop.getGmail().getPassword());
                }
            };
        }
    }


    private MimeMessage setFromAddress(String fromAddress) throws AddressException, MessagingException {
        this.msg.setFrom(new InternetAddress(fromAddress));
        return msg;
    }

    private MimeMessage setToAddress(String toAddress) throws MessagingException {
        this.msg.setRecipients(Message.RecipientType.TO, toAddress);
        return msg;
    }

    private MimeMessage setSentDate(Date sentDate) throws MessagingException {
        this.msg.setSentDate(sentDate);
        return msg;
    }

    private MimeMessage setSubject(String subject) throws MessagingException {
        this.msg.setSubject(subject);
        return msg;
    }

    private MimeMessage setContent(String content) throws MessagingException {
        this.msg.setText(content);
        return msg;
    }

}
