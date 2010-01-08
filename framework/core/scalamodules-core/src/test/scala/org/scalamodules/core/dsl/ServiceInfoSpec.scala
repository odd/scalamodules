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

  "Creating a ServiceInfo" when {

    "the given service is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceInfo(null, None) } should produce [IllegalArgumentException]
      }
    }

    "the given service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceInfo(new C, null) } should produce [IllegalArgumentException]
      }
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
        val interfaces = ServiceInfo(new C with T with A, None).interfaces
        interfaces should have size (2)
        interfaces should contain (classOf[T].getName)
        interfaces should contain (classOf[A].getName)
      }
    }
  }

  "Calling ServiceInfo.as" when {

    "the given type for the service interface is valid" should {
      "return a new ServiceInfo with an according service interface" in {
        ServiceInfo(new C with T, None).as[T].interface should be (Some(classOf[T]))
        ServiceInfo(new C with T, None).as[C].interface should be (Some(classOf[C]))
      }
    }
  }
}

class C

trait T

trait A extends T