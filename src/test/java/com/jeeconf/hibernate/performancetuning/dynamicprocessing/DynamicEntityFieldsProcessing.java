package com.jeeconf.hibernate.performancetuning.dynamicprocessing;

import org.junit.Test;
import org.springframework.test.annotation.Commit;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.dynamicprocessing.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;

@DatabaseSetup("/dynamicprocessing.xml")
public class DynamicEntityFieldsProcessing extends BaseTest {

	@Test
	@Commit
	public void dynamicUpdate() {
		Client client = session.get(Client.class, 1);
		client.setAge(35);
		session.flush();

		AssertSqlCount.assertSelectCount(1);
		AssertSqlCount.assertUpdateCount(1);

        /*

        update client
            set age=35
        where
            id_client=1

         */

	}
}
