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
class ServiceCreatorSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  "Creating a ServiceCreator" when {

    "the given service is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceCreator(null, None)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given first service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceCreator(new TestClass1, null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given second service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceCreator(new TestClass1, interface2 = null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given third service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceCreator(new TestClass1, interface3 = null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given service properties are null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceCreator(new TestClass1, properties = null)(mock[BundleContext]) } should produce [IllegalArgumentException]
      }
    }

    "the given BundleContest is null" should {
      "throw an IllegalArgumentException" in {
        evaluating { ServiceCreator(new TestClass1)(null) } should produce [IllegalArgumentException]
      }
    }
  }

  "Calling ServiceCreator.interfaces" when {

    "ServiceCreator.interface1 is Some(TestClass1)" should {
      "return Array(TestClass1)" in {
        ServiceCreator(new TestClass1, Some(classOf[TestClass1]))(mock[BundleContext]).interfaces should equal (Array(classOf[TestClass1].getName))
      }
    }

    "ServiceCreator.interface1 is Some(TestClass1) and interface2 is Some(TestInterface1)" should {
      "return Array(TestClass1. TestInterface1)" in {
        ServiceCreator(new TestClass1 with TestInterface1, Some(classOf[TestClass1]), Some(classOf[TestInterface1]))(mock[BundleContext]).interfaces should
          equal (Array(classOf[TestClass1].getName, classOf[TestInterface1].getName))
      }
    }

    "ServiceCreator.interface1 is Some(TestClass1), interface2 is Some(TestInterface1) and interface3 is Some(TestInterface2)" should {
      "return Array(TestClass1. TestInterface1)" in {
        ServiceCreator(new TestClass1 with TestInterface1 with TestInterface2, Some(classOf[TestClass1]), Some(classOf[TestInterface1]), Some(classOf[TestInterface2]))(mock[BundleContext]).interfaces should
          equal (Array(classOf[TestClass1].getName, classOf[TestInterface1].getName, classOf[TestInterface2].getName))
      }
    }

    "ServiceCreator.interfaceX is None and ServiceCreator.service is a TestClass1 implementing nothing" should {
      "return Array(TestClass1)" in {
        ServiceCreator(new TestClass1, None)(mock[BundleContext]).interfaces should equal (Array(classOf[TestClass1].getName))
      }
    }

    "ServiceCreator.interfaceX is None and ServiceCreator.service is a TestClass1 implementing TestInterface1" should {
      "return Array(TestInterface1)" in {
        ServiceCreator(new TestClass1 with TestInterface1, None)(mock[BundleContext]).interfaces should equal (Array(classOf[TestInterface1].getName))
        val interfaces = ServiceCreator(new TestClass1 with TestInterface1 with TestInterface3, None)(mock[BundleContext]).interfaces
        interfaces should have size (2)
        interfaces should contain (classOf[TestInterface1].getName)
        interfaces should contain (classOf[TestInterface3].getName)
      }
    }
  }

  "Calling ServiceCreator.under" when {

    // First we test the "normal" version

    "the given first service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          ServiceCreator(new TestClass1)(mock[BundleContext]) under (null, Some(classOf[TestClass1]), Some(classOf[TestClass1]))
        } should produce [IllegalArgumentException]
      }
    }

    "the given second service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          ServiceCreator(new TestClass1)(mock[BundleContext]) under (classOf[TestClass1], null, Some(classOf[TestClass1]))
        } should produce [IllegalArgumentException]
      }
    }

    "the given third service interface is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          ServiceCreator(new TestClass1)(mock[BundleContext]) under (classOf[TestClass1], Some(classOf[TestClass1]), null)
        } should produce [IllegalArgumentException]
      }
    }

    "the given type for the service interface is valid" should {
      "return a new ServiceCreator with an according service interface" in {
        (ServiceCreator(new TestClass1 with TestInterface1)(mock[BundleContext]) under classOf[TestInterface1]).interface1 should be (Some(classOf[TestInterface1]))
        (ServiceCreator(new TestClass1 with TestInterface1)(mock[BundleContext]) under classOf[TestClass1]).interface1 should be (Some(classOf[TestClass1]))
      }
    }

    "the given type for the service interfaces is valid" should {
      "return a new ServiceCreator with according service interfaces" in {
        val service =
          ServiceCreator(new TestClass1 with TestInterface1 with TestInterface2)(mock[BundleContext]) under (classOf[TestClass1], Some(classOf[TestInterface1]), Some(classOf[TestInterface2]))
        service should not be (null)
        service.interface1 should be (Some(classOf[TestClass1]))
        service.interface2 should be (Some(classOf[TestInterface1]))
        service.interface3 should be (Some(classOf[TestInterface2]))
      }
    }

    // And now the tuple versions: We only need to test for not-null, because they delegate to the "normal" version

    "the given service interfaces Tuple2 is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          ServiceCreator(new TestClass1)(mock[BundleContext]) under null.asInstanceOf[(Class[TestClass1], Class[TestClass1])]
        } should produce [IllegalArgumentException]
      }
    }

    "the given service interfaces Tuple3 is null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          ServiceCreator(new TestClass1)(mock[BundleContext]) under null.asInstanceOf[(Class[TestClass1], Class[TestClass1], Class[TestClass1])]
        } should produce [IllegalArgumentException]
      }
    }
  }

  "Calling ServiceCreator.andRegister" when {

    "there are no custom service properties" should {
      "call BundleContext.registerService correctly" in {
        val bundleContext = mock[BundleContext]
        val service = new TestClass1
        ServiceCreator(service)(bundleContext).andRegister
        verify(bundleContext).registerService(Array(classOf[TestClass1].getName), service, null)
      }
    }

    "there are custom service properties" should {
      "call BundleContext.registerService correctly" in {
        val bundleContext = mock[BundleContext]
        val service = new TestClass1
        val properties = Map("scala" -> "modules")
        val capturedProperties = ArgumentCaptor.forClass(classOf[Dictionary[String, String]])
        ServiceCreator(service)(bundleContext).withProperties(properties).andRegister
        verify(bundleContext).registerService(Matchers.eq(Array(classOf[TestClass1].getName)), Matchers.eq(service), capturedProperties.capture)
        capturedProperties.getValue should have size (1)
        capturedProperties.getValue.get("scala") should be ("modules")
      }
    }
  }

  "Calling ServiceCreator.withProperties" when {

    "the given service properties are null" should {
      "throw an IllegalArgumentException" in {
        evaluating {
          ServiceCreator(new TestClass1)(mock[BundleContext]) withProperties null.asInstanceOf[Map[String, Any]]
        } should produce [IllegalArgumentException]
      }
    }

    "the given service properties are not null and not empty" should {
      "return a new ServiceCreator with according service properties" in {
        val service = ServiceCreator(new TestClass1)(mock[BundleContext]) withProperties (Map("x" -> 1))
        service should not be (null)
        service.properties should be (Some(Map("x" -> 1)))
      }
    }
  }
}
