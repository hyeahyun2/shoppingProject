package market.dto;

/*	CREATE TABLE p_ripple(
	`rippleId` INT NOT NULL AUTO_INCREMENT,
	`boardName` VARCHAR(10) NOT NULL,
	`boardNum` INT NOT NULL,
	`memberId` VARCHAR(10) NOT NULL,
	`name` VARCHAR(10) NOT NULL,
	`content` TEXT NOT NULL,
	`insertDate` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`ip` VARCHAR(20),
	PRIMARY KEY(`rippleId`)
	)DEFAULT CHARSET=UTF8;
 * */
public class RippleDto {
	private int rippleId;
	private String boardName;
	private int boardNum;
	private String memberId;
	private String name;
	private String content;
	private String insertDate;
	private String ip;

	// getter setter
	public int getRippleId() {
		return rippleId;
	}
	public void setRippleId(int rippleId) {
		this.rippleId = rippleId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
