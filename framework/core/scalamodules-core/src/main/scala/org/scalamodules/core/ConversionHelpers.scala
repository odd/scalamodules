/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import java.util.Dictionary
import scala.collection.Map
import scala.collection.JavaConversions.asEnumeration

/**
 * Helpers for converting betweet Java and Scala collection types beyond what is offered by the Scala library.
 */
object ConversionHelpers {

  /**
   * Implicitly converts a Scala Map to a read-only Java Dictionary backed by the given Scala Map.
   */
  implicit def scalaMapToJavaDictionary[K, V](map: Map[K, V]): Dictionary[K, V] = {
    if (map == null) null
    else new Dictionary[K, V] {
      override def size = map.size
      override def isEmpty = map.isEmpty
      override def keys = map.keysIterator
      override def elements = map.valuesIterator
      override def get(o: Object) = map.get(o.asInstanceOf[K]) match {
        case None        => null.asInstanceOf[V]
        case Some(value) => value.asInstanceOf[V]
      }
      override def put(key: K, value: V) = throw new UnsupportedOperationException("This Dictionary is read-only!")
      override def remove(o: Object) = throw new UnsupportedOperationException("This Dictionary is read-only!")
    }
  }
}
