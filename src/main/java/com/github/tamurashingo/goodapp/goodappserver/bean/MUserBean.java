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
package com.github.tamurashingo.goodapp.goodappserver.bean;

import com.github.tamurashingo.dbutils3.Column;

import lombok.Data;

/**
 * ユーザマスタ情報
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
@Data
public class MUserBean implements java.io.Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    @Column("id")
    private String id;
    @Column("user_id")
    private String userId;
    @Column("name")
    private String name;
    @Column("email")
    private String email;
    @Column("pin")
    private String pin;
    @Column("invalid_flag")
    private String invalidFlag;
}
