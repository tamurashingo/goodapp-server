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
package com.github.tamurashingo.goodapp.goodappserver.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.tamurashingo.goodapp.goodappserver.response.OnetimePasswordResponse;
import com.github.tamurashingo.goodapp.goodappserver.response.OnetimePasswordResponse.CheckOnetimePassword;
import com.github.tamurashingo.goodapp.goodappserver.service.OnetimePassswordService;
import com.google.inject.Inject;

/**
 * ワンタイムパスワードAPI
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
@Path("/otp/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class OnetimePasswordResource {

    private OnetimePassswordService  onetimePasswordService;
    @Inject
    public void setOnetimePasswordService(OnetimePassswordService onetimePasswordService) {
        this.onetimePasswordService = onetimePasswordService;
    }

    /**
     * 指定したユーザのワンタイムパスワードを発行する。
     * 以前に発行したワンタイムパスワードは無効化する。
     *
     * @param id ユーザID
     * @return
     */
    @GET
    public OnetimePasswordResponse.GenerateOnetimePassword generateOnetimePassword(@PathParam("id") String id) {
        return onetimePasswordService.generateOnetimePassword(id);
    }

    /**
     * パスワードチェックを行う。
     *
     * @param id ユーザID
     * @param password パスワード(PIN + ワンタイムパスワード)
     * @return
     */
    @POST
    public CheckOnetimePassword checkOnetimePassword(@PathParam("id") String id, @FormParam("password") String password) {
        return onetimePasswordService.checkOnetimePassword(id, password);
    }


}
