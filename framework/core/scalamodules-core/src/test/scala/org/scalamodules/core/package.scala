/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import java.util.Dictionary
import org.osgi.framework.BundleContext
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import scala.collection.immutable.{ Map => IMap }

@org.junit.runner.RunWith(classOf[JUnitRunner])
class coreSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  "A BundleContext" should {
    "be converted to a RichBundleContext implicitly" in {
      val bundleContext = mock[BundleContext]
      val richBundleContext: RichBundleContext = bundleContext
    }
  }

  "Calling interface" when {
    "the type is given explicitly" should {
      "return the correct type" in {
        interface[String] should be (Some(classOf[String]))
      }
    }
  }

  "Calling interfaces" when {
    "the two types are given explicitly" should {
      "return the correct types" in {
        interfaces[String, String] should be (Some(classOf[String]), Some(classOf[String]))
      }
    }
  }

// TODO Enable as soon as Scala supports overloading in package objects (hopefully in 2.8)!
//  "Calling interfaces" when {
//    "the three types are given explicitly" should {
//      "return the correct types" in {
//        interfaces[String, String, Int] should be (Some(classOf[String]), Some(classOf[String]), Some(classOf[Int]))
//      }
//    }
//  }

  "Calling scalaMapToJavaDictionary" when {
    val emptyScalaMap = IMap[Any, Any]()
    val notEmptyScalaMap = IMap("a" -> "1")

    "the given Scala Map is null" should {
      "return null" in {
        val javaDictionary: Dictionary[Any, Any] = scalaMapToJavaDictionary(null)
        javaDictionary should be (null)
      }
    }

    "the given Scala map is not-null and empty" should {
      "return a not-null and empty Java Dictionary" in {
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
    }

    "the given Scala map is not-null and not-empty" should {
      "return a not-null and not-empty Java Dictionary" in {
        val javaDictionary = scalaMapToJavaDictionary(notEmptyScalaMap)
        javaDictionary should not be (null)
        javaDictionary get "a" should not equal (null)
      }
    }
  }
}
