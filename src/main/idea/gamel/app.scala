package idea.gamel

import scala.swing._
import scala.collection.mutable.{Map, HashMap}

/**
 * GamelApp encapsulates implicit functions that converts
 * symbols to entity, scene, etc
 * */
abstract class GamelApp extends App {

  // on/off for alias of true/false
  val on = true
  val off = false

  implicit def symbolToEntity(s: Symbol): GamelInstance = {
    if (global.entities contains s)
      return global.entities(s)
    throw new UndefinedInstanceException("the instance " + s + " is undefined");
  }

  implicit def symbolToTupleEntity(s: Symbol): (Symbol, GamelInstance) = {
    if (global.entities contains s)
      return (s, global.entities(s))
    return (s, null)
  }

  implicit def symbolToScene(s: Symbol): GamelScene = {
    if (global.scenes contains s)
      return global.scenes(s)
    return null
  }

  implicit def symbolToTupleAction(s: Symbol): (Symbol, GamelAction) = {
    if(global.actions contains s)
      return Tuple2 (s, global.actions(s))
    return (s, null)
  }

  // implicit def lambdaToRenderer(func: (Graphics2D => Unit)): GamelRenderer = {
  //   object anonymous extends GamelRenderer {
  //     def render = func
  //   }
  //   return anonymous
  //   // return new GamelRenderer {
  //   //   def render = func
  //   // }
  // }

}
