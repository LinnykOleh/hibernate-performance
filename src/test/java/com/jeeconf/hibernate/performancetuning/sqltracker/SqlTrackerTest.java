package com.jeeconf.hibernate.performancetuning.sqltracker;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.sqltracker.entity.Account;
import com.jeeconf.hibernate.performancetuning.sqltracker.entity.Client;
import org.junit.Test;

@DatabaseSetup("/sqltracker.xml")
public class SqlTrackerTest extends BaseTest {

    /**
     * Важная и полезная информация
     *
     * spring.jpa.properties.hibernate.generate_statistics=true
     */
    @Test
    public void showStatistics() {
        Client client = session.get(Client.class, 1);

        /*
     2019-03-05 14:02:41.894  INFO 12473 --- [           main] i.StatisticalLoggingSessionEventListener : Session Metrics {
                182753 nanoseconds spent acquiring 1 JDBC connections;
                0 nanoseconds spent releasing 0 JDBC connections;
                248332 nanoseconds spent preparing 1 JDBC statements;
                5286745 nanoseconds spent executing 1 JDBC statements;
                0 nanoseconds spent executing 0 JDBC batches;
                0 nanoseconds spent performing 0 L2C puts;
                0 nanoseconds spent performing 0 L2C hits;
                0 nanoseconds spent performing 0 L2C misses;
                0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
                0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
}
         */
    }

    @Test
    public void sqlCountAssertion() {
        Account account1 = session.get(Account.class, 1);
        Account account2 = session.get(Account.class, 2);

        AssertSqlCount.assertSelectCount(2);

        /*

    2019-03-05 14:04:41.660  INFO 12877 --- [           main] i.StatisticalLoggingSessionEventListener : Session Metrics {
                261582 nanoseconds spent acquiring 1 JDBC connections;
                0 nanoseconds spent releasing 0 JDBC connections;
                258718 nanoseconds spent preparing 2 JDBC statements;
                5455425 nanoseconds spent executing 2 JDBC statements;
                0 nanoseconds spent executing 0 JDBC batches;
                0 nanoseconds spent performing 0 L2C puts;
                0 nanoseconds spent performing 0 L2C hits;
                0 nanoseconds spent performing 0 L2C misses;
                0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
                0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
            }

         */
    }
}
