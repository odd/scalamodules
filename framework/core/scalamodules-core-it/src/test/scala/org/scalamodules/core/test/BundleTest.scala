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

@org.junit.runner.RunWith(classOf[MavenConfiguredJUnit4TestRunner])
class BundleTest {

  @Inject
  private var ctx: BundleContext = _

  @org.junit.Test
  def test() {
    // Start tracking

    // Get one service should result in None

    // Registering a service should result in greetingStatus == ADDING-1

    // Get one service should result in Some("Hello!")

    // Register another service with properties should result in greetingStatus == ADDING-2

    // Get one service should result in Some("Welcome...")

    // Register another service with multiple service interfaces

    // Get one for Introduction should result in a successful look-up

    // Get one for Interested should result in a successful look-up

    // Get many services should result in Some(List("Hello!", "Welcome!", "Howdy!))

    // Get many services with filter (!(name=*)) should result in Some(List("Hello!", "Howdy!"))

    // Because of the partial function support this must not throw an error!

    // Unregistering a service should result in greetingStatus == "REMOVED-1"

    // Stopping the tracking should result in greetingStatus == "REMOVED-3" (three Greeting services untracked)

  }
}
