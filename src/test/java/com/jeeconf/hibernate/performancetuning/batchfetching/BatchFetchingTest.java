package com.jeeconf.hibernate.performancetuning.batchfetching;

import java.util.List;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.batchfetching.entity.Account;
import com.jeeconf.hibernate.performancetuning.batchfetching.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;

@DatabaseSetup("/nplusone.xml")
public class BatchFetchingTest extends BaseTest {

	@Test
	public void batchFetching() {
		List<Client> clients = session
				.createQuery("select c from BatchableFetchedClientEntity c where c.age >= :age")
				.setParameter("age", 18)
				.list();
		clients.forEach(c -> c.getAccounts().size());

		AssertSqlCount.assertSelectCount(3);

    /*
        select *
        from
            BatchableFetchedClientEntity c
        where
            c.age >= 18

        select *
        from
            account a
        where
            a.id_client in (1, 2, 3, 4, 5)

        select *
        from
            account a
        where
            a.id_client in (6, 7, 8, 9, 10)
     */
	}

	@Test
	public void batchSizeCache() {
		Account account1 = session.get(Account.class, 1);
		Account account2 = session.get(Account.class, 4);
		account1.getClient().getName();

		AssertSqlCount.assertSelectCount(3);
	}
}
