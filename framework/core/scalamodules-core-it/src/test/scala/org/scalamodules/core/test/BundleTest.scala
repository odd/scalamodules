/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core
package test

import org.ops4j.pax.exam.Inject
import org.ops4j.pax.exam.junit.MavenConfiguredJUnit4TestRunner
import org.osgi.framework.BundleContext
import org.scalatest.matchers.ShouldMatchers
import java.lang.String

@org.junit.runner.RunWith(classOf[MavenConfiguredJUnit4TestRunner])
class BundleTest extends ShouldMatchers {

  val Service1 = "service1"

  @Inject
  private var context: BundleContext = _

  @org.junit.Test
  def test() {
    context findService withInterface[ServiceInterface] andApply { s => s } should be (None)

    context createService ServiceImplementation(Service1) andRegister
    
    context findService withInterface[ServiceInterface] andApply { _.name } should be (Some(Service1))
  }

  trait ServiceInterface {
    def name: String
  }

  case class ServiceImplementation(name: String) extends ServiceInterface
}
