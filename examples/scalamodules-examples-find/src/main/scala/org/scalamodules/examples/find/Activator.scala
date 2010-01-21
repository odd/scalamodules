/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.examples
package find

import org.osgi.framework.{ BundleActivator, BundleContext }
import org.scalamodules.core._

class Activator extends BundleActivator {

  override def start(context: BundleContext) {
    context findService withInterface[Greeting] andApply { _.welcome } match {
      case None          => println("No Greeting service available!")
      case Some(welcome) => println(welcome)
    }
  }

  override def stop(context: BundleContext) {}
}
