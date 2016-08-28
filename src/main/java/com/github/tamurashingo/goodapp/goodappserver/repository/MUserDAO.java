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
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;

import com.github.tamurashingo.goodapp.goodappserver.bean.MUserBean;
import com.github.tamurashingo.goodapp.goodappserver.repository.mapper.MUserDAOMapper;
import com.google.common.base.Optional;

/**
 * ユーザマスタDAO
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public abstract class MUserDAO extends GoodappRepository {

    /**
     * 指定したユーザの最新の有効なレコードを取得する。
     * 
     * @param userId ユーザID
     * @return ユーザ情報
     */
    @SingleValueResult(MUserBean.class)
    @SqlQuery(
              " select "
            + "   id, "
            + "   user_id, "
            + "   name, "
            + "   email, "
            + "   pin, "
            + "   invalid_flag "
            + " from "
            + "   m_user "
            + " where "
            + "   id = ( "
            + "     select "
            + "       max(id) "
            + "     from "
            + "       m_user "
            + "     where "
            + "       user_id = :userId "
            + "     and "
            + "       invalid_flag = '0' "
            + "   ) "
            )
    @Mapper(MUserDAOMapper.GetLatestMUserBeanMapper.class)
    public abstract Optional<MUserBean> getLatestMUserBean(@Bind("userId") String userId);
}
