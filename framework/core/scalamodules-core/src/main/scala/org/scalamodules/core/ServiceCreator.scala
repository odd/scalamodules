/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import org.osgi.framework.{ BundleContext, ServiceRegistration }
import scala.collection.Map
import scala.collection.immutable.{ Map => IMap }

private[scalamodules] class ServiceCreator[S <: AnyRef,
                                           I1 >: S <: AnyRef,
                                           I2 >: S <: AnyRef,
                                           I3 >: S <: AnyRef]
                                          (serviceObject: S,
                                           interface1: Option[Class[I1]] = None,
                                           interface2: Option[Class[I2]] = None,
                                           interface3: Option[Class[I3]] = None,
                                           properties: Option[Map[String, Any]] = None)
                                          (context: BundleContext) {

  require(serviceObject != null, "The service object must not be null!")
  require(interface1 != null, "The first service interface must not be null!")
  require(interface2 != null, "The second service interface must not be null!")
  require(interface3 != null, "The third service interface must not be null!")
  require(properties != null, "The service properties must not be null!")
  require(context != null, "The BundleContext must not be null!")

  def under[T1 >: S <: AnyRef,
            T2 >: S <: AnyRef]
           (interfaces: (Class[T1], Class[T2])): ServiceCreator[S, T1, T2, S] = {
    require(interfaces != null, "The service interfaces must not be null!")
    under(interfaces._1, Some(interfaces._2))
  }

  def under[T1 >: S <: AnyRef,
            T2 >: S <: AnyRef,
            T3 >: S <: AnyRef]
           (interfaces: (Class[T1], Class[T2], Class[T3])): ServiceCreator[S, T1, T2, T3] = {
    require(interfaces != null, "The service interfaces must not be null!")
    under(interfaces._1, Some(interfaces._2), Some(interfaces._3))
  }

  def under[T1 >: S <: AnyRef,
            T2 >: S <: AnyRef,
            T3 >: S <: AnyRef]
           (interface1: Class[T1],
            interface2: Option[Class[T2]] = None,
            interface3: Option[Class[T3]] = None): ServiceCreator[S, T1, T2, T3] = {
    require(interface1 != null, "The first service interface must not be null!")
    require(interface2 != null, "The second service interface must not be null!")
    require(interface3 != null, "The third service interface must not be null!")
    new ServiceCreator(serviceObject, Some(interface1), interface2, interface3, properties)(context)
  }

  def withProperties(properties: (String, Any)*): ServiceCreator[S, I1, I2, I3] = withProperties(IMap(properties: _*))

  def withProperties(properties: Map[String, Any]): ServiceCreator[S, I1, I2, I3] = {
    require(properties != null, "The service properties must not be null!")
    new ServiceCreator(serviceObject, interface1, interface2, interface3, Some(properties))(context)
  }

  def andRegister: ServiceRegistration = properties match {
    case None => context.registerService(interfaces, serviceObject, null)
    case Some(p) => context.registerService(interfaces, serviceObject, p)
  }

  private[scalamodules] def interfaces: Array[String] = {
    val interfaces = Traversable(interface1, interface2, interface3) flatMap { i => i } map { _.getName }
    if (!interfaces.isEmpty) interfaces.toArray
    else allInterfacesOrClass
  }

  private def allInterfacesOrClass = {
    val interfaces = allInterfaces(serviceObject.getClass)
    if (interfaces.isEmpty) Array(serviceObject.getClass.getName)
    else interfaces map { i => i.getName }
  }

  private def allInterfaces[S](clazz: Class[S]): Array[Class[_]] = {
    val interfaces = clazz.getInterfaces filter { _ != classOf[ScalaObject] }
    interfaces ++ (interfaces flatMap { allInterfaces(_) } filter { i => !(interfaces contains i) })
  }
}
