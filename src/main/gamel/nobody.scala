package gamel

/**
 * Defines the nobody instance, which gets implicit
 * ownership of every newly created, unowned object.
 *
 * nobody is an entity, but it does not get a symbolic
 * name.
 */
object nobody extends GamelInstance {
  override def to(newParent: Symbol): Unit = {
    throw new IllegalArgumentException("Cannot give nobody");
  }
}
