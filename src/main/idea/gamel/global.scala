package idea.gamel

import scala.collection.mutable.{Map, HashMap}

object global {

  // one script could only have one game object
  // but we could possibly extends to support
  // multiple instances of game with HashMap
  var game: GamelGame = null;

  // prototypes store all entity types created by "define"
  var prototypes = new HashMap[Symbol, GamelEntity]

  // entities store all entity created by "created"
  var entities = new HashMap[Symbol, GamelEntity]

  // scenes store all scenes created by "scene"
  var scenes = new HashMap[Symbol, GamelScene]

}
