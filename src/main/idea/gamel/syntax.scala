package idea.gamel

import scala.collection.mutable.{Map, HashMap}

/**
 * 'define' creates the prototype of objects
 * e.g.
 *  define a new entity {
 *    ...
 *  }
 * */
object define {

  def a(ent: GamelEntity): GamelEntity = {
    // check entity is not nul
    if (ent == null)
      throw new DefinitionException("the entity cannot be null")
    // check whether the name of the entity is empty
    if (ent.name == null)
      throw new DefinitionException("the name of the entity cannot be null")
    // check whether this type of entity has already been added to global.prototype
    if (global.prototypes contains ent.name)
      throw new DefinitionException("the entity " + ent.name + " has already been defined!")

    global.prototypes(ent.name) = ent
    ent
  }

}

/**
 * 'create' instantiates an entity or scene
 * e.g.
 *  create a new entity {
 *    ...
 *  }
 * */
object create {

  def a(ent: GamelEntity): GamelEntity = {
    // check if the entity is null
    if (ent == null)
      throw new CreationException("the entity cannot be null")
    if (ent.name == null)
      throw new CreationException("the name of the entity cannot be null")
    if (global.entities contains ent.name)
      throw new CreationException("the entne " + ent.name + " has already been defined!")

    global.entities(ent.name) = ent
    ent
  }

  def a(sce: GamelScene): GamelScene = {
    // check if the scene is null
    if (sce == null)
      throw new CreationException("the scene cannot be null")
    if (sce.name == null)
      throw new CreationException("the name of the scene cannot be null")
    if (global.scenes contains sce.name)
      throw new CreationException("the scene " + sce.name + " has already been defined!")

    // save the scene to global scene scope
    global.scenes(sce.name) = sce
    sce
  }

  def a(game: GamelGame): GamelGame = {
    // check if the game is null
    if (game == null)
      throw new CreationException("the game cannot be null")

    // check if game has already been created 
    if (global.game != null)
      throw new CreationException("the game has already been created")

    // check if the game has a name
    if (game.name == null)
      throw new CreationException("the game does not have a name")

    // check if the game know where to start
    if (game.startScene == null)
      throw new CreationException("the game does not know where to start (aka. the game needs a starting scene)")
    
    // save the game to global scope
    global.game = game
    game
  }

}

/**
 * 'create' configures the status of 
 * the game
 * e.g.
 *  create a new game {
 *    ...
 *  }
 * */
object start { 

  def game(): Unit = {
    gamel.start()
  }

}

/**
 * leave the possibility that we might support user defined plug-ins
 * although very unlikely
 * */
// object use {
// }

/**
 * support for functionalities
 * */
object turn {
  def KeyTyped         = (status: Boolean) => global.listeners("KeyTyped")           = status
  def KeyReleased      = (status: Boolean) => global.listeners("KeyReleased")        = status
  def KeyPressed       = (status: Boolean) => global.listeners("KeyPressed")         = status
  def MouseClicked     = (status: Boolean) => global.listeners("MouseClicked")       = status
  def MouseButtonEvent = (status: Boolean) => global.listeners("MouseButtonEvent")   = status
  def MouseDragged     = (status: Boolean) => global.listeners("MouseDragged")       = status
  def MouseEntered     = (status: Boolean) => global.listeners("MouseEntered")       = status
  def MouseMotionEvent = (status: Boolean) => global.listeners("MouseMotionEvent")   = status
  def MouseMoved       = (status: Boolean) => global.listeners("MouseMoved")         = status
  def MousePressed     = (status: Boolean) => global.listeners("MousePressed")       = status
  def MouseReleased    = (status: Boolean) => global.listeners("MouseReleased")      = status
  def MouseWheelMoved  = (status: Boolean) => global.listeners("MouseWheelMoved")    = status
}
