package com.jeeconf.hibernate.performancetuning.cache;

import static junit.framework.Assert.assertTrue;

import org.hibernate.Cache;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.cache.entity.City;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;

@DatabaseSetup("/cache.xml")
public class EntityCacheTest extends BaseTest {

	@Test
	public void secondLevelCache() {
		City city = session.get(City.class, 1);
		Cache secondLevelCache = getSessionFactory().getCache();

		assertTrue(secondLevelCache.containsEntity(City.class, 1));

		//secondLevelCache.evictEntity(City.class, 1);
		session.clear(); // clear first level cache

		City cachedCity = session.get(City.class, 1);

		AssertSqlCount.assertSelectCount(1);
	}

	@Test
	public void queryCache() {
		String query = "select c from City c";
		session.createQuery(query).setCacheable(true).list();
		session.clear();

		session.createQuery(query).setCacheable(true).list();

		AssertSqlCount.assertSelectCount(1);
	}

	@Test
	public void queryCacheInConjunctionWithSecondLevel() {
		String query = "select c from CachableFromSecondLevelCacheClient c";
		session.createQuery(query).setCacheable(true).list();
		session.clear();

		session.createQuery(query).setCacheable(true).list();

		AssertSqlCount.assertSelectCount(3);
	}

}
