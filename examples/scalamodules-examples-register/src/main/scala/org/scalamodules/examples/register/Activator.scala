/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.examples
package register

import java.io.Serializable
import org.osgi.framework.{ BundleActivator, BundleContext }
import org.scalamodules.core.dsl._

class Activator extends BundleActivator {

  override def start(context: BundleContext) {
    val greeting = new Greeting {
      override def welcome = "Hello!"
      override def goodbye = "Bye!"
    }
    context createService greeting andRegister

    val coolGreeting = new Greeting {
      override def welcome = "Hey!"
      override def goodbye = "See you!"
    }
    context createService coolGreeting under interface[Greeting] withProperties ("style" -> "cool") andRegister

    val politeGreeting = new Greeting with Serializable {
      override def welcome = "Welcome!"
      override def goodbye = "Good-bye!"
    }
    context createService politeGreeting under interfaces[Greeting, Serializable] withProperties ("style" -> "polite") andRegister
  }

  override def stop(context: BundleContext) {}
}
