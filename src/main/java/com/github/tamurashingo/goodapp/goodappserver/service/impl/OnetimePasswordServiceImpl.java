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
package com.github.tamurashingo.goodapp.goodappserver.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.skife.jdbi.v2.Transaction;
import org.skife.jdbi.v2.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tamurashingo.goodapp.goodappserver.GoodappServerConstants;
import com.github.tamurashingo.goodapp.goodappserver.bean.MUserBean;
import com.github.tamurashingo.goodapp.goodappserver.mail.MailUtil;
import com.github.tamurashingo.goodapp.goodappserver.repository.GoodappRepository;
import com.github.tamurashingo.goodapp.goodappserver.repository.MUserDAO;
import com.github.tamurashingo.goodapp.goodappserver.repository.OnetimePasswordDAO;
import com.github.tamurashingo.goodapp.goodappserver.response.OnetimePasswordResponse.CheckOnetimePassword;
import com.github.tamurashingo.goodapp.goodappserver.response.OnetimePasswordResponse.GenerateOnetimePassword;
import com.github.tamurashingo.goodapp.goodappserver.service.OnetimePassswordService;
import com.github.tamurashingo.goodapp.goodappserver.session.SessionUtil;
import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * {@link OnetimePassswordService}の実装クラス
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public class OnetimePasswordServiceImpl implements OnetimePassswordService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OnetimePasswordServiceImpl.class);
    private GoodappRepository repository;

    @Inject
    public void setGoodappRepository(GoodappRepository repository) {
        this.repository = repository;
    }

    @Override
    public GenerateOnetimePassword generateOnetimePassword(String userId) {
        GenerateOnetimePassword result = repository.inTransaction(new Transaction<GenerateOnetimePassword, GoodappRepository>() {
            @Override
            public GenerateOnetimePassword inTransaction(GoodappRepository transactional, TransactionStatus status) throws Exception {
                GenerateOnetimePassword response = new GenerateOnetimePassword();
                try {
                    transactional.begin();
                    OnetimePasswordDAO dao = transactional.createOnetimePasswordDAO();
                    MUserDAO muserDAO = transactional.createMUserDAO();
                    Optional<MUserBean> bean = muserDAO.getLatestMUserBean(userId);
                    if (bean.isPresent()) {
                        MUserBean muserBean = bean.get();
                        dao.invalidateOldOTP(userId);
                        String otp = genRand();
                        dao.createOTP(userId, otp);
                        MailUtil mail = MailUtil.getInstance();
                        mail.sendMail(muserBean.getEmail(), muserBean.getUserId(), otp);
                        response.setResult(GoodappServerConstants.Response.SUCCESS);
                        response.setMessage("ワンタイムパスワードをメールで送りました");
                        LOGGER.info("otpgen:user={}", userId);
                    }
                    else {
                        throw new Exception("有効なユーザが登録されていません。userid=" + userId);
                    }
                }
                catch (Exception ex) {
                    LOGGER.error("otpgen error:user={}", ex);
                    response.setResult(GoodappServerConstants.Response.FAILURE);
                    response.setMessage("ワンタイムパスワードの生成に失敗しました");
                }
                return response;
            }
        });

        return result;
    }


    private String genRand() {
        int r = ThreadLocalRandom.current().nextInt(0, 10000);
        return String.format("%04d", r);
    }

    @Override
    public CheckOnetimePassword checkOnetimePassword(String userId, String password) {
        CheckOnetimePassword response = repository.inTransaction(new Transaction<CheckOnetimePassword, GoodappRepository>() {

            @Override
            public CheckOnetimePassword inTransaction(GoodappRepository transactional, TransactionStatus status) throws Exception {
                CheckOnetimePassword response = new CheckOnetimePassword();
                try {
                    transactional.begin();
                    OnetimePasswordDAO dao = transactional.createOnetimePasswordDAO();
                    int cnt = dao.checkPassword(userId, password);
                    if (cnt == 1) {
                        LOGGER.warn("LOGIN OK user={}", userId);
                        dao.login(userId);
                        response.setResult(GoodappServerConstants.Response.SUCCESS);
                        response.setSession(SessionUtil.getSession(userId));
                        response.setMessage("ログインしました");
                    }
                    else {
                        LOGGER.warn("LOGIN ERROR user={}", userId);
                        dao.updateErrorCount(userId);
                        response.setResult(GoodappServerConstants.Response.FAILURE);
                        response.setMessage("ユーザIDまたはパスワードが違います");
                    }
                    transactional.commit();
                }
                catch (Exception ex) {
                    transactional.rollback();
                    LOGGER.error("ログイン処理", ex);
                    response.setResult(GoodappServerConstants.Response.FAILURE);
                    response.setMessage("システムエラーです。しばらくお待ちください");
                }
                return response;
            }
        });
        return response;
    }
}
