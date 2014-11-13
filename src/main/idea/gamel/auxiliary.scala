package idea.gamel

/**
 * Gamel Auxiliary Object
 * This is a trait for helper 
 * objects for the game
 * e.g. A Scene Renderer needs to load image, 
 * sound resources before actually starting 
 * the game
 * 
 * Currently GamelAuxiliary will only be called
 * when we want to initialize something
 * */
trait GamelAuxiliary {

  def init()

}
