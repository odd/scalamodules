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
        evaluating { RichBundleContext(null) } should produce [IllegalArgumentException]
      }
    }
  }

  "Calling RichBundleContext.createService" when {

    "the given service object is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { RichBundleContext(mock[BundleContext]) createService null } should produce [IllegalArgumentException]
      }
    }

    "the given service object is not-null" should {
      "return a not-null ServiceCreator" in {
        RichBundleContext(mock[BundleContext]) createService "TEST" should not be (null)
      }
    }
  }

  "Calling RichBundleContext.findService" when {

    // First we test the "normal" version

    "the given first service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          RichBundleContext(mock[BundleContext]).findService(null.asInstanceOf[Class[TestInterface1]])
        } should produce [IllegalArgumentException]
      }
    }

    "the given second service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          RichBundleContext(mock[BundleContext]).findService(classOf[TestInterface1], null)
        } should produce [IllegalArgumentException]
      }
    }

    "the given third service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          RichBundleContext(mock[BundleContext]).findService(classOf[TestInterface1], interface3 = null)
        } should produce [IllegalArgumentException]
      }
    }

    "the given service interfaces are not-null" should {
      "return a not-null ServiceFinder with the correct interfaces" in {
        val serviceGetter = RichBundleContext(mock[BundleContext]).findService(classOf[TestInterface1],
                                                                               Some(classOf[TestInterface2]),
                                                                               Some(classOf[TestInterface3]))
        serviceGetter should not be (null)
        serviceGetter.interface1 should be (classOf[TestInterface1])
        serviceGetter.interface2 should be (Some(classOf[TestInterface2]))
        serviceGetter.interface3 should be (Some(classOf[TestInterface3]))
      }
    }

    // And now the tuple versions: We only need to test for not-null, because they delegate to the "normal" version

    "the given service interfaces Tuple2 is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          RichBundleContext(mock[BundleContext]).findService(null.asInstanceOf[Tuple2[Class[TestInterface1],
                                                                                      Class[TestInterface2]]])
        } should produce [IllegalArgumentException]
      }
    }

    "the given service interfaces Tuple3 is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          RichBundleContext(mock[BundleContext]).findService(null.asInstanceOf[Tuple3[Class[TestInterface1],
                                                                                      Class[TestInterface2],
                                                                                      Class[TestInterface3]]])
        } should produce [IllegalArgumentException]
      }
    }
  }
}
