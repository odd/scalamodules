/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core.dsl

private[dsl] case class ServiceInfo[I <: AnyRef, S <: I](val service: S,
                                                         val interface: Option[Class[I]]) {

  require(service != null, "The service must not be null!")
  require(interface != null, "The service interface must not be null!")

  def interfaces: Array[String] = interface map { i => Array(i.getName) } getOrElse allInterfacesOrClass(service)

  private def allInterfacesOrClass[S <: AnyRef](service: S) = {
    val interfaces = allInterfaces(service.getClass)
    if (interfaces.isEmpty) Array(service.getClass.getName)
    else interfaces map { i => i.getName }
  }

  def allInterfaces[S](clazz: Class[S]): Array[Class[_]] = {
    val interfaces = clazz.getInterfaces filter { _ != classOf[ScalaObject] }
    interfaces ++ ( interfaces flatMap { allInterfaces(_) } filter { i => !(interfaces contains i) })
  }
}
