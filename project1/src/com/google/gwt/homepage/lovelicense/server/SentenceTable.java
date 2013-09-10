
package com.google.gwt.homepage.lovelicense.server;

import java.text.SimpleDateFormat;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
public class SentenceTable {

	
	//@PrimaryKey
    //@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    //private Long id;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
		
	
	




	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



	@Persistent
	private Integer version;
	
	

	@Persistent
	private String gubun;//구분 : 1(단독), 2(passage)
	
	@Persistent
	private String passage_key;//passage 번호(key)
	
	@Persistent
	private int passage_seq;//passage내 순번
	
	@Persistent
	private String passage_subject;//passage 주제
	
	
	public String getPassage_subject() {
		return passage_subject;
	}


	public void setPassage_subject(String passage_subject) {
		this.passage_subject = passage_subject;
	}



	@Persistent
	@NotNull(message = "내용을 입력하세요")
	@Size(min = 3, message = "3자리 이상 입력하세요")
	private String sentenceToStudy;//외국어문장
	
	
	@Persistent
	@NotNull(message = "제목을 입력하세요")
	@Size(min = 3, message = "제목은 3자리 이상 입력하세요")
	private String sentenceTranslated;//번역된문장
	
	
	@Persistent
	private int study_cnt;//공부횟수
	
	@Persistent
	private int succese_cnt;//성공횟수
	
	@Persistent
	private Date last_study_dt;//최종학습일
	
	@Persistent
	private Date reg_dt;//등록일
	
	
	@Persistent
	private Date modi_dt;//수정일
	
	
	public String getReg_dt_yyyymmdd() {
		// A custom date format
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String s = "";
		if(reg_dt!=null)
			s = formatter.format(reg_dt);
		else
			;
		return s;
	}

	public String getModi_dt_yyyymmdd() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String s = "";
		if(modi_dt!=null)
			s = formatter.format(modi_dt);
		else
			;
		return s;
	}
	
	//오류가 나지 않도록 아무액션없는 값으로 함.
	public void setModi_dt_yyyymmdd(String yyyymmdd){
		;
	}
	//오류가 나지 않도록 아무액션없는 값으로 함.
	public void setReg_dt_yyyymmdd(String yyyymmdd){
		;
	}
	




	@Persistent
	private String email;//등록자
	
	
	
	


	public SentenceTable() {}

  
	/* 해당하는 게시글 가져옴*/
	public static SentenceTable findSentenceTable(String id) {
		 //System.out.println("3333333333333333333333333");   

		     PersistenceManager pm = persistenceManager();
		     System.out.println("find"+id);  
		     try {
		     	 
		    	 SentenceTable sentence = pm.getObjectById(SentenceTable.class, id);
		     //	System.out.println("5555555555555555");  
		         return sentence;
		      
		      
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
      
      String curUserId = UserServiceWrapper.get().getCurrentUser().getEmail();
      
      /*
      if(answerWriteUpdateGubun==0){//질문 등록 및 수정의 경우
	      if (email == null) {//질문 insert
	    	  email = curUserId;
	    	  reg_dt=new Date();
	    	  modi_dt=new Date();
	    	  System.out.println("질문 등록");
	      }else{//질문 modidt
	    	  modi_dt=new Date();
	      }
      }else if(answerWriteUpdateGubun==1){//답변 등록의 경우
    	  answer.setReg_dt(new Date());
    	  answer.setModi_dt(new Date());
    	  System.out.println("답변 등록");
      }else if(answerWriteUpdateGubun==2){//답변 수정의 경우
    	  answer.setModi_dt(new Date());
      }
	  */
      
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
  public void persistCreate() {
	  PersistenceManager pm = persistenceManager();
    try {
      // Set the user id if this is a new task.
    	//System.out.println("persist#################");
      String curUserId = UserServiceWrapper.get().getCurrentUser().getEmail();
      
      
     
	   
	    	  email = curUserId;
	    	  
	    	//  TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
          	//Calendar oCalendar = Calendar.getInstance(tz);  // 현재 날짜/시간 등의 각종 정보 얻기
         
	    	  reg_dt=new Date();
	    	  modi_dt=new Date();
	    	  
	     

      
      // Verify the current user owns the task before updating it.
      if (curUserId.equals(email)) {
    	  
		 pm.makePersistent(this);//적용      
      }
    } finally {
      pm.close();
    }
  }

  //방명록 수정의 경우
  public void persistEdit() {
	  PersistenceManager pm = persistenceManager();
    try {
      // Set the user id if this is a new task.
    	
      String curUserId = UserServiceWrapper.get().getCurrentUser().getEmail();
      
      modi_dt=new Date();
      
     
     
      
      // Verify the current user owns the task before updating it.
      if (curUserId.equals(email)) {
    	  
		 pm.makePersistent(this);//적용      
      }
    } finally {
      pm.close();
    }
  }
  
 

  
  public Integer getVersion() {
	return version;
}


public void setVersion(Integer version) {
	this.version = version;
}


public String getGubun() {
	return gubun;
}


public void setGubun(String gubun) {
	this.gubun = gubun;
}


public String getPassage_key() {
	return passage_key;
}


public void setPassage_key(String passage_key) {
	this.passage_key = passage_key;
}


public int getPassage_seq() {
	return passage_seq;
}


public void setPassage_seq(int passage_seq) {
	this.passage_seq = passage_seq;
}


public String getSentenceToStudy() {
	return sentenceToStudy;
}


public void setSentenceToStudy(String sentenceToStudy) {
	this.sentenceToStudy = sentenceToStudy;
}


public String getSentenceTranslated() {
	return sentenceTranslated;
}


public void setSentenceTranslated(String sentenceTranslated) {
	this.sentenceTranslated = sentenceTranslated;
}


public int getStudy_cnt() {
	return study_cnt;
}


public void setStudy_cnt(int study_cnt) {
	this.study_cnt = study_cnt;
}


public int getSuccese_cnt() {
	return succese_cnt;
}


public void setSuccese_cnt(int succese_cnt) {
	this.succese_cnt = succese_cnt;
}


public Date getLast_study_dt() {
	return last_study_dt;
}


public void setLast_study_dt(Date last_study_dt) {
	this.last_study_dt = last_study_dt;
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


/**
   * 게시물 삭제
   */
  public void remove() {
	  PersistenceManager pm = persistenceManager();
    try {
      GuestBookTable guestBook = pm.getObjectById(GuestBookTable.class, this.id);

      // Verify the current user owns the task before removing it.
      if (UserServiceWrapper.get().getCurrentUser().getEmail().equals(guestBook.getEmail())) {
    	  pm.deletePersistent(guestBook);
      }
    } finally {
    	pm.close();
    }
  }
  
  
  /**
   * 방명록 리스트 전체를 가져옴
   */
  /*
  public static List<GuestBookTable> findAllGuestBooks() {
	  PersistenceManager pm = persistenceManager();
    try {
    	Query query = pm.newQuery("SELECT  FROM " + GuestBookTable.class.getName()+" order by reg_dt desc " );
    	System.out.println("findAllGuestBooks"+query.toString());
    	List<GuestBookTable> guestBookList = (List<GuestBookTable>)query.execute();
    	System.out.println("findAllGuestBooks-end"+guestBookList.size());

    
    	
    	//geustBookList를 곧바로 참고하면 오류가 발생함/
    	//List<GuestBookTable> detached = pm.detachCopy(guestBookList);
    	
    	 //for(int x=0;x<guestBookList.size();x++)
         	//System.out.println("resultServer"+guestBookList.get(x).getAnswer().getContents());

    	
    	
      //if (guestBookList.size() == 0) {
        //populateDatastore();
        //guestBookList = (List<GuestBookTable>)query.execute();
      //}

      return guestBookList;
    } finally {
      pm.close();
    }
  }
*/
  
  /**
   * Populate the datastore with some default tasks. We do this to make the app
   * more intuitive on first use.
   */
  
  private static void populateDatastore() {
    {
    	System.out.println("populateDatastore");
      // Task 0.
      GuestBookTable task0 = new GuestBookTable();
      task0.setSubject("홈페이지가 너무 좋네요0");
     // task0.setEmail("test@gmail.com");
      task0.setReg_dt(new Date());
      task0.setModi_dt(new Date());
      String contents0= new String("홈페이지가 너무 좋네요. 감사합니다.0") ;
      task0.setContents(contents0);
      
      GuestBookAnswerTable answer0 = new GuestBookAnswerTable();
      answer0.setEmail("test@gmail.com");
      String contents00= new String("홈페이지가 너무 좋네요. 감사합니다.0") ;
      answer0.setContents(contents00);
      answer0.setReg_dt(new Date());
      answer0.setModi_dt(new Date());
      //answer0.persist();
      task0.setAnswer(answer0);
      
      task0.persist();
    }
    {
      // Task 1.
    	GuestBookTable task1 = new GuestBookTable();
        task1.setSubject("홈페이지가 너무 좋네요1");
        //task1.setEmail("test@gmail.com");
        task1.setReg_dt(new Date());
        task1.setModi_dt(new Date());
        String contents1= new String("홈페이지가 너무 좋네요. 감사합니다.1") ;
        task1.setContents(contents1);
        
        GuestBookAnswerTable answer1 = new GuestBookAnswerTable();
        answer1.setEmail("test@gmail.com");
        String contents01= new String("홈페이지가 너무 좋네요. 감사합니다.1") ;
        answer1.setContents(contents01);
        answer1.setReg_dt(new Date());
        answer1.setModi_dt(new Date());
       // answer1.persist();
        task1.setAnswer(answer1);
        
        task1.persist();
    }
    {
      // Task 2.
    	GuestBookTable task2 = new GuestBookTable();
        task2.setSubject("홈페이지가 너무 좋네요2");
        //task2.setEmail("test@gmail.com");
        task2.setReg_dt(new Date());
        task2.setModi_dt(new Date());
        String contents2= new String("홈페이지가 너무 좋네요. 감사합니다.2") ;
        task2.setContents(contents2);
        
        GuestBookAnswerTable answer2 = new GuestBookAnswerTable();
        answer2.setEmail("test@gmail.com");
        String contents02= new String("홈페이지가 너무 좋네요. 감사합니다.2") ;
        answer2.setContents(contents02);
        answer2.setReg_dt(new Date());
        answer2.setModi_dt(new Date());
       // answer2.persist();
        task2.setAnswer(answer2);
        
        task2.persist();
    }
    {
      // Task 3.
    	GuestBookTable task3 = new GuestBookTable();
        task3.setSubject("홈페이지가 너무 좋네요3");
        //task3.setEmail("test@gmail.com");
        task3.setReg_dt(new Date());
        task3.setModi_dt(new Date());
        String contents3= new String("홈페이지가 너무 좋네요. 감사합니다.3") ;
        task3.setContents(contents3);
        
        GuestBookAnswerTable answer3 = new GuestBookAnswerTable();
        answer3.setEmail("test@gmail.com");
        String contents03= new String("홈페이지가 너무 좋네요. 감사합니다.3") ;
        answer3.setContents(contents03);
        answer3.setReg_dt(new Date());
        answer3.setModi_dt(new Date());
       // answer3.persist();
        task3.setAnswer(answer3);
        
        task3.persist();
    }
    
    {
        // Task 4.
      	GuestBookTable task4 = new GuestBookTable();
          task4.setSubject("홈페이지가 너무 좋네요4");
          //task4.setEmail("test@gmail.com");
          task4.setReg_dt(new Date());
          task4.setModi_dt(new Date());
          String contents4= new String("홈페이지가 너무 좋네요. 감사합니다.4") ;
          task4.setContents(contents4);
          
          GuestBookAnswerTable answer4 = new GuestBookAnswerTable();
          answer4.setEmail("test@gmail.com");
          String contents04= new String("홈페이지가 너무 좋네요. 감사합니다.4") ;
          answer4.setContents(contents04);
          answer4.setReg_dt(new Date());
          answer4.setModi_dt(new Date());
         // answer4.persist();
          task4.setAnswer(answer4);
          
          task4.persist();
      }
    
    
    {
        // Task 5.
      	GuestBookTable task5 = new GuestBookTable();
          task5.setSubject("홈페이지가 너무 좋네요5");
          //task5.setEmail("test@gmail.com");
          task5.setReg_dt(new Date());
          task5.setModi_dt(new Date());
          String contents5= new String("홈페이지가 너무 좋네요. 감사합니다.5") ;
          task5.setContents(contents5);
          
          GuestBookAnswerTable answer5 = new GuestBookAnswerTable();
          answer5.setEmail("test@gmail.com");
          String contents05= new String("홈페이지가 너무 좋네요. 감사합니다.5") ;
          answer5.setContents(contents05);
          answer5.setReg_dt(new Date());
          answer5.setModi_dt(new Date());
         // answer5.persist();
          task5.setAnswer(answer5);
          
          task5.persist();
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
