/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import java.util.Dictionary
import org.mockito.ArgumentCaptor
import org.mockito.Matchers
import org.mockito.Mockito._
import org.osgi.framework.BundleContext
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import scala.collection.immutable.Map

@org.junit.runner.RunWith(classOf[JUnitRunner])
class ServiceSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  "Creating a Service" when {

    "the given service is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { Service(null, None)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given first service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { Service(new C1, null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given second service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { Service(new C1, interface2 = null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given third service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { Service(new C1, interface3 = null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given service properties are null" should {
      "throw an IllegalArgumentException" in {
        evaluating { Service(new C1, properties = null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given BundleContest is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { Service(new C1)(null) } should produce [IllegalArgumentException]
      }
    }
  }

  "Calling Service.interfaces" when {

    "Service.interface1 is Some(C1)" should {
      "return Array(C1)" in {
        Service(new C1, Some(classOf[C1]))(mock[BundleContext]).interfaces should equal (Array(classOf[C1].getName))
      }
    }

    "Service.interface1 is Some(C1) and interface2 is Some(T1)" should {
      "return Array(C1. T1)" in {
        Service(new C1 with T1, Some(classOf[C1]), Some(classOf[T1]))(mock[BundleContext]).interfaces should
          equal (Array(classOf[C1].getName, classOf[T1].getName))
      }
    }

    "Service.interface1 is Some(C1), interface2 is Some(T1) and interface3 is Some(T2)" should {
      "return Array(C1. T1)" in {
        Service(new C1 with T1 with T2, Some(classOf[C1]), Some(classOf[T1]), Some(classOf[T2]))(mock[BundleContext]).interfaces should
          equal (Array(classOf[C1].getName, classOf[T1].getName, classOf[T2].getName))
      }
    }

    "Service.interfaceX is None and Service.service is a C1 implementing nothing" should {
      "return Array(C1)" in {
        Service(new C1, None)(mock[BundleContext]).interfaces should equal (Array(classOf[C1].getName))
      }
    }

    "Service.interfaceX is None and Service.service is a C1 implementing T1" should {
      "return Array(T1)" in {
        Service(new C1 with T1, None)(mock[BundleContext]).interfaces should equal (Array(classOf[T1].getName))
        val interfaces = Service(new C1 with T1 with A, None)(mock[BundleContext]).interfaces
        interfaces should have size (2)
        interfaces should contain (classOf[T1].getName)
        interfaces should contain (classOf[A].getName)
      }
    }
  }

  "Calling Service.under" when {

    // First we test the "normal" version

    "the given first service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          Service(new C1)(mock[BundleContext]) under (null, Some(classOf[C1]), Some(classOf[C1]))
        } should produce [IllegalArgumentException]
      }
    }

    "the given second service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          Service(new C1)(mock[BundleContext]) under (Some(classOf[C1]), null, Some(classOf[C1]))
        } should produce [IllegalArgumentException]
      }
    }

    "the given third service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          Service(new C1)(mock[BundleContext]) under (Some(classOf[C1]), Some(classOf[C1]), null)
        } should produce [IllegalArgumentException]
      }
    }

    "the given type for the service interface is valid" should {
      "return a new Service with an according service interface" in {
        (Service(new C1 with T1)(mock[BundleContext]) under Some(classOf[T1])).interface1 should be (Some(classOf[T1]))
        (Service(new C1 with T1)(mock[BundleContext]) under Some(classOf[C1])).interface1 should be (Some(classOf[C1]))
      }
    }

    "the given type for the service interfaces is valid" should {
      "return a new Service with according service interfaces" in {
        val service =
          Service(new C1 with T1 with T2)(mock[BundleContext]) under (Some(classOf[C1]), Some(classOf[T1]), Some(classOf[T2]))
        service should not be (null)
        service.interface1 should be (Some(classOf[C1]))
        service.interface2 should be (Some(classOf[T1]))
        service.interface3 should be (Some(classOf[T2]))
      }
    }

    // And now the tuple versions: We only need to test for not-null, because they delegate to the "normal" version

    "the given service interfaces Tuple2 is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          Service(new C1)(mock[BundleContext]) under null.asInstanceOf[(Option[Class[C1]], Option[Class[C1]])]
        } should produce [IllegalArgumentException]
      }
    }

    "the given service interfaces Tuple3 is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          Service(new C1)(mock[BundleContext]) under null.asInstanceOf[(Option[Class[C1]], Option[Class[C1]], Option[Class[C1]])]
        } should produce [IllegalArgumentException]
      }
    }
  }

  "Calling Service.andRegister" when {

    "there are no custom service properties" should {
      "call BundleContext.registerService correctly" in {
        val bundleContext = mock[BundleContext]
        val service = new C1
        Service(service)(bundleContext).andRegister
        verify(bundleContext).registerService(Array(classOf[C1].getName), service, null)
      }
    }

    "there are custom service properties" should {
      "call BundleContext.registerService correctly" in {
        val bundleContext = mock[BundleContext]
        val service = new C1
        val properties = Map("scala" -> "modules")
        val capturedProperties = ArgumentCaptor.forClass(classOf[Dictionary[String, String]])
        Service(service)(bundleContext).withProperties(properties).andRegister
        verify(bundleContext).registerService(Matchers.eq(Array(classOf[C1].getName)), Matchers.eq(service), capturedProperties.capture)
        capturedProperties.getValue should have size (1)
        capturedProperties.getValue.get("scala") should be ("modules")
      }
    }
  }

  "Calling Service.withProperties" when {

    "the given service properties are null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          Service(new C1)(mock[BundleContext]) withProperties null.asInstanceOf[Map[String, Any]]
        } should produce [IllegalArgumentException]
      }
    }

    "the given service properties are not null and not empty" should {
      "return a new Service with according service properties" in {
        val service = Service(new C1)(mock[BundleContext]) withProperties (Map("x" -> 1))
        service should not be (null)
        service.properties should be (Some(Map("x" -> 1)))
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
