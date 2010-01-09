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
class dslSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  "A BundleContext" should {
    "be converted to a RichBundleContext implicitly" in {
      val bundleContext = mock[BundleContext]
      val richBundleContext: RichBundleContext = bundleContext
    }
  }

//  "A service object" should {
//    "be converted to a ServiceContext implicitly" in {
//      val service = "SERVICE"
//      val serviceContext: ServiceContext[String, String] = service
//    }
//  }
}
