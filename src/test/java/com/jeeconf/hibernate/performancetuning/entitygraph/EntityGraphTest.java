package com.jeeconf.hibernate.performancetuning.entitygraph;

import java.util.List;

import org.hibernate.annotations.QueryHints;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.entitygraph.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;

@DatabaseSetup("/nplusone.xml")
public class EntityGraphTest extends BaseTest {

	@Test
	public void clientFetchAccounts() {
		List<Client> clients = em
				.createQuery("select c from EntityGraphClient c where c.age >= :age", Client.class)
				.setParameter("age", 18)
				.setHint(QueryHints.FETCHGRAPH, em.getEntityGraph(Client.ACCOUNTS_GRAPH))
				.getResultList();

		clients.forEach(c -> c.getAccounts().size());

		AssertSqlCount.assertSelectCount(1);

        /*

        select *
        from
            client c
        left outer join
            account a on c.id_client=a.id_client
        where
        	c.age>=18

         */
	}

}
