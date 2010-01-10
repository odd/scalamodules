/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core.dsl

import org.osgi.framework.{ BundleContext }

private[scalamodules] case class RichBundleContext(context: BundleContext) {

  require(context != null, "The BundleContext must not be null!")

  def createService[S <: AnyRef](s: S) = {
    require(s != null, "The service object must not be null!")
    ServiceContext(s)
  }
}
