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
package com.github.tamurashingo.goodapp.goodappserver.repository;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * ワンタイムパスワードテーブルDAO
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public abstract class OnetimePasswordDAO extends GoodappRepository {

    /**
     * 指定したユーザのワンタイムパスワードをすべて無効にする
     *
     * @param userId ユーザID
     * @return 更新レコード件数
     */
    @SqlUpdate(
              " update "
            + "   t_otp "
            + " set "
            + "   invalid_flag = '1', "
            + "   update_date = now() "
            + " where "
            + "   invalid_flag = '0' "
            + " and "
            + "   user_id = :user_id "
            )
    public abstract int invalidateOldOTP(@Bind("user_id") String userId);

    /**
     * ワンタイムパスワードを登録する
     *
     * @param userId ユーザID
     * @param otp ワンタイムパスワード
     * @return 登録レコード件数
     */
    @SqlUpdate(
              " insert into "
            + "   t_otp "
            + " ( "
            + "   user_id, "
            + "   otp, "
            + "   invalid_flag, "
            + "   error_count, "
            + "   create_date, "
            + "   update_date, "
            + "   error_date, "
            + "   login_date "
            + " ) "
            + " values ( "
            + "   :user_id, "
            + "   :otp, "
            + "   0, "
            + "   0, "
            + "   now(), "
            + "   now(), "
            + "   null, "
            + "   null "
            + " )"
            )
    public abstract int createOTP(@Bind("user_id") String userId, @Bind("otp") String otp);

    /**
     * ユーザが入力したパスワードのチェックを行う。
     * パスワードチェックは30分以内に発行されたワンタイムパスワードが対象となる。
     * パスワードはPIN+ワンタイムパスワード。
     *
     * @param userId ユーザID
     * @param password パスワード
     * @return
     */
    @SqlQuery(
              " select "
            + "   count(*) "
            + " from "
            + "   t_otp otp, "
            + "   m_user user "
            + " where "
            + "   otp.user_id = user.user_id "
            + " and "
            + "   otp.create_date > CURRENT_TIMESTAMP + INTERVAL -30 MINUTE "
            + " and "
            + "   otp.invalid_flag = '0' "
            + " and "
            + "   user.invalid_flag = '0' "
            + " and "
            + "   otp.error_count < 3 "
            + " and "
            + "   concat(user.pin, otp.otp) = :password "
            + " and "
            + "   user.user_id = :user_id "
            )
    public abstract int checkPassword(@Bind("user_id") String userId, @Bind("password") String password);

    /**
     * エラーカウントをインクリメントする
     *
     * @param userId ユーザID
     * @return 更新レコード件数
     */
    @SqlUpdate(
              " update "
            + "   t_otp "
            + " set "
            + "   error_count = error_count + 1, "
            + "   update_date = now(), "
            + "   error_date = now() "
            + " where "
            + "   user_id = :user_id "
            + " and "
            + "   invalid_flag = '0' "
            )
    public abstract int updateErrorCount(@Bind("user_id") String userId);


    /**
     * ログインしたのでワンタイムパスワードを無効化する
     * 
     * @param userId ユーザID
     * @return 更新レコード件数
     */
    @SqlUpdate(
              " update "
            + "   t_otp "
            + " set "
            + "   invalid_flag = '1', "
            + "   update_date = now(), "
            + "   login_date = now() "
            + " where "
            + "   user_id = :user_id "
            )
    public abstract int login(@Bind("user_id") String userId);
}
