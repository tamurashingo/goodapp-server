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
package com.github.tamurashingo.goodapp.goodappserver;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 * GoodappServer configuration
 * 
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public class GoodappServerConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database;

    @Valid
    @NotNull
    private MailProperties mailProperties;
    
    @Valid
    @NotNull
    private SessionProperties sessionProperties;

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return this.database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory database) {
        this.database = database;
    }

    @JsonProperty("mail")
    public MailProperties getMailProperties() {
        return this.mailProperties;
    }

    @JsonProperty("mail")
    public void setMailProperties(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    @JsonProperty("session")
    public SessionProperties getSessionProperties() {
        return this.sessionProperties;
    }

    @JsonProperty("session")
    public void setSessionProperties(SessionProperties sessionProperties) {
        this.sessionProperties = sessionProperties;
    }

    /**
     * メール送信用の情報
     *
     * @author tamura shingo (tamura.shingo at gmail.com)
     */
    public static class MailProperties implements java.io.Serializable {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;
        /** メールサーバのホスト名 */
        private String host;
        /** メールサーバのポート番号 */
        private String port;
        /** メールの送信元アドレス */
        private String fromAddress;
        /** メール件名のフォーマット */
        private String subject;
        /** メール本文のフォーマット */
        private String content;
        @Valid
        private GmailProperties gmail;

        /**
         * SMTPにGMAILを使う際の設定情報
         *
         * @author tamura shingo (tamura.shingo at gmail.com)
         *
         */
        public static class GmailProperties implements java.io.Serializable {
            /** serialVersionUID */
            private static final long serialVersionUID = 1L;
            /** gmailユーザ名 */
            @NotNull
            private String username;
            /** mailパスワード */
            @NotNull
            private String password;
            /**
             * @return the username
             */
            public String getUsername() {
                return username;
            }
            /**
             * @param username the username to set
             */
            public void setUsername(String username) {
                this.username = username;
            }
            /**
             * @return the password
             */
            public String getPassword() {
                return password;
            }
            /**
             * @param password the password to set
             */
            public void setPassword(String password) {
                this.password = password;
            }
        }

        /**
         * @return the host
         */
        public String getHost() {
            return host;
        }
        /**
         * @param host the host to set
         */
        public void setHost(String host) {
            this.host = host;
        }
        /**
         * @return the port
         */
        public String getPort() {
            return port;
        }
        /**
         * @param port the port to set
         */
        public void setPort(String port) {
            this.port = port;
        }
        /**
         * @return the fromAddress
         */
        public String getFromAddress() {
            return fromAddress;
        }
        /**
         * @param fromAddress the fromAddress to set
         */
        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }
        /**
         * @return the subject
         */
        public String getSubject() {
            return subject;
        }
        /**
         * @param subject the subject to set
         */
        public void setSubject(String subject) {
            this.subject = subject;
        }
        /**
         * @return the content
         */
        public String getContent() {
            return content;
        }
        /**
         * @param content the content to set
         */
        public void setContent(String content) {
            this.content = content;
        }
        /**
         * @return the gmail
         */
        @JsonProperty("gmail")
        public GmailProperties getGmail() {
            return gmail;
        }
        /**
         * @param gmail the gmail to set
         */
        public void setGmail(GmailProperties gmail) {
            this.gmail = gmail;
        }
    }

    /**
     * セッション用の情報
     *
     * @author tamura shingo (tamura.shingo at gmail.com)
     */
    public static class SessionProperties implements java.io.Serializable {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;
        /** 暗号化に使用するパスワード */
        private String password;
        /** セッションタイムアウト時間(分) */
        private Long timeout;
        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }
        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }
        /**
         * @return the timeout
         */
        public Long getTimeout() {
            return timeout;
        }
        /**
         * @param timeout the timeout to set
         */
        public void setTimeout(Long timeout) {
            this.timeout = timeout;
        }
    }
}
