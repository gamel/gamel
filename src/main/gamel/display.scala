package gamel

trait Display {
  // support for fullscreen
  def isFullScreenSupported(): Boolean

  // support for listeners
  def isKeyTypedSupported(): Boolean
  def isKeyPressedSupported(): Boolean
  def isKeyReleasedSupported(): Boolean
  def isMouseClickedSupported(): Boolean
  def isMouseDraggedSupported(): Boolean
  def isMouseEnteredSupported(): Boolean
  def isMouseMovedSupported(): Boolean
  def isMousePressedSupported(): Boolean
  def isMouseReleasedSupported(): Boolean
  def isMouseWheelMovedSupported(): Boolean

  // window operation
  def launch(): Unit
  def close(): Unit
  def repaint(): Unit
}
