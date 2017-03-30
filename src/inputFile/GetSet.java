package inputFile;

public class GetSet {
	private int Age;
	private String Name;
	
	public int getAge() {
		return Age;
	}
	
	public void setAge(int age) {
		this.Age = age;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public static void main(String[] args) {
		GetSet gs = new GetSet();
		gs.setAge(10);
		
		gs.setName("amar");
		System.out.println(gs.getName());
	}

}
