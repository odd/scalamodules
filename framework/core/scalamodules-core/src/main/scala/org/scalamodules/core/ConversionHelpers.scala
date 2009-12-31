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
import scala.collection.immutable.{Map => IMap}

object ConversionHelpers {

  /**
   * Converts a Scala Map to a read-only Java Dictionary.
   */
  implicit def scalaMapToJavaDictionary[K, V](map: Map[K, V]) = null
}
