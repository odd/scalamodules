/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules

import java.util.Dictionary
import org.osgi.framework.{ BundleContext, ServiceReference}
import scala.collection.Map
import scala.collection.JavaConversions.asEnumeration

/**
 * Some implicit conversions and other stuff essential for the ScalaModules DSL.
 */
package object core {

  /**
   * Implicitly converts a BundleContext to a RichBundleContext backed by the given Scala Map.
   */
  implicit def toRichBundleContext(context: BundleContext) = new RichBundleContext(context)

  /**
   * Returns the given or inferred type.
   */
  def interface[I](implicit manifest: Manifest[I]) = manifest.erasure.asInstanceOf[Class[I]]

  /**
   * Returns the given or inferred types as a Tuple2.
   */
  def interfaces[I1, I2](implicit manifest1: Manifest[I1], manifest2: Manifest[I2]) =
    (manifest1.erasure.asInstanceOf[Class[I1]], manifest2.erasure.asInstanceOf[Class[I2]])

// TODO Enable as soon as Scala supports overloading in package objects (hopefully in 2.8)!
//  /**
//   * Returns the given or inferred types as a Tuple3.
//   */
//  def interfaces[I1, I2, I3](implicit manifest1: Manifest[I1], manifest2: Manifest[I2], manifest3: Manifest[I3]) =
//    (manifest1.erasure.asInstanceOf[Class[I1]],
//     manifest2.erasure.asInstanceOf[Class[I2]],
//     manifest3.erasure.asInstanceOf[Class[I3]])

  /**
   * Returns the given or inferred type.
   */
  def withInterface[I](implicit manifest: Manifest[I]) = manifest.erasure.asInstanceOf[Class[I]]

  /**
   * Implicitly converts a Scala Map to a read-only Java Dictionary backed by the given Scala Map.
   */
  private[scalamodules] implicit def scalaMapToJavaDictionary[K, V](map: Map[K, V]): Dictionary[K, V] = {
    if (map == null) null
    else new Dictionary[K, V] {
      override def size = map.size
      override def isEmpty = map.isEmpty
      override def keys = map.keysIterator
      override def elements = map.valuesIterator
      override def get(o: Object) = map.get(o.asInstanceOf[K]) match {
        case None        => null.asInstanceOf[V]
        case Some(value) => value.asInstanceOf[V]
      }
      override def put(key: K, value: V) = throw new UnsupportedOperationException("This Dictionary is read-only!")
      override def remove(o: Object) = throw new UnsupportedOperationException("This Dictionary is read-only!")
    }
  }

  /**
   * Invokes the given function on the service obtained from the given BundleContext with the given ServiceReference.
   */
  private[scalamodules] def invokeService[I, T](serviceReference: ServiceReference,
                                                f: I => T)
                                               (context: BundleContext): Option[T] = {
    require(context != null, "The BundleContext must not be null!")
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
