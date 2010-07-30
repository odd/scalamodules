/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
import com.weiglewilczek.bnd4sbt.BNDPlugin
import sbt._

class ScalaModulesParentProject(info: ProjectInfo) extends ParentProject(info) {

  // ===================================================================================================================
  // Dependencies
  // ===================================================================================================================

  object Dependencies {

    // Versions
    val osgiVersion = "4.2.0"
    val paxExamVersion = "1.2.0"

    // Provided
    val osgiCore = "org.osgi" % "org.osgi.core" % osgiVersion % "provided" withSources
    val osgiCompendium = "org.osgi" % "org.osgi.compendium" % osgiVersion % "provided" withSources

    // Test
    val specs = "org.scala-tools.testing" %% "specs" % "1.6.5" % "test"
    val mockito = "org.mockito" % "mockito-all" % "1.8.4" % "test"
    val junitIF = "com.novocode" % "junit-interface" % "0.3" % "test"

    // Test (Pax Exam)
    val paxExam = "org.ops4j.pax.exam" % "pax-exam" % paxExamVersion % "test"
    val paxExamJUnit = "org.ops4j.pax.exam" % "pax-exam-junit" % paxExamVersion % "test"
    val paxExamCD = "org.ops4j.pax.exam" % "pax-exam-container-default" % paxExamVersion % "test"
  }

  // ===================================================================================================================
  // scalamodules-core subproject
  // ===================================================================================================================

  val coreProject = project("core", "scalamodules-core", new CoreProject(_))

  class CoreProject(info: ProjectInfo) extends DefaultProject(info) with BNDPlugin {
    import Dependencies._
    override def libraryDependencies = Set(osgiCore, osgiCompendium, specs, mockito)
    override def bndExportPackage = "com.weiglewilczek.scalamodules;version=\"%s\"".format(projectVersion.value) :: Nil
  }

  // ===================================================================================================================
  // scalamodules-core-it subproject
  // ===================================================================================================================

  val coreITProject = project("core-it", "scalamodules-core-it", new CoreITProject(_), coreProject)

  class CoreITProject(info: ProjectInfo) extends DefaultProject(info) {
    import Dependencies._
    override def testAction = super.testAction dependsOn coreProject.`package`
    override def libraryDependencies = Set(specs, mockito, paxExam, paxExamJUnit, paxExamCD, junitIF)
    override def testFrameworks =
      super.testFrameworks ++ Seq(new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker"))
  }
}
