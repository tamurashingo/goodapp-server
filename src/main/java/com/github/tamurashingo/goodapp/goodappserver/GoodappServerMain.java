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

import org.skife.jdbi.v2.DBI;

import com.github.tamurashingo.goodapp.goodappserver.mail.MailUtil;
import com.github.tamurashingo.goodapp.goodappserver.repository.GoodappRepository;
import com.github.tamurashingo.goodapp.goodappserver.resource.OnetimePasswordResource;
import com.github.tamurashingo.goodapp.goodappserver.service.OnetimePassswordService;
import com.github.tamurashingo.goodapp.goodappserver.service.impl.OnetimePasswordServiceImpl;
import com.github.tamurashingo.goodapp.goodappserver.session.SessionUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Goodapp Server.
 *
 * @author tamura shingo (tamura.shingo at gmail.com)
 *
 */
public class GoodappServerMain extends Application<GoodappServerConfiguration> {

    public static void main(String...args) throws Exception {
        new GoodappServerMain().run(args);
    }

    @Override
    public void initialize(Bootstrap<GoodappServerConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<GoodappServerConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(GoodappServerConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(GoodappServerConfiguration configuration, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "goodappdb");
        final GoodappRepository repository = jdbi.onDemand(GoodappRepository.class);

        MailUtil.init(configuration.getMailProperties());
        SessionUtil.init(configuration.getSessionProperties());

        Injector injector = createInjector(repository);
        environment.jersey().register(injector.getInstance(OnetimePasswordResource.class));
    }

    /*-
     * サービス追加時はここにinterfaceとimplement classを追加する
     */
    private Injector createInjector(GoodappRepository repository) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(OnetimePassswordService.class).to(OnetimePasswordServiceImpl.class);
                bind(GoodappRepository.class).toInstance(repository);
            }
        });
    }



}
