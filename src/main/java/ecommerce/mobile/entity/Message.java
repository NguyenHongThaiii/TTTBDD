package ecommerce.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
	private String messageContent;
	@ManyToOne(fetch = FetchType.EAGER)
	private RoomChat roomchat;
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentMessage")
	private Message parentMessage;
	private String file;
	private String suffix;

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public Message(String messageContent, RoomChat roomchat, User user, Message parentMessage, String file,
			String suffix) {
		super();
		this.messageContent = messageContent;
		this.roomchat = roomchat;
		this.user = user;
		this.parentMessage = parentMessage;
		this.file = file;
		this.suffix = suffix;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public RoomChat getRoomchat() {
		return roomchat;
	}

	public void setRoomchat(RoomChat roomchat) {
		this.roomchat = roomchat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Message getParentMessage() {
		return parentMessage;
	}

	public void setParentMessage(Message parentMessage) {
		this.parentMessage = parentMessage;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
