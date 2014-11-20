package gamel

import scala.collection.mutable.{Map, HashMap}

// syntax:
//  'a does 'action using (p1, p2, p3, ...)

abstract class GamelAction {

  /**
   * return the name of this entity
   * @return Symbol
   * */
  var name: Symbol = null

  var init: Unit => Unit = null

  var condition: Unit => Boolean = null
  var action: List[Any] => Unit = null
  var renderer: GamelRenderer = null

  def using (params: Any*): Unit = {
    action(List(params:_*))
  }
}

class action extends GamelAction { }
