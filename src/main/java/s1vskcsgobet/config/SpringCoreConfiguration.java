package s1vskcsgobet.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "s1vskcsgobet")
@PropertySource(value = "classpath:application.properties")
@EnableTransactionManagement
@EntityScan(basePackages = "s1vskcsgobet.core.domain")
@EnableJpaRepositories(value = "s1vskcsgobet.core.database")
public class SpringCoreConfiguration {

    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/db/changelog/changelog-master.xml");
        liquibase.setShouldRun(false);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}