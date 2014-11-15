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

  //implicit def symbolToEntity(s: Symbol): GamelType = {
  //  if (global.entities contains s)
  //    return global.entities(s)
  //  // throw new InstanceNotFoundException("The entity " + s + " has not been foundr!")
  //  return null
  //}

  implicit def symbolToScene(s: Symbol): GamelScene = {
    if (global.scenes contains s)
      return global.scenes(s)
    // throw new InstanceNotFoundException("The scene " + s + " has not been foundr!")
    return null
  }

  implicit def symbolToAction(s: Symbol): GamelAction = {
    if(global.actions contains s)
      return global.actions(s)
    return null
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
