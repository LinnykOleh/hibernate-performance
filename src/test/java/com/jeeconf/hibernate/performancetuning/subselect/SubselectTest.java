package com.jeeconf.hibernate.performancetuning.subselect;

import java.util.List;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import com.jeeconf.hibernate.performancetuning.subselect.entity.Client;

@DatabaseSetup("/nplusone.xml")
public class SubselectTest extends BaseTest {

	@Test
	public void subSelect() {
		List<Client> clients = session
				.createQuery("select c from SubSelectableEntity c where c.age >= :age")
				.setParameter("age", 18)
				.list();
		clients.forEach(c -> c.getAccounts().size());

		AssertSqlCount.assertSelectCount(2);

    /*
    select * from SubSelectableEntity c where c.age >= :age

    select *
    from
        account a
    where a.id_client in (select id_client from client c where c.age>=18)

    */

	}
}
