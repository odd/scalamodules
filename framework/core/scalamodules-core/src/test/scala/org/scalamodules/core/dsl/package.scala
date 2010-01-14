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

  "Calling interface with the type given explicitly" should {
    "return the correct type" in {
      interface[String] should be (Some(classOf[String]))
    }
  }

  "Calling towInterfaces with the two types given explicitly" should {
    "return the correct type" in {
      interfaces[String, String] //should be (Some(classOf[String]), Some(classOf[String]))
    }
  }

// TODO Enable as soon as Scala supports overloading in package objects (hopefully in 2.8)!
//  "Calling threeInterfaces with the three type given explicitly" should {
//    "return the correct type" in {
//      interfaces[String, String, Int] //should be (Some(classOf[String]), Some(classOf[String]), Some(classOf[Int]))
//    }
//  }
}
