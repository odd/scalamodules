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
class ServiceContextSpec extends WordSpec with ShouldMatchers {

  "Creating a ServiceContext" when {

    "the given service is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceContext(null, None) } should produce [IllegalArgumentException]
      }
    }

    "the given first service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceContext(new C1, null) } should produce [IllegalArgumentException]
      }
    }

    "the given second service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceContext(new C1, interface2 = null) } should produce [IllegalArgumentException]
      }
    }

    "the given third service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceContext(new C1, interface3 = null) } should produce [IllegalArgumentException]
      }
    }
  }

  "Calling ServiceContext.interfaces" when {

    "ServiceContext.interface1 is Some(C1)" should {
      "return Array(C1)" in {
        ServiceContext(new C1, Some(classOf[C1])).interfaces should equal (Array(classOf[C1].getName))
      }
    }

    "ServiceContext.interface1 is Some(C1) and interface2 is Some(T1)" should {
      "return Array(C1. T1)" in {
        ServiceContext(new C1 with T1, Some(classOf[C1]), Some(classOf[T1])).interfaces should
          equal (Array(classOf[C1].getName, classOf[T1].getName))
      }
    }

    "ServiceContext.interface1 is Some(C1), interface2 is Some(T1) and interface3 is Some(T2)" should {
      "return Array(C1. T1)" in {
        ServiceContext(new C1 with T1 with T2, Some(classOf[C1]), Some(classOf[T1]), Some(classOf[T2])).interfaces should
          equal (Array(classOf[C1].getName, classOf[T1].getName, classOf[T2].getName))
      }
    }

    "ServiceContext.interface is None and ServiceContext.service is a C1 implementing nothing" should {
      "return Array(C1)" in {
        ServiceContext(new C1, None).interfaces should equal (Array(classOf[C1].getName))
      }
    }

    "ServiceContext.interface is None and ServiceContext.service is a C1 implementing T1" should {
      "return Array(T1)" in {
        ServiceContext(new C1 with T1, None).interfaces should equal (Array(classOf[T1].getName))
        val interfaces = ServiceContext(new C1 with T1 with A, None).interfaces
        interfaces should have size (2)
        interfaces should contain (classOf[T1].getName)
        interfaces should contain (classOf[A].getName)
      }
    }
  }

  "Calling ServiceContext.underInterface" when {

    "the given type for the service interface is valid" should {
      "return a new ServiceContext with an according service interface" in {
        (ServiceContext(new C1 with T1, None) underInterface classOf[T1]).interface1 should be (Some(classOf[T1]))
        (ServiceContext(new C1 with T1, None) underInterface classOf[C1]).interface1 should be (Some(classOf[C1]))
      }
    }
  }
}

class C1
class C2
class C3

trait T1
trait T2

trait A extends T1
