package gamel

import scala.collection.mutable.{Map, HashMap}

// syntax:
//  'a does 'action using (p1, p2, p3, ...)

protected[gamel] abstract class GamelAction {

  /**
   * return the name of this entity
   * @return Symbol
   * */
  protected[gamel] var name: Symbol = null

  var init: Unit => Unit = null

  protected[gamel] var condition: Unit => Boolean = null
  protected[gamel] var action: List[Any] => Unit = null
  protected[gamel] var renderer: GamelRenderer = null

  def using (params: Any*): Unit = {
    action(List(params:_*))
  }
}

class action extends GamelAction { }
