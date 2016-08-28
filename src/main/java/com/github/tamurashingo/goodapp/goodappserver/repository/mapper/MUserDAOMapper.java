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
package com.github.tamurashingo.goodapp.goodappserver.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.github.tamurashingo.dbutils3.BeanBuilder;
import com.github.tamurashingo.dbutils3.BeanBuilderException;
import com.github.tamurashingo.goodapp.goodappserver.bean.MUserBean;
import com.github.tamurashingo.goodapp.goodappserver.repository.MUserDAO;

/**
 * ユーザマスタのマッパー
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public class MUserDAOMapper {

    /**
     * {@link MUserDAO#getLatestMUserBean(String)}用のマッパー
     *
     * @author tamura shingo (tamura.shingo at gmail.com)
     *
     */
    public static class GetLatestMUserBeanMapper implements ResultSetMapper<MUserBean> {
        private static final BeanBuilder builder;
        static {
            builder = new BeanBuilder(MUserBean.class);
        }
        @Override
        public MUserBean map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            try {
                MUserBean bean = builder.build(r);
                return bean;
            }
            catch (BeanBuilderException ex) {
                throw new SQLException(ex);
            }
        }
    }
}
