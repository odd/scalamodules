/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import org.osgi.framework.BundleContext

private[scalamodules] class ServiceFinder[I <: AnyRef](interface: Class[I])
                                                      (context: BundleContext) {

  require(interface != null, "The service interface must not be null!")
  require(context != null, "The BundleContext must not be null!")

  def andApply[T](f: I => T): Option[T] = {
    require(f != null, "The function to be applied to the service must not be null!")
    context getServiceReference interface.getName match {
      case null             => None
      case serviceReference => invokeService(serviceReference, f)(context)
    }
  }

  // TODO Add andApply for service plus properties!
}
