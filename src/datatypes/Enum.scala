package datatypes

/**
 * Because I don't fully trust (understand how to use?) Scala Enumeration. 
 */
abstract class Enum[T <: EnumType[_]] {
	val values: List[T]
	lazy val texts = values.map (_.text)
	lazy val textValueMap = (texts zip values).toMap
	lazy val codeValueMap = values.map (value => (value.code, value)).toMap
	
	def valuesToString: String = {
		values.mkString ("{", ",", "}")
	}
	
	def forString (str: String): T =
		textValueMap.getOrElse (
			str, {
				System.err.println ("Warning: Unkown "+this+" \""+str+"\".")
				values.head
			}
		)
		
	def forCode (code: Int): T =
	  	codeValueMap.getOrElse (
	  		code, {
	  			System.err.println ("Warning: Unkown "+this+" code \""+code+"\".")
				values.head
	  		}
	  	)
}