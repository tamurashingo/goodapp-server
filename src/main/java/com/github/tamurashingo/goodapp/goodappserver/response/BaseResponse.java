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
package com.github.tamurashingo.goodapp.goodappserver.response;

/**
 * フロントへのレスポンスに必ず含まれる情報をまとめたBean.
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 * @since 0.0.1
 */
public class BaseResponse implements java.io.Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * APIの実行結果。
     * 成功の場合は"SUCCESS"、失敗の場合は"FAILURE"が入る。
     */
    protected String result;
    /**
     * APIの実行結果のメッセージ。
     * 基本的にユーザに見せるために画面に表示することを想定している。
     */
    protected String message;
    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
