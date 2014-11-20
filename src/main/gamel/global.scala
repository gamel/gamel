package gamel

import scala.collection.mutable.{Map, HashMap}

protected[gamel] object global {

  // one script could only have one game object
  // but we could possibly extends to support
  // multiple instances of game with HashMap
  protected[gamel] var game: GamelGame = null;

  // prototypes store all entity types created by "define"
  protected[gamel] val prototypes = new HashMap[Symbol, GamelType]

  // entities store all entity created by "created"
  protected[gamel] val entities = new HashMap[Symbol, GamelInstance]

  // scenes store all scenes created by "scene"
  protected[gamel] val scenes = new HashMap[Symbol, GamelScene]

  // all defined actions
  protected[gamel] val actions = new HashMap[Symbol, GamelAction]

  // game resources
  protected[gamel] val resources = new HashMap[String, GamelResource]

  // game front-end functionality support
  protected[gamel] val listeners = HashMap[String, Boolean] (
    "KeyTyped"            -> false,
    "KeyPressed"          -> true,
    "KeyReleased"         -> false,
    "MouseClicked"        -> true,
    "MouseDragged"        -> false,
    "MouseEntered"        -> false,
    "MouseMoved"          -> false,
    "MousePressed"        -> false,
    "MouseReleased"       -> false,
    "MouseWheelMoved"     -> false
  )

}
