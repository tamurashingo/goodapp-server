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

/**
 * GoodappServerで発生する例外の基底クラス
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public class GoodappServerException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * construct a new {@code GoodappServerException}
     * with the specified short message.
     *
     * @param message the short message
     */
    public GoodappServerException(String message) {
        super(message);
    }

    /**
     * construct a new {@code GoodappServerException}
     * with the specified short message and cause.
     *
     * @param message the short message
     * @param cause the cause
     */
    public GoodappServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * construct a new {@code GoodappServerException}
     * with the specified cause.
     *
     * @param cause the cause
     */
    public GoodappServerException(Throwable cause) {
        super(cause);
    }
}
