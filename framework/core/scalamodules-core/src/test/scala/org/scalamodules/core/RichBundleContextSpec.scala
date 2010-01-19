/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import org.osgi.framework.BundleContext
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

@org.junit.runner.RunWith(classOf[JUnitRunner])
class RichBundleContextSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  "Creating a RichBundleContext" when {

    "the given BundleContext is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { new RichBundleContext(null) } should produce [IllegalArgumentException]
      }
    }
  }

  "Calling RichBundleContext.createService" when {

    "the given service object is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { new RichBundleContext(mock[BundleContext]) createService null } should produce [IllegalArgumentException]
      }
    }

    "the given service object is not-null" should {
      "return a not-null ServiceCreator" in {
        new RichBundleContext(mock[BundleContext]) createService "TEST" should not be (null)
      }
    }
  }

  "Calling RichBundleContext.findService" when {

    "the given service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          new RichBundleContext(mock[BundleContext]).findService(null.asInstanceOf[Class[TestInterface1]])
        } should produce [IllegalArgumentException]
      }
    }

    "the given service interfaces are not-null" should {
      "return a not-null ServiceFinder with the correct interfaces" in {
        val serviceFinder = new RichBundleContext(mock[BundleContext]).findService(classOf[TestInterface1])
        serviceFinder should not be (null)
      }
    }
  }
}
