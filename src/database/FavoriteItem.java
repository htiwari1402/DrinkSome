package database;

public class FavoriteItem {
	String id;
	String title;
	String image;
	String type;
	public FavoriteItem(String id,String title, String image, String type) {
		super();
		this.id=id;
		this.title = title;
		this.image = image;
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public FavoriteItem() {
		super();
	}
	public FavoriteItem(String title) {
		super();
		this.title = title;
	}
	

}
