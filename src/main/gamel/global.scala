package gamel

import scala.collection.mutable.{Map, HashMap}

object global {

  // one script could only have one game object
  // but we could possibly extends to support
  // multiple instances of game with HashMap
  var game: GamelGame = null;

  // prototypes store all entity types created by "define"
  val prototypes = new HashMap[Symbol, GamelType]

  // entities store all entity created by "created"
  val entities = new HashMap[Symbol, GamelInstance]

  // scenes store all scenes created by "scene"
  val scenes = new HashMap[Symbol, GamelScene]

  // all defined actions
  val actions = new HashMap[Symbol, GamelAction]

  // game resources
  val resources = new HashMap[String, GamelResource]

  // game front-end functionality support
  val listeners = HashMap[String, Boolean] (
    "KeyTyped"            -> false,
    "KeyPressed"          -> true,
    "KeyReleased"         -> false,
    "MouseClicked"        -> true,
    "MouseButtonEvent"    -> false,
    "MouseDragged"        -> false,
    "MouseEntered"        -> false,
    "MouseMotionEvent"    -> false,
    "MouseMoved"          -> false,
    "MousePressed"        -> false,
    "MouseReleased"       -> false,
    "MouseWheelMoved"     -> false
  )

}
