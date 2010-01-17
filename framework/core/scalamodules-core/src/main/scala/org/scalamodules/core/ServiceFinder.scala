/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import org.osgi.framework.BundleContext

private[scalamodules] case class ServiceFinder[I1 <: AnyRef,
                                               I2 <: AnyRef,
                                               I3 <: AnyRef]
                                              (interface1: Class[I1],
                                               interface2: Option[Class[I2]] = None,
                                               interface3: Option[Class[I3]] = None)
                                              (bundleContext: BundleContext) {

  require(interface1 != null, "The first service interface must not be null!")
  require(interface2 != null, "The second service interface must not be null!")
  require(interface3 != null, "The third service interface must not be null!")
  require(bundleContext != null, "The BundleContext must not be null!")
}
