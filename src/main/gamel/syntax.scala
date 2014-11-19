package gamel

import java.awt.Image
import scala.collection.mutable.{Map, HashMap}

/**
 * 'define' creates the prototype of objects
 * e.g.
 *  define a new entity {
 *    ...
 *  }
 * */
object define {

  def a(ent: GamelType): GamelType = {
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

  def a(action: GamelAction): GamelAction = {
    if (action == null)
      throw new DefinitionException("the action cannot be null")
    if (action.name == null)
      throw new DefinitionException("the action name cannot be null")
    if (action.action == null)
      throw new DefinitionException("the aciton function cannot be null")
    if (global.actions contains action.name)
      throw new DefinitionException("the action " + action.name + " has already been defined!")

    global.actions(action.name) = action
    action
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

  def a(ent: GamelInstance): GamelInstance = {
    // check if the entity is null
    if (ent == null)
      throw new CreationException("the entity cannot be null")
    if (ent.name == null)
      throw new CreationException("the name of the entity cannot be null")
    if (global.entities contains ent.name)
      throw new CreationException("the entne " + ent.name + " has already been defined!")

    // save the instance to the global scope
    global.entities(ent.name) = ent

    // mark this instance as defined
    nobody define ent.name

    // mark all of its objects as used
    ent.objects foreach {
      obj => if(nobody isUsed obj._1){
        throw new CreationException(obj._1 + " already belongs to someone")
      } else {
        nobody use obj._1
      }
    }

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

    // mark all of its objects as used
    sce.objects foreach {
      obj => if(nobody isUsed obj._1){
        throw new CreationException(obj._1 + " already belongs to someone")
      } else {
        nobody use obj._1
      }
    }

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

object start {
  def game(): Unit = {
    gamel.start()
  }
}

object require {

  def image(uri: String): GamelImage = new GamelImage(uri)

  // possibly we can use sound, etc
}

object use {

  def entity(name: Symbol): GamelInstance = {
    if (name == null)
      throw new IllegalArgumentException("the entity name to search for could not be null")

    if (global.entities contains name) {
      return global.entities(name)
    }
    else {
      throw new UndefinedInstanceException("the entity " + name + " has not been found")
    }
  }

  def image(name: String): Image = {
    if (global.resources contains name) {
      val res = global.resources(name)
      val img = res.asInstanceOf[GamelImage]
      if (res == img) {
        return res.resource.asInstanceOf[Image]
      }
      else {
        throw new IllegalStateException(name + " is not an imager!")
      }
    }
    else {
        throw new IllegalStateException(name + " has not been found in resources!")
    }
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
  def MouseDragged     = (status: Boolean) => global.listeners("MouseDragged")       = status
  def MouseEntered     = (status: Boolean) => global.listeners("MouseEntered")       = status
  def MouseMoved       = (status: Boolean) => global.listeners("MouseMoved")         = status
  def MousePressed     = (status: Boolean) => global.listeners("MousePressed")       = status
  def MouseReleased    = (status: Boolean) => global.listeners("MouseReleased")      = status
  def MouseWheelMoved  = (status: Boolean) => global.listeners("MouseWheelMoved")    = status
}

