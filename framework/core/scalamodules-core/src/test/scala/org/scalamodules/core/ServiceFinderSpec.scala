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
class ServiceFinderSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  "Creating a ServiceFinder" when {

    "the given first service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceFinder(null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given second service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceFinder(classOf[TestInterface1], interface2 = null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given third service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceFinder(classOf[TestInterface1], interface3 = null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given BundleContest is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceFinder(classOf[TestInterface1])(null) } should produce [IllegalArgumentException]
      }
    }
  }
}
