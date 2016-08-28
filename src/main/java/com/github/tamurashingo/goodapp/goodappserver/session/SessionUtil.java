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
package com.github.tamurashingo.goodapp.goodappserver.session;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.github.tamurashingo.goodapp.goodappserver.GoodappServerConfiguration.SessionProperties;

/**
 * セッション情報ユーティリティ
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public class SessionUtil {

    /** セッション情報暗号化用パスワード */
    private static String password;
    /** タイムアウト分 */
    private static Long timeout;
    
    public static void init(SessionProperties sessionProperties) {
        SessionUtil.password = sessionProperties.getPassword();
        SessionUtil.timeout = sessionProperties.getTimeout();
    }

    /**
     * セッション情報を生成する
     *
     * @param userId ユーザID
     * @return セッション情報
     */
    public static String getSession(String userId) {
        return Jwts.builder()
            .setExpiration(getExpire())
            .setSubject(userId)
            .signWith(SignatureAlgorithm.HS512, password)
            .compact();
    }

    /**
     * 現在時刻を基準にしたタイムアウト日時を取得する
     *
     * @return タイムアウト日時
     */
    public static Date getExpire() {
        return getExpire(LocalDateTime.now());
    }

    /**
     * 指定日時を基準にしたタイムアウト日時を取得する
     *
     * @param ldt 指定日時
     * @return タイムアウト日時
     */
    public static Date getExpire(LocalDateTime ldt) {
        return Date.from(ldt.plusMinutes(timeout).atZone(ZoneId.systemDefault()).toInstant()); 
    }
}
