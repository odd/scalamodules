/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core

import org.osgi.framework.{ BundleContext }

/**
 * Some implicit conversions and other stuff essential for the ScalaModules DSL.
 */
package object dsl {

  /**
   * Implicitly converts a BundleContext to a RichBundleContext backed by the given Scala Map.
   */
  implicit def toRichBundleContext(context: BundleContext) = new RichBundleContext(context)

  /**
   * Returns the given or inferred type in a Some.
   */
  def interface[I](implicit manifest: Manifest[I]) = Some(manifest.erasure.asInstanceOf[Class[I]])
}
