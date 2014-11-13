package idea.gamel

import scala.collection.mutable.{Map, HashMap}

object define {

  def a(ent: GamelEntity): GamelEntity = {
    // check entity is not nul
    if (ent == null)
      throw new IllegalArgumentException("the entity cannot be null")
    // check whether the name of the entity is empty
    if (ent.name == null)
      throw new IllegalStateException("the name of the entity cannot be null")
    // check whether this type of entity has already been added to global.prototype
    if (global.prototypes contains ent.name)
      throw new IllegalStateException("the entity " + ent.name + " has already been defined!")

    global.prototypes(ent.name) = ent
    ent
  }

}

object create {

  def a(ent: GamelEntity): GamelEntity = {
    // check if the entity is null
    if (ent == null)
      throw new IllegalArgumentException("the entity cannot be null")
    if (ent.name == null)
      throw new IllegalStateException("the name of the entity cannot be null")
    if (global.entities contains ent.name)
      throw new IllegalStateException("the entne " + ent.name + " has already been defined!")
    // MISSING()
    ent
  }

  def a(sce: GamelScene): GamelScene = {
    // check if the scene is null
    if (sce == null)
      throw new IllegalArgumentException("the scene cannot be null")
    if (sce.name == null)
      throw new IllegalStateException("the name of the scene cannot be null")
    if (global.scenes contains sce.name)
      throw new IllegalStateException("the scene " + sce.name + " has already been defined!")

    // save the scene to global scene scope
    global.scenes(sce.name) = sce
    sce
  }

  def a(game: GamelGame): GamelGame = {
    // check if the game is null
    if (game == null)
      throw new IllegalArgumentException("the game cannot be null")

    // check if game has already been created 
    if (global.game != null)
      throw new IllegalStateException("the game has already been created")

    // check if the game has a name
    if (game.name == null)
      throw new IllegalStateException("the game does not have a name")

    // check if the game know where to start
    if (game.startScene == null)
      throw new IllegalStateException("the game does not know where to start (aka. the game needs a starting scene)")
    
    // save the game to global scope
    global.game = game

    game
  }

}
