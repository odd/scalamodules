/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import org.osgi.framework.{ BundleContext, ServiceReference }

private[scalamodules] trait ServiceInvoker[I <: AnyRef] {
  this: { def context: BundleContext } =>

  def invoke[T](serviceReference: ServiceReference, f: I => T) = {
    require(serviceReference != null, "The ServiceReference must not be null!")
    require(f != null, "The function to be applied to the service must not be null!")
    try {
      context getService serviceReference match {  // Might be null even if serviceReference is not null!
        case null    => None
        case service => Some(f(service.asInstanceOf[I]))
      }
    } finally context ungetService serviceReference  // Must be called!
  }
}
