import scala.collection.mutable.{Map, HashMap}
import scala.language.implicitConversions

// Entity class
class Entity(n: Symbol) {

  def name = n

  def as(s: Any): Entity = {
    println("<==" + s)
    this
  }

  def apply(s: Any): Unit = {
    println("apply " + s)
  }

  // def unapply()
}

// Scene class
class Scene(n: Symbol) {

  def name = n

  def as(s: Any): Scene = {
    println("<==" + s)
    this
  }

  def apply(s: Any): Unit = {
    println("apply " + s)
  }

  // def unapply()
}

object nowhere {
  // do something here
  var storage = new HashMap[Symbol, Entity]
}

object create {

  // create an entity
  def entity(s: Symbol): Entity = new Entity(s)

  // create an scene
  def scene(s: Symbol): Scene = new Scene(s)
}


object Gamel extends App {

  // convert string to entity
  // implicit def symbolToEntity(s: Symbol): Entity = new Entity(s)
  // implicit def symbolToScene(s: Symbol): Scene = new Scene(s)
  // implicti def symbolToObject(s: Symbol): Thing = global get s

  println("Hello, Gamel!")

  create entity 'Tianyu as ( 
    "Has",
    "Has",
    "Has",
    "Has"
  )

  // create scene 'Scene1 as (
  //   "scene",
  //   "01"
  // )

  // 'Scene1 --> 'Scene2

}
