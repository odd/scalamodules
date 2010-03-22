/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core
package test

import java.lang.String
import org.ops4j.pax.exam.Inject
import org.ops4j.pax.exam.junit.MavenConfiguredJUnit4TestRunner
import org.osgi.framework.BundleContext
import org.scalatest.matchers.ShouldMatchers

@org.junit.runner.RunWith(classOf[MavenConfiguredJUnit4TestRunner])
class BundleTest extends ShouldMatchers {

  @org.junit.Test
  def test() {
    val Service1 = "service1"
    val Name = "name"
    context findService withInterface[ServiceInterface] andApply { s => s } should be (None)
    context createService (ServiceImplementation(Service1), Name -> Service1)
    context findService withInterface[ServiceInterface] andApply { _.name } should be (Some(Service1))
    context findService withInterface[ServiceInterface] andApply { (_, properties) => properties(Name) } should be (Some(Service1))
  }

  @Inject
  private var context: BundleContext = _

  private trait ServiceInterface {
    def name: String
  }

  private case class ServiceImplementation(name: String) extends ServiceInterface
}