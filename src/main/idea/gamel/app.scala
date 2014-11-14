package idea.gamel

import scala.collection.mutable.{Map, HashMap}

/**
 * GamelApp encapsulates implicit functions that converts
 * symbols to entity, scene, etc
 * */
abstract class GamelApp extends App {

  implicit def symbolToEntity(s: Symbol): GamelEntity = {
    if (global.entities contains s)
      return global.entities(s)
    return null
  }

  implicit def SymbolToScene(s: Symbol): GamelScene = {
    if (global.scenes contains s)
      return global.scenes(s)
    return null
  }

  def start(): Unit = {
    gamel.start()
  }
}
