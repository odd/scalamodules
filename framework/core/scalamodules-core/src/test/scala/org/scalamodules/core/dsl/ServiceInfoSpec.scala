/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core.dsl

import org.scalatest._
import junit.JUnitRunner
import matchers.ShouldMatchers

@org.junit.runner.RunWith(classOf[JUnitRunner])
class ServiceInfoSpec extends WordSpec with ShouldMatchers {

  "Creating a ServiceInfo with a null service" should {
    "throw an IllegalArgumentException" in {
      evaluating { ServiceInfo(null, None) } should produce [IllegalArgumentException]
    }
  }

  "Creating a ServiceInfo with a null service interface" should {
    "throw an IllegalArgumentException" in {
      evaluating { ServiceInfo(new C, null) } should produce [IllegalArgumentException]
    }
  }

  "Calling ServiceInfo.interfaces" when {

    "ServiceInfo.interface is Some(C)" should {
      "return Array(C)" in {
        ServiceInfo(new C, Some(classOf[C])).interfaces should equal (Array(classOf[C].getName))
      }
    }

    "ServiceInfo.interface is None and ServiceInfo.service is a C implementing nothing" should {
      "return Array(C)" in {
        ServiceInfo(new C, None).interfaces should equal (Array(classOf[C].getName))
      }
    }

    "ServiceInfo.interface is None and ServiceInfo.service is a C implementing T" should {
      "return Array(T)" in {
        ServiceInfo(new C with T, None).interfaces should equal (Array(classOf[T].getName))
      }
    }
  }
}

class C

trait T
