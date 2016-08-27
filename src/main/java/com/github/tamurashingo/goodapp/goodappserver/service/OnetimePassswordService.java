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
package com.github.tamurashingo.goodapp.goodappserver.service;

import com.github.tamurashingo.goodapp.goodappserver.response.OnetimePasswordResponse.CheckOnetimePassword;
import com.github.tamurashingo.goodapp.goodappserver.response.OnetimePasswordResponse.GenerateOnetimePassword;

/**
 * ワンタイムパスワード関連のサービス
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public interface OnetimePassswordService {

    /**
     * ワンタイムパスワードを生成し、登録済みのメールアドレスに送信する。
     *
     * @param userId 対象のユーザID
     * @return ワンタイムパスワード生成結果
     */
    public GenerateOnetimePassword generateOnetimePassword(String userId);

    /**
     * ユーザが入力したPIN + ワンタイムパスワードがあっているかをチェックする。
     *
     * @param userId
     * @param password
     * @return パスワードチェック結果
     */
    public CheckOnetimePassword checkOnetimePassword(String userId, String password);

}
