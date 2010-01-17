/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import org.osgi.framework.{ BundleContext }

private[scalamodules] case class RichBundleContext(bundleContext: BundleContext) {

  require(bundleContext != null, "The BundleContext must not be null!")

  def createService[S <: AnyRef](service: S): ServiceCreator[S, S, S, S] = {
    require(service != null, "The service object must not be null!")
    ServiceCreator(service)(bundleContext)
  }

  def findService[I1 <: AnyRef,
                 I2 <: AnyRef]
                (interfaces: (Class[I1], Class[I2])): ServiceFinder[I1, I2, Nothing] = {
    require(interfaces != null, "The service interfaces must not be null!")
    findService(interfaces._1, Some(interfaces._2))
  }

  def findService[I1 <: AnyRef,
                 I2 <: AnyRef,
                 I3 <: AnyRef]
                (interfaces: (Class[I1], Class[I2], Class[I3])): ServiceFinder[I1, I2, I3] = {
    require(interfaces != null, "The service interfaces must not be null!")
    findService(interfaces._1, Some(interfaces._2), Some(interfaces._3))
  }

  def findService[I1 <: AnyRef,
                 I2 <: AnyRef,
                 I3 <: AnyRef]
                (interface1: Class[I1],
                 interface2: Option[Class[I2]] = None,
                 interface3: Option[Class[I3]] = None): ServiceFinder[I1, I2, I3] = {
    require(interface1 != null, "The first service interface must not be null!")
    require(interface2 != null, "The second service interface must not be null!")
    require(interface3 != null, "The third service interface must not be null!")
    ServiceFinder(interface1, interface2, interface3)(bundleContext)
  }
}
