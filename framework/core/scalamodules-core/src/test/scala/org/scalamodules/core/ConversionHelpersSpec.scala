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
    val notEmptyScalaMap = IMap("a" -> "1")

    "return null when called with a null Scala Map" in {
      val javaDictionary: Dictionary[Any, Any] = scalaMapToJavaDictionary(null)
      javaDictionary should be (null)
    }

    "return a not-null and empty Java Dictionary when called with a not-null and empty Scala Map" in {
      val javaDictionary: Dictionary[Any, Any] = scalaMapToJavaDictionary(emptyScalaMap)
      javaDictionary should not be (null)
      javaDictionary.size should be (0)
      javaDictionary.isEmpty should be (true)
      javaDictionary.keys.hasMoreElements should be (false)
      javaDictionary.elements.hasMoreElements should be (false)
      javaDictionary get "" should equal (null)
      evaluating { javaDictionary.put("", "") } should produce [UnsupportedOperationException]
      evaluating { javaDictionary remove "" } should produce [UnsupportedOperationException]
    }

    "return a not-null and not-empty Java Dictionary when called with a not-null and not-empty Scala Map" in {
      val javaDictionary = scalaMapToJavaDictionary(notEmptyScalaMap)
      javaDictionary should not be (null)
      javaDictionary get "a" should not equal (null)
    }
  }
}
