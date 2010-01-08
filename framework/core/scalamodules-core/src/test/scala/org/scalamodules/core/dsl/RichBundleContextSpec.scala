/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core.dsl

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

  "Calling RichBundleContext.register" when {

    "the given ServiceInfo is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { RichBundleContext(mock[BundleContext]) register null } should produce [IllegalArgumentException]
      }
    }

    "the given ServiceInfo is not-null" should {
      "return a not-null ServiceRegistration" ignore {
        RichBundleContext(mock[BundleContext]) register ServiceInfo("TEST", None) should not be (null)
        RichBundleContext(mock[BundleContext]) register ServiceInfo("TEST", Some(classOf[String])) should not be (null)
      }
    }
  }
}
