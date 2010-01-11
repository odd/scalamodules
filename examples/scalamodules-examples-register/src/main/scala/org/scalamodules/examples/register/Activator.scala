/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.examples
package register

import org.osgi.framework.{ BundleActivator, BundleContext }
import org.scalamodules.core.dsl._

class Activator extends BundleActivator {

  override def start(context: BundleContext) {
    val hello = new Greeting {
      override def welcome = "Hello!"
      override def goodbye = "Bye!"
    }
    context createService hello
  }

  override def stop(context: BundleContext) {}
}
