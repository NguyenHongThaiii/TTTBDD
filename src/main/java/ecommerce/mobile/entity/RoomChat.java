package ecommerce.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roomchats")
public class RoomChat extends BaseEntity {
	private String roomname;
	private String url;

	public RoomChat() {
		// TODO Auto-generated constructor stub
	}

	public RoomChat(String roomname, String url) {
		super();
		this.roomname = roomname;
		this.url = url;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
