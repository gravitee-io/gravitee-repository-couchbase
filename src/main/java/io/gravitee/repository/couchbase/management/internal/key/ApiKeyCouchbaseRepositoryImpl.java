/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.repository.couchbase.management.internal.key;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.path.WherePath;
import io.gravitee.repository.couchbase.management.internal.model.ApiKeyCouchbase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.repository.query.support.N1qlUtils;

import java.util.List;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ApiKeyCouchbaseRepositoryImpl implements ApiKeyCouchbaseRepositoryCustom {

	private final static String CLASS_FIELD_VALUE = ApiKeyCouchbase.class.getName();
	private final static String CLASS_FIELD = "_class";

	@Autowired
	private CouchbaseTemplate cbTemplate;

	public List<ApiKeyCouchbase> findBySubscription(String subscription) {
		WherePath baseStatement = N1qlUtils.createSelectFromForEntity(cbTemplate.getCouchbaseBucket().name());
		Expression baseExpression = Expression.x(CLASS_FIELD).eq(Expression.x("$class"));
		Expression propertyExpression = Expression.x("subscription").eq(Expression.x("$subscription"));

		JsonObject parameters = JsonObject.create()
				.put("subscription", subscription)
				.put("class", CLASS_FIELD_VALUE);

		N1qlQuery query = N1qlQuery.parameterized(baseStatement.where(baseExpression.and(propertyExpression)), parameters);
		return cbTemplate.findByN1QL(query, ApiKeyCouchbase.class);
	}

	public List<ApiKeyCouchbase> findByPlan(String plan) {
		WherePath baseStatement = N1qlUtils.createSelectFromForEntity(cbTemplate.getCouchbaseBucket().name());
		Expression baseExpression = Expression.x(CLASS_FIELD).eq(Expression.x("$class"));
		Expression propertyExpression = Expression.x("plan").eq(Expression.x("$plan"));

		JsonObject parameters = JsonObject.create()
				.put("plan", plan)
				.put("class", CLASS_FIELD_VALUE);

		N1qlQuery query = N1qlQuery.parameterized(baseStatement.where(baseExpression.and(propertyExpression)), parameters);
		return cbTemplate.findByN1QL(query, ApiKeyCouchbase.class);
	}
}
