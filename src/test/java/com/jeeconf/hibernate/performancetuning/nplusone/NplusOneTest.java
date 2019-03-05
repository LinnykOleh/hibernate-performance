package com.jeeconf.hibernate.performancetuning.nplusone;

import java.util.List;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.nplusone.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;

@DatabaseSetup("/nplusone.xml")
public class NplusOneTest extends BaseTest {

	@Test
	public void npo() {
		List<Client> clients = session.createQuery("select c from NPlusOneClient c where c.age >= :age")
				.setParameter("age", 18)
				.list();
		clients.forEach(c -> c.getAccounts().size());

		AssertSqlCount.assertSelectCount(11);
	}

	@Test
	public void joinFetch() {
		List<Client> clients = session
				.createQuery("select c from NPlusOneClient c join fetch c.accounts where c.age >= :age")
				.setParameter("age", 18)
				.list();
		clients.forEach(c -> c.getAccounts().size());

		AssertSqlCount.assertSelectCount(1);

	/*
	select c
    from
        NPlusOneClient c
    join
        fetch c.accounts
    where
        c.age >= :age

    or in sql

    select * from client c inner join account a on c.id_client=a.id_client where c.age>=18

    */
	}
}
