package gamel

import scala.swing._
import scala.swing.event._

protected[gamel] abstract class GamelEvent {
  protected[gamel] var name: String = null

  override def toString = name
}

class GamelKeyEvent extends GamelEvent{
  // key event
  var char: Char = null.asInstanceOf[Char]
  var key: Key.Value = null
  var modifiers: Int = null.asInstanceOf[Int]
  var location: Key.Location.Value = null
}


class GamelMouseEvent extends GamelEvent{
  // key event
  var point: Point = null
  var modifiers: Int = null.asInstanceOf[Int]
  var location: Key.Location.Value = null
  var clicks: Int = null.asInstanceOf[Int]
  var rotation: Int = null.asInstanceOf[Int]
}
