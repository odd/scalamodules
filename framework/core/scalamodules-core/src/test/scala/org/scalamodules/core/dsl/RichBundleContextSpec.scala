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

  "A RichBundleContext" when {

    "created with a null BundleContext" should {
      "throw an IllegalArgumentException" in {
        evaluating { RichBundleContext(null) } should produce [IllegalArgumentException]
      }
    }

    "called on register with a null ServiceInfo" should {
      "throw an IllegalArgumentException" in {
        evaluating { RichBundleContext(mock[BundleContext]) register null } should produce [IllegalArgumentException]
      }
    }

    "called on register with a not-null ServiceInfo" should {
      "return a not-null ServiceRegistration" ignore {
        RichBundleContext(mock[BundleContext]) register ServiceInfo("TEST", None) should not be (null)
        RichBundleContext(mock[BundleContext]) register ServiceInfo("TEST", Some(classOf[String])) should not be (null)
      }
    }
  }
}
