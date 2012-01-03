package com.google.gwt.homepage.lovelicense.server;



import java.io.Serializable;



import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class FriendTable  implements  Comparable<FriendTable>{
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String i_email="";//이메일
	
	@Persistent
	private String friend_email="";//친구이메일
	
	@Persistent
	private String addr1="";//주소
	
	@Persistent
	private String sex="";//남여
	
	@Persistent
	private String job="";//하는 일
	
	@Persistent
	private Integer score=0;//궁합 점수
	 
	

	
	
	 public int compareTo(FriendTable o) {
	    return (o.getScore()).compareTo((Integer)score);
	 }
	
	
	
	 

	
	public Key getKey() {
		return key;
	}






	public void setKey(Key key) {
		this.key = key;
	}






	public String getI_email() {
		return i_email;
	}

	public void setI_email(String i_email) {
		this.i_email = i_email;
	}

	public String getFriend_email() {
		return friend_email;
	}

	public void setFriend_email(String friend_email) {
		this.friend_email = friend_email;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}




	public String getAddr1() {
		return addr1;
	}




	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}




	public String getSex() {
		return sex;
	}




	public void setSex(String sex) {
		this.sex = sex;
	}




	public String getJob() {
		return job;
	}




	public void setJob(String job) {
		this.job = job;
	}
	
		
}
