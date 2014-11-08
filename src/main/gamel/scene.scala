package gamel;

// represents a scene instantiation
protected[gamel] class Scene(n: Symbol) {
    def scene_name = n
    def as(inner: Property*) = {
        //check that all properties are only HasObj
        println ("creating a scene with has-es")
    }
}

