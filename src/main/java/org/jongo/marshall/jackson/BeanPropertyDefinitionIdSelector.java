/*
 * Copyright (C) 2011 Benoit GUEROUT <bguerout at gmail dot com> and Yves AMSELLEM <amsellem dot yves at gmail dot com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jongo.marshall.jackson;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class BeanPropertyDefinitionIdSelector implements IdSelector<BeanPropertyDefinition> {

    public boolean isId(BeanPropertyDefinition property) {
        return hasIdName(property) || hasIdAnnotation(property);
    }

    public boolean isObjectId(BeanPropertyDefinition property) {
        return property.getPrimaryMember().getAnnotation(org.jongo.marshall.jackson.oid.ObjectId.class) != null
                || property.getPrimaryMember().getAnnotation(MongoObjectId.class) != null
                || ObjectId.class.isAssignableFrom(property.getAccessor().getRawType());
    }

    private static boolean hasIdName(BeanPropertyDefinition property) {
        return "_id".equals(property.getName());
    }

    private static boolean hasIdAnnotation(BeanPropertyDefinition property) {
        if (property == null) return false;
        AnnotatedMember accessor = property.getPrimaryMember();
        return accessor != null && (accessor.getAnnotation(MongoId.class) != null || accessor.getAnnotation(Id.class) != null);
    }
}
