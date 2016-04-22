package datatypes

case class EnumType[T] (text: String, name: String, code: T) {
	override def toString: String = name
}