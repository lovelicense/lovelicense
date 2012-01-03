
package com.google.gwt.homepage.lovelicense.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.VersionStrategy;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.Version;



import javax.persistence.Entity;
import javax.persistence.EntityManager;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.homepage.lovelicense.shared.SajuDataTableProxy;


import com.google.web.bindery.requestfactory.shared.Request;

/**
 * 
 */
@PersistenceCapable

@Version(
    	strategy=VersionStrategy.VERSION_NUMBER,
    	column="version",
    	extensions={@Extension(vendorName="datanucleus",key="field-name",
   			value="version")}
    )
public class GuestBookTable {

	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	

		
	@Persistent
	private Integer version;
	
	
	@Persistent
	private String subject;//제목
	
	@Persistent
	private Date reg_dt;//등록일
	
	@Persistent
	private Date modi_dt;//수정일
	
	@Persistent
	private String email;//등록자
	
	@Persistent
	private int cnt;//조회수
	
	
	
	@Persistent
	private Text contents;//본문
	
	
	
	
	
	@Persistent
	private GuestBookAnswerTable answer;//답변
	
	 
	 
	 
	 
	

 
  
  public GuestBookAnswerTable getAnswer() {
		return answer;
	}


	public void setAnswer(GuestBookAnswerTable answer) {
		this.answer = answer;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public Date getReg_dt() {
		return reg_dt;
	}


	public void setReg_dt(Date reg_dt) {
		this.reg_dt = reg_dt;
	}


	public Date getModi_dt() {
		return modi_dt;
	}


	public void setModi_dt(Date modi_dt) {
		this.modi_dt = modi_dt;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getCnt() {
		return cnt;
	}


	public void setCnt(int cnt) {
		this.cnt = cnt;
	}


	


	public Text getContents() {
		return contents;
	}


	public void setContents(Text contents) {
		this.contents = contents;
	}


	public GuestBookTable() {}

  
	 public static GuestBookTable findGuestBookTable(Long id) {
		 //System.out.println("3333333333333333333333333");   

		     PersistenceManager pm = persistenceManager();
		   //  System.out.println("44444444444444444444");  
		     try {
		     	 
		    	 GuestBookTable guestBook = pm.getObjectById(GuestBookTable.class, id);
		     //	System.out.println("5555555555555555");  
		         return guestBook;
		      
		      
		     } finally {
		     	pm.close();
		     }
		   }

 
  
  /**
   * Persist this object in the data store.
   */
  public void persist() {
	  PersistenceManager pm = persistenceManager();
    try {
      // Set the user id if this is a new task.
    	//System.out.println("persist#################");
      String curUserId = UserServiceWrapper.get().getCurrentUser().getEmail();
      
      
      
      if (email == null) {//insert
    	  email = curUserId;
    	  reg_dt=new Date();
      }else{//modidt
    	  modi_dt=new Date();
      }

      
      // Verify the current user owns the task before updating it.
      if (curUserId.equals(email)) {
		 pm.makePersistent(this);//적용      
      }
    } finally {
      pm.close();
    }
  }
  
  
  /**
   * Persist this object in the data store.
   */
  public void updateCnt() {
	  PersistenceManager pm = persistenceManager();
    try {
      // Set the user id if this is a new task.
    	//System.out.println("persist#################");
      //String curUserId = UserServiceWrapper.get().getCurrentUser().getEmail();
      
      
      
      if (email == null) {//error
    	  ;
      }else{
    	  cnt=cnt+1;
      }

		 pm.makePersistent(this);//적용      
      
    } finally {
      pm.close();
    }
  }

  
  /**
   * Remove this object from the data store.
   */
  public void remove() {
	  PersistenceManager pm = persistenceManager();
    try {
      GuestBookTable guestBook = pm.getObjectById(GuestBookTable.class, id);

      // Verify the current user owns the task before removing it.
      if (UserServiceWrapper.get().getCurrentUser().getEmail().equals(guestBook.getEmail())) {
    	  pm.deletePersistent(guestBook);
      }
    } finally {
    	pm.close();
    }
  }
  
  
  /**
   * Find all tasks for the current user.
   */
  
  public static List<GuestBookTable> findAllGuestBooks() {
	  PersistenceManager pm = persistenceManager();
    try {
    	Query query = pm.newQuery("SELECT  FROM " + GuestBookTable.class.getName()+" order by redi_dt desc " );
     
    	List<GuestBookTable> guestBookList = (List<GuestBookTable>)query.execute();
     

      /*
       * If this is the first time running the app, populate the datastore with
       * some default tasks and re-query the datastore for them.
       */
      if (guestBookList.size() == 0) {
        populateDatastore();
        guestBookList = (List<GuestBookTable>)query.execute();
      }

      return guestBookList;
    } finally {
      pm.close();
    }
  }

  
  /**
   * Populate the datastore with some default tasks. We do this to make the app
   * more intuitive on first use.
   */
  
  private static void populateDatastore() {
    {
      // Task 0.
      GuestBookTable task0 = new GuestBookTable();
      task0.setSubject("홈페이지가 너무 좋네요0");
      task0.setEmail("0321@naver.com");
      Text contents0= new Text("홈페이지가 너무 좋네요. 감사합니다.0") ;
      task0.setContents(contents0);
      
      GuestBookAnswerTable answer0 = new GuestBookAnswerTable();
      answer0.setEmail("answer@naver.com");
      Text contents00= new Text("홈페이지가 너무 좋네요. 감사합니다.0") ;
      answer0.setContents(contents00);
      answer0.persist();
      task0.setAnswer(answer0);
      
      task0.persist();
    }
    {
      // Task 1.
    	GuestBookTable task1 = new GuestBookTable();
        task1.setSubject("홈페이지가 너무 좋네요1");
        task1.setEmail("1321@naver.com");
        Text contents1= new Text("홈페이지가 너무 좋네요. 감사합니다.1") ;
        task1.setContents(contents1);
        
        GuestBookAnswerTable answer1 = new GuestBookAnswerTable();
        answer1.setEmail("answer@naver.com");
        Text contents01= new Text("홈페이지가 너무 좋네요. 감사합니다.1") ;
        answer1.setContents(contents01);
        answer1.persist();
        task1.setAnswer(answer1);
        
        task1.persist();
    }
    {
      // Task 2.
    	GuestBookTable task2 = new GuestBookTable();
        task2.setSubject("홈페이지가 너무 좋네요2");
        task2.setEmail("2321@naver.com");
        Text contents2= new Text("홈페이지가 너무 좋네요. 감사합니다.2") ;
        task2.setContents(contents2);
        
        GuestBookAnswerTable answer2 = new GuestBookAnswerTable();
        answer2.setEmail("answer@naver.com");
        Text contents02= new Text("홈페이지가 너무 좋네요. 감사합니다.2") ;
        answer2.setContents(contents02);
        answer2.persist();
        task2.setAnswer(answer2);
        
        task2.persist();
    }
    {
      // Task 3.
    	GuestBookTable task3 = new GuestBookTable();
        task3.setSubject("홈페이지가 너무 좋네요3");
        task3.setEmail("3321@naver.com");
        Text contents3= new Text("홈페이지가 너무 좋네요. 감사합니다.3") ;
        task3.setContents(contents3);
        
        GuestBookAnswerTable answer3 = new GuestBookAnswerTable();
        answer3.setEmail("answer@naver.com");
        Text contents03= new Text("홈페이지가 너무 좋네요. 감사합니다.3") ;
        answer3.setContents(contents03);
        answer3.persist();
        task3.setAnswer(answer3);
        
        task3.persist();
    }
  }
  
  
  
  /**
   * Create an entity manager to interact with the database.
   * 
   * @return an {@link EntityManager} instance
   */
  private static PersistenceManager persistenceManager() {
    return PMF.get().getPersistenceManager();
  }

  
 

	
  
	
	
	
	
	
	
	
	
	
	    
  
}
