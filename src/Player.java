public class Player {
	private int id;
	public String name;
	public transient String favGame;
	
	public Player() {
	}
	
	public Player(int id, String name, String favGame) {
		this.id      = id;
		this.name    = name;
		this.favGame = favGame;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFavGame() {
		return favGame;
	}
	
	public void setFavGame(String favGame) {
		this.favGame = favGame;
	}
	
	@Override
	public String toString() {
		return "Player{" + "id=" + id + ", name='" + name + '\'' + ", favGame='" + favGame + '\'' + '}';
	}
}
