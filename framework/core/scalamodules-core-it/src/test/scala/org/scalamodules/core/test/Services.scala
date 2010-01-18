/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.scalamodules.core.test

trait Greeting {
  def greet: String
}

trait Introduction {
  def myNameIs: String
}

trait Interested {
  def andYours: String
}

trait Reverser {
  def reverse: String
}
