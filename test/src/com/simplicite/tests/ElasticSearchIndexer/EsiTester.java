package com.simplicite.tests.ElasticSearchIndexer;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import com.simplicite.commons.ElasticSearchIndexer.EsiHelper;

/**
 * Unit tests EsiTester
 */
public class EsiTester {
	@Test
	public void test() {
		try {
			(new EsiHelper(Grant.getSystemAdmin())).indexAllModules();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
