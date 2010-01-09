/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core.dsl

private[scalamodules] case class ServiceContext
  [S <: AnyRef,
   I1 >: S <: AnyRef,
   I2 >: S <: AnyRef,
   I3 >: S <: AnyRef]
  (service: S,
   interface1: Option[Class[I1]] = None,
   interface2: Option[Class[I2]] = None,
   interface3: Option[Class[I3]] = None) {

  require(service != null, "The service must not be null!")
  require(interface1 != null, "The first service interface must not be null!")
  require(interface2 != null, "The second service interface must not be null!")
  require(interface3 != null, "The third service interface must not be null!")

  def underInterface[T >: S <: AnyRef](interface: Class[T]) =
    new ServiceContext(service, Some(interface))

  private[scalamodules] def interfaces: Array[String] = {
    val interfaces = Traversable(interface1, interface2, interface3) flatMap { i => i } map { _.getName }
    if (!interfaces.isEmpty) interfaces.toArray
    else allInterfacesOrClass(service)
  }

  private def allInterfacesOrClass[S <: AnyRef](service: S) = {
    val interfaces = allInterfaces(service.getClass)
    if (interfaces.isEmpty) Array(service.getClass.getName)
    else interfaces map { i => i.getName }
  }

  private def allInterfaces[S](clazz: Class[S]): Array[Class[_]] = {
    val interfaces = clazz.getInterfaces filter { _ != classOf[ScalaObject] }
    interfaces ++ (interfaces flatMap { allInterfaces(_) } filter { i => !(interfaces contains i) })
  }
}
