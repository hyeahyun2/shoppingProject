package market.dto;
/*
 * CREATE TABLE p_board(
	num INT NOT NULL AUTO_INCREMENT,
	id VARCHAR(10) NOT NULL,
	NAME varchar(10) NOT NULL,
	SUBJECT VARCHAR(100) NOT NULL,
	content TEXT NOT NULL,
	regist_day VARCHAR(30),
	hit INT,
	ip VARCHAR(20),
	PRIMARY KEY(num)
	)DEFAULT CHARSET=UTF8;
 * */
/**
 * int num
 * */
public class BoardDto {
	private int num;
	private String id;
	private String name;
	private String subject;
	private String content;
	private String registDay;
	private int hit;
	private String ip;
	
	// getter setter
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegistDay() {
		return registDay;
	}
	public void setRegistDay(String registDay) {
		this.registDay = registDay;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
