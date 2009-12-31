/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import ConversionHelpers._

import java.util.Dictionary
import org.scalatest._
import junit.JUnitRunner
import matchers.ShouldMatchers
import scala.collection.immutable.{Map => IMap}

@org.junit.runner.RunWith(classOf[JUnitRunner])
class ConversionHelpersSpec extends WordSpec with ShouldMatchers {

  "The method scalaMapToJavaDictionary" should {
    val emptyScalaMap = IMap[Any, Any]()

    "return null when called with null" in {
      val result: Dictionary[_, _] = scalaMapToJavaDictionary(null)
      result should be (null)
    }

    "not return null when called with null" in {
      val result: Dictionary[_, _] = scalaMapToJavaDictionary(emptyScalaMap)
      result should not be (null)
    }
  }
}
