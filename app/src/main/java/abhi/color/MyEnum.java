package abhi.color;

public class MyEnum {

	public enum Numbers{
		
		FIRST(1), SECOND(2), THIRD(3);
		
		private int value;
		
		private Numbers(int value){
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
	}
}
