package gamel

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

  implicit def symbolToEntity(s: Symbol): GamelEntity = {
    if(global.entities contains s){
      return global.entities(s)
    } else if (global.scenes contains s) {
      return global.scenes(s)
    } else {
      throw new UndefinedInstanceException("the instance or scene " + s + " is undefined")
    }
  }

  implicit def symbolToTupleInstance(s: Symbol): (Symbol, GamelInstance) = {
    if (global.entities contains s)
      return (s, global.entities(s))
    return (s, null)
  }

  implicit def symbolToTupleAction(s: Symbol): (Symbol, GamelAction) = {
    if(global.actions contains s)
      return Tuple2 (s, global.actions(s))
    return (s, null)
  }

  implicit def lambdaToRenderer(func: ((GamelEntity, Graphics2D) => Unit)): GamelRenderer = {
    return new GamelRenderer {
      def render(self: GamelEntity, g2d: Graphics2D): Unit = func(self, g2d)
    }
  }

  implicit def lambdaToSimpleRenderer(func: (Graphics2D => Unit)): GamelRenderer = {
    return new GamelRenderer {
      def render(self: GamelEntity, g2d: Graphics2D): Unit = func(g2d)
    }
  }

}
