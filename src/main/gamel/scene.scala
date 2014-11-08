package gamel;

// represents a scene instantiation
protected[gamel] class Scene(n: Symbol) extends Thing {
    val scene_name = n
    var properties: List[HasObj] = List()    
    def as(inner: Property*) = {
        val props = List(inner:_*)

        // check each property to be a has and not an action
        for(p <- props) {
            p match {
                case h: HasObj => properties :+ h.asInstanceOf[HasObj]
                case _ => throw new InstantiationException("Cannot add actions to a scene")
            }
        }
    }
}

