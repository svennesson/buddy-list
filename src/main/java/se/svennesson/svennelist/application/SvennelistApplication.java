package se.svennesson.svennelist.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import se.svennesson.svennelist.auth.BuddyListAuthenticator;
import se.svennesson.svennelist.auth.BuddyListAuthorizer;
import se.svennesson.svennelist.config.SvennelistConfiguration;
import se.svennesson.svennelist.dao.BuddyListDao;
import se.svennesson.svennelist.dao.ItemDao;
import se.svennesson.svennelist.dao.TokenDAO;
import se.svennesson.svennelist.dao.UserDao;
import se.svennesson.svennelist.model.BuddyList;
import se.svennesson.svennelist.model.Item;
import se.svennesson.svennelist.model.User;
import se.svennesson.svennelist.resources.BuddyListResource;
import se.svennesson.svennelist.resources.OAuth2Resource;
import se.svennesson.svennelist.resources.RegisterResource;
import se.svennesson.svennelist.resources.UserResource;
import se.svennesson.svennelist.service.BuddyListService;
import se.svennesson.svennelist.service.TokenService;
import se.svennesson.svennelist.service.UserService;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

public class SvennelistApplication extends Application<SvennelistConfiguration> {

    private final HibernateBundle<SvennelistConfiguration> hibernate = new HibernateBundle<SvennelistConfiguration>(User.class, BuddyList.class, Item.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(SvennelistConfiguration svennelistConfiguration) {
            return svennelistConfiguration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<SvennelistConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor()));
        bootstrap.addBundle(hibernate);
    }

    public static void main(String[] args) throws Exception{
        new SvennelistApplication().run(args);
    }

    @Override
    public void run(SvennelistConfiguration configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Flyway migrations
        DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSourceFactory.getUrl(), dataSourceFactory.getUser(), dataSourceFactory.getPassword());
        flyway.setBaselineOnMigrate(true);
        flyway.migrate();

        //CORS
        final FilterRegistration.Dynamic corsFilter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
        corsFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        corsFilter.setInitParameter(ALLOWED_METHODS_PARAM, "GET, PUT, POST, OPTIONS, DELETE, HEAD");
        corsFilter.setInitParameter(ALLOWED_ORIGINS_PARAM, "*");
        corsFilter.setInitParameter(ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization");
        corsFilter.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");

        //DAO
        final UserDao userDao = new UserDao(hibernate.getSessionFactory());
        final BuddyListDao buddyListDao = new BuddyListDao(hibernate.getSessionFactory());
        final ItemDao itemDao = new ItemDao(hibernate.getSessionFactory());
        final TokenDAO tokenDao = new TokenDAO(hibernate.getSessionFactory());

        //Service
        final UserService userService = new UserService(userDao);
        final BuddyListService buddyListService = new BuddyListService(buddyListDao, itemDao);
        final TokenService tokenService = new TokenService(tokenDao, userService);

        //Auth
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new BuddyListAuthenticator(userService, tokenService))
                .setAuthorizer(new BuddyListAuthorizer())
                .setPrefix("Bearer")
                .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        //Jersey
        environment.jersey().register(new UserResource(userService));
        environment.jersey().register(new BuddyListResource(buddyListService));
        environment.jersey().register(new RegisterResource(userService));
        environment.jersey().register(new OAuth2Resource(tokenService));

    }
}
