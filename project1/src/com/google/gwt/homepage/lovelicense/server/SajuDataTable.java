/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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


//import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.EntityManager;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.gwt.homepage.lovelicense.shared.SajuDataTableProxy;
import com.google.web.bindery.requestfactory.shared.Request;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

/**
 * 
 */
@PersistenceCapable
//@Entity
//@Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION",
  //      extensions={@Extension(vendorName="datanucleus", key="field-name", value="myVersion")})
@Version(
    	strategy=VersionStrategy.VERSION_NUMBER,
    	column="version",
    	extensions={@Extension(vendorName="datanucleus",key="field-name",
   			value="version")}
    )
public class SajuDataTable {

	@Persistent
	private List<FriendTable> friends=new ArrayList<FriendTable>();
	
	@NotPersistent
	static String chungan[]={"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
	
	@NotPersistent
	static String jigan[]={"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};
	
	@NotPersistent
	static String yoyak_desc[]={"자존심이 강하고 독선적이라 외부 세계와 자신의 세계를 구분해서 살아가야 하므로 급파장이 급니다","내강외유의 성격을 가지셨네요","성격이 화끈하고 화려한 것을 좋아하네요","용모가 깨끗하여 주위 사람으로부터 호감을 사며, 언변이 능란하네요","이상이 거대하며 통이 커 무사 기질이 있네요","지적, 온화, 인자, 중후한 스타일이며 다소 게으르네요","겉으로는 강해 보이나 눈물과 정이 많네요","성격이 맵고 강직하며 죽기를 두려워하지 않네요","권모술수 및 처세에 능하시네요","남에게 잘 보이려고 하며 감수성이 예민하고 신경질적이네요"};
	
	
	/***************음력날짜 구하는데 사용하는 변수*/
	// 양력 각달의 일수를 저장한 배열
	@NotPersistent
	static int[] MonthTable = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	// 1881-2050년까지의 음력 데이터
	// 1(29), 2(30), 3(윤달29), 4(윤달30)
	@NotPersistent
	static String[] LunarTable = {
			"1212122322121", "1212121221220", "1121121222120", "2112132122122", "2112112121220", 
			"2121211212120", "2212321121212", "2122121121210", "2122121212120", "1232122121212", 
			"1212121221220", "1121123221222", "1121121212220", "1212112121220", "2121231212121", 
			"2221211212120", "1221212121210", "2123221212121", "2121212212120", "1211212232212", 
			"1211212122210", "2121121212220", "1212132112212", "2212112112210", "2212211212120", 
			"1221412121212", "1212122121210", "2112212122120", "1231212122212", "1211212122210", 
			"2121123122122", "2121121122120", "2212112112120", "2212231212112", "2122121212120", 
			"1212122121210", "2132122122121", "2112121222120", "1211212322122", "1211211221220", 
			"2121121121220", "2122132112122", "1221212121120", "2121221212110", "2122321221212", 
			"1121212212210", "2112121221220", "1231211221222", "1211211212220", "1221123121221", 
			"2221121121210", "2221212112120", "1221241212112", "1212212212120", "1121212212210", 
			"2114121212221", "2112112122210", "2211211412212", "2211211212120", "2212121121210", 
			"2212214112121", "2122122121120", "1212122122120", "1121412122122", "1121121222120", 
			"2112112122120", "2231211212122", "2121211212120", "2212121321212", "2122121121210", 
			"2122121212120", "1212142121212", "1211221221220", "1121121221220", "2114112121222", 
			"1212112121220", "2121211232122", "1221211212120", "1221212121210", "2121223212121", 
			"2121212212120", "1211212212210", "2121321212221", "2121121212220", "1212112112210", 
			"2223211211221", "2212211212120", "1221212321212", "1212122121210", "2112212122120", 
			"1211232122212", "1211212122210", "2121121122210", "2212312112212", "2212112112120", 
			"2212121232112", "2122121212110", "2212122121210", "2112124122121", "2112121221220", 
			"1211211221220", "2121321122122", "2121121121220", "2122112112322", "1221212112120", 
			"1221221212110", "2122123221212", "1121212212210", "2112121221220", "1211231212222", 
			"1211211212220", "1221121121220", "1223212112121", "2221212112120", "1221221232112", 
			"1212212122120", "1121212212210", "2112132212221", "2112112122210", "2211211212210", 
			"2221321121212", "2212121121210", "2212212112120", "1232212122112", "1212122122120", 
			"1121212322122", "1121121222120", "2112112122120", "2211231212122", "2121211212120", 
			"2122121121210", "2124212112121", "2122121212120", "1212121223212", "1211212221220", 
			"1121121221220", "2112132121222", "1212112121220", "2121211212120", "2122321121212", 
			"1221212121210", "2121221212120", "1232121221212", "1211212212210", "2121123212221", 
			"2121121212220", "1212112112220", "1221231211221", "2212211211220", "1212212121210", 
			"2123212212121", "2112122122120", "1211212322212", "1211212122210", "2121121122120", 
			"2212114112122", "2212112112120", "2212121211210", "2212232121211", "2122122121210", 
			"2112122122120", "1231212122212", "1211211221220", "2121121321222", "2121121121220", 
			"2122112112120", "2122141211212", "1221221212110", "2121221221210", "2114121221221"
	};
	@NotPersistent
	static int yearChunganPlus=7;//년 천간 +
	
	@NotPersistent
	static int yearJiganPlus=9;  //년 지간 +
	
	@NotPersistent
	static int monthChunganPlus=4;//월 천간 ++\
	
	@NotPersistent
	static int monthJiganPlus=2;//월 지간 ++
	
	@NotPersistent
	static int dateChunganPlus=5;//일 천간 ++
	
	@NotPersistent
	static int dateJiganPlus=3;//일 지간 ++
	/******************end*******************/
	
	
	
	////////////////////////////////////////////
	
	
	//@Id
	@PrimaryKey
	@Persistent
	//@Extension(vendorName="datanucleus", key="gae.encoded-pk",value="true") 
    private String email;

	
	@Persistent
	private String solar_year="";//양력 년
	@Persistent
	private String solar_month="";//양력 월
	@Persistent
	private String solar_date="";//양력 일
    
	@Persistent
	private String birth_time="";//태어난 시간
    
	@Persistent
	private String lunar_year="";//음력 년
	@Persistent
	private String lunar_month="";//음력 월
	@Persistent
	private String lunar_date="";//음력 일
	@Persistent
	private  Boolean isYunMonth=false;//음력 윤
    
    

	@Persistent
	private String yearSkyVal="";//년 천간
	@Persistent
	private String yearLandVal="";//년 지간
	@Persistent
	private String monthSkyVal="";//월 천간
	@Persistent
	private String monthLandVal="";//월 지간
	@Persistent
	private String dateSkyVal="";//일 천간
	@Persistent
	private String dateLandVal="";//일 지간
	@Persistent
	private String timeSkyVal="";//시간 천간
	@Persistent
	private String timeLandVal="";//시간 지간
	
	
	@Persistent
	private String yearSkyPM="";//년 천간 +or-
	@Persistent
	private String yearLandPM="";//년 지간 +or-
	@Persistent
	private String monthSkyPM="";//월 천간 +or-
	@Persistent
	private String monthLandPM="";//월 지간 +or-
	@Persistent
	private String dateSkyPM="";//일 천간 +or-
	@Persistent
	private String dateLandPM="";//일 지간 +or-
	@Persistent
	private String timeSkyPM="";//시간 천간 +or-
	@Persistent
	private String timeLandPM="";//시간 지간 +or-
	
	@Persistent
	private String yearSky5hang="";//년 천간 오행
	@Persistent
	private String yearLand5hang="";//년 지간오행
	@Persistent
	private String monthSky5hang="";//월 천간 오행
	@Persistent
	private String monthLand5hang="";//월 지간 오행
	@Persistent
	private String dateSky5hang="";//일 천간 오행
	@Persistent
	private String dateLand5hang="";//일 지간 오행
	@Persistent
	private String timeSky5hang="";//시간 천간 오행
	@Persistent
	private String timeLand5hang="";//시간 지간 오행
	
	@Persistent
	private String yoyakDesc="";//사주 요약
	
	@Persistent
	private int plusPercent=0;//+%
	@Persistent
	private int minusPercent=0;//-%
	
	@Persistent
	private int mokPlus=0;
	@Persistent
	private int whaPlus=0;
	@Persistent
	private int geumPlus=0;
	@Persistent
	private int suPlus=0;
	
	@Persistent
	private int jamiMinus=0;
	@Persistent
	private int chukohMinus=0;
	@Persistent
	private int inyuMinus=0;
	@Persistent
	private int myosinMinus=0;
	@Persistent
	private int jinhaeMinus=0;
	@Persistent
	private int sasulMinus=0;
	
	@Persistent
    private int[] ohhangCnt ={0,0,0,0,0};//5행 갯수
	
	@Persistent
	private float[] ohhangCntAvg ={0,0,0,0,0};//2개 사주의 합에서 5행 갯수 평균
	
	@Persistent
	private String skyHab="";//갑기, 을경, 병신, 정임, 무계
	
	@Persistent
	private String skyChung="";//갑경, 을신, 병임, 정계
	
	@Persistent
	private Boolean neverMarriage=false;//불혼 계패살
	
	@Persistent
	private int totalScore=0;//총점
	
	
	@Persistent
	private String  sex="";//성별
	
	@Persistent
	private String  job="";//직업
	
	@Persistent
	private String  addr1="";//사는 곳
	
	
	
	
	//////////////////////////////////////////////

  
    //@Persistent
    //@Version
	
	@Persistent
	private Integer version;
	
	//@Id 이거 빼면 오류생김
	 /*
	@Id
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	public Long getId() {
	    return id;
	  }
	 public void setId(Long id) {
		    this.id = id;
		  }
*/
	
	public String getId() {
	    return this.email;
	  }
	 public void setId(String email) {
		    this.email = email;
		  }
	 
	 
	 public void setVersion(Integer version) {
		    this.version = version;
		  }
	 public Integer getVersion() {
		    return version;
		  }
	
	 
	 
	 public SajuDataTable(String year, String month, String date, String time){
		 this.solar_year=year;
		 this.solar_month=month;
		 this.solar_date=date;
		 this.birth_time=time; 
	 }
	 
  /**
   * Find all tasks for the current user.
   */
	/*
  @SuppressWarnings("unchecked")
  public static List<Task> findAllTasks() {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from Task o where o.userId=:userId");
      query.setParameter("userId", UserServiceWrapper.get().getCurrentUserId());
      List<Task> list = query.getResultList();

      
      //  If this is the first time running the app, populate the datastore with
       /// some default tasks and re-query the datastore for them.
       ///
      if (list.size() == 0) {
        populateDatastore();
        list = query.getResultList();

        //
        // Workaround for this issue:
        //  http://code.google.com/p/datanucleus-appengine/issues/detail?id=24
        // /
        list.size();
      }

      return list;
    } finally {
      em.close();
    }
  }
*/


  /**
   * 사주정보를 가져온다.
   * 
   * @param id the {@link Task} id
   * @return the associated {@link Task}, or null if not found
   */
	/*
  public static SajuDataTable findSajuData(String  user_email) {
    if (user_email == null) {
      return null;
    }

    PersistenceManager pm = persistenceManager();
    try {
    	SajuDataTable sajuInfo = pm.getObjectById(SajuDataTable.class, user_email);
      if (sajuInfo != null && UserServiceWrapper.get().getCurrentUserId().equals(sajuInfo.email)) {
        return sajuInfo;
      }
      return null;
    } finally {
    	pm.close();
    }
  }
  */
	
  /**
   * 사주정보를 가져온다.
   * 
   * @param id the {@link Task} id
   * @return the associated {@link Task}, or null if not found
   */
 
  public static SajuDataTable findSajuDataTableByEmail() {
//System.out.println("3333333333333333333333333");   

    PersistenceManager pm = persistenceManager();
  //  System.out.println("44444444444444444444");  
    try {
    	 
    	 //SajuDataTable sajuInfo = pm.getObjectById(SajuDataTable.class, UserServiceWrapper.get().getCurrentUserId());
    	
    	SajuDataTable result=null;
    	
    	 Query query = pm.newQuery("SELECT  FROM " + SajuDataTable.class.getName()+" WHERE email == :email " );
		  
		 List<SajuDataTable> sajuInfo = (List<SajuDataTable>)query.execute(UserServiceWrapper.get().getCurrentUser().getEmail());
		  
		 if (sajuInfo.isEmpty()) {
		    	;
		 }else if(sajuInfo.size()==1){
		    for (SajuDataTable mySaju : sajuInfo) {
		    	result=mySaju;
		    }
		 }else{//앞으로 기존것은 모두 지우고 null을 반환하도록 함
			 for (SajuDataTable mySaju : sajuInfo) {//현재는마지막걸로 함.
			    	result=mySaju;
			 }
			 
			 System.out.println("한 아이디에 여러개의 이메일이 존재합니다.");  
		 }
        return result;
     
     
    } finally {
    	pm.close();
    }
  }

  
	 public static SajuDataTable findSajuDataTable(String email) {
		 //System.out.println("3333333333333333333333333");   

		     PersistenceManager pm = persistenceManager();
		   //  System.out.println("44444444444444444444");  
		     try {
		     	 
		     	 SajuDataTable sajuInfo = pm.getObjectById(SajuDataTable.class, email);
		     //	System.out.println("5555555555555555");  
		         return sajuInfo;
		      
		      
		     } finally {
		     	pm.close();
		     }
		   }
/*	 
	 public static String findSajuDataTableTest() {
		 System.out.println("3333333333333333333333333");   

		     //PersistenceManager pm = persistenceManager();
		     System.out.println("44444444444444444444");  
		     try {
		     	 
		     	 //SajuDataTable sajuInfo = pm.getObjectById(SajuDataTable.class, UserServiceWrapper.get().getCurrentUserId());
		     	System.out.println("5555555555555555");
		     	String result="result";
		         return result;
		      
		      
		     } finally {
		     	//pm.close();
		     }
		   }
	*/ 
  /*
  public static SajuDataTable findSajuDataTable(Long id) {
	    

	      PersistenceManager pm = persistenceManager();
	   
	      try {
	      	 
	      	 SajuDataTable sajuInfo = pm.getObjectById(SajuDataTable.class, id);
	   
	          return sajuInfo;
	       
	       
	      } finally {
	      	pm.close();
	      }
	    }
  */
  
  /**
   * Persist this object in the data store.
   */
  public void persist() {
	  PersistenceManager pm = persistenceManager();
    try {
      // Set the user id if this is a new task.
    	//System.out.println("persist#################");
      String curUserId = UserServiceWrapper.get().getCurrentUser().getEmail();
      
      /*
      System.out.println("curUserId"+curUserId);
      System.out.println("getSolar_month"+solar_month);
      System.out.println("getSolar_date"+getSolar_date());
      System.out.println("getBirth_time"+getBirth_time());
      System.out.println("getSex"+getSex());
      System.out.println("getJob"+getJob());
      System.out.println("getAddr1"+getAddr1());
	  */
      
      if (email == null) {
    	  System.out.println("email - null");
    	  email = curUserId;
      }

      
      // Verify the current user owns the task before updating it.
      if (curUserId.equals(email)) {
    	  
    	//생년월일시가 바뀌었으면 사주계산후 적용
    	  //SajuDataTable sajuInfo = pm.getObjectById(SajuDataTable.class, email);

    	  //if(sajuInfo.getSolar_year()!=solar_year || sajuInfo.getSolar_month()!=solar_month || sajuInfo.getSolar_date()!=solar_date || sajuInfo.getBirth_time() !=birth_time){
    		//  calSaju();
          //}
       // pm.makePersistent(this);
        
        
        
        SajuDataTable sajuInfo=null;
    	
     	 Query query = pm.newQuery("SELECT  FROM " + SajuDataTable.class.getName()+" WHERE email == :email " );
		  
		 List<SajuDataTable> sajuInfoList = (List<SajuDataTable>)query.execute(UserServiceWrapper.get().getCurrentUser().getEmail());
//System.out.println("sajuInfoList"+sajuInfoList.size());		  
		 if (sajuInfoList.isEmpty()) {//insert
			 calSaju();
		 }else if(sajuInfoList.size()==1){//update
		    for (SajuDataTable mySaju : sajuInfoList) {
		    	sajuInfo=mySaju;
		    }
		    
		    if(sajuInfo.getSolar_year()!=solar_year || sajuInfo.getSolar_month()!=solar_month || sajuInfo.getSolar_date()!=solar_date || sajuInfo.getBirth_time() !=birth_time){
		      System.out.println("#수정init");
		    	initSaju();//기존사주정보 삭제(오행갯수)
    		  calSaju();
           }
		    
		 }else{//앞으로 기존것은 모두 지우고 null을 반환하도록 함
			 for (SajuDataTable mySaju : sajuInfoList) {//현재는마지막걸로 함.
				 sajuInfo=mySaju;
			 }
			 
			 //System.out.println("신규또는수정 : 한 아이디에 여러개의 이메일이 존재합니다.");  
		 }
		// System.out.println("persist_last#################");	       
		 pm.makePersistent(this);//적용
		// System.out.println("persit-last");        
      }
    } finally {
      pm.close();
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

  
  //사주정보 초기화(내정보 수정을 해서 생년월일시가 바뀌어 업데이트시 기존사용정보를 삭제시 사용)
  private void initSaju(){
	   lunar_year="";//음력 년
	   lunar_month="";//음력 월
	   lunar_date="";//음력 일
	   isYunMonth=false;//음력 윤
	    
	    

		yearSkyVal="";//년 천간
		yearLandVal="";//년 지간
		monthSkyVal="";//월 천간
		monthLandVal="";//월 지간
		dateSkyVal="";//일 천간
		dateLandVal="";//일 지간
		timeSkyVal="";//시간 천간
		timeLandVal="";//시간 지간
		
		
		yearSkyPM="";//년 천간 +or-
		yearLandPM="";//년 지간 +or-
		monthSkyPM="";//월 천간 +or-
		monthLandPM="";//월 지간 +or-
		dateSkyPM="";//일 천간 +or-
		dateLandPM="";//일 지간 +or-
		timeSkyPM="";//시간 천간 +or-
		timeLandPM="";//시간 지간 +or-
		
		yearSky5hang="";//년 천간 오행
		yearLand5hang="";//년 지간오행
		monthSky5hang="";//월 천간 오행
		monthLand5hang="";//월 지간 오행
		dateSky5hang="";//일 천간 오행
		dateLand5hang="";//일 지간 오행
		timeSky5hang="";//시간 천간 오행
		timeLand5hang="";//시간 지간 오행
		
		yoyakDesc="";//사주 요약
		
		plusPercent=0;//+%
		minusPercent=0;//-%
		
		mokPlus=0;
		whaPlus=0;
		geumPlus=0;
		suPlus=0;
		
		jamiMinus=0;
		chukohMinus=0;
		inyuMinus=0;
		myosinMinus=0;
		jinhaeMinus=0;
		sasulMinus=0;
		
		ohhangCnt[0] =0;//5행 갯수
		ohhangCnt[1] =0;//5행 갯수
		ohhangCnt[2] =0;//5행 갯수
		ohhangCnt[3] =0;//5행 갯수
		ohhangCnt[4] =0;//5행 갯수
		
		ohhangCntAvg[0] =0;//2개 사주의 합에서 5행 갯수 평균
		ohhangCntAvg[1] =0;//2개 사주의 합에서 5행 갯수 평균
		ohhangCntAvg[2] =0;//2개 사주의 합에서 5행 갯수 평균
		ohhangCntAvg[3] =0;//2개 사주의 합에서 5행 갯수 평균
		ohhangCntAvg[4] =0;//2개 사주의 합에서 5행 갯수 평균
		
		skyHab="";//갑기, 을경, 병신, 정임, 무계
		
		skyChung="";//갑경, 을신, 병임, 정계
		
		neverMarriage=false;//불혼 계패살
		
		totalScore=0;//총점
		
  }
  /**
   * Populate the datastore with some default tasks. We do this to make the app
   * more intuitive on first use.
   */
  /*
  @SuppressWarnings("deprecation")
  private static void populateDatastore() {
    {
      // Task 0.
      Task task0 = new Task();
      task0.setName("Beat Angry Birds");
      task0.setNotes("This game is impossible!");
      task0.setDueDate(new Date(100, 4, 20));
      task0.persist();
    }
    {
      // Task 1.
      Task task1 = new Task();
      task1.setName("Make a million dollars");
      task1.setNotes("Then spend it all on Android apps");
      task1.persist();
    }
    {
      // Task 2.
      Task task2 = new Task();
      task2.setName("Buy a dozen eggs");
      task2.setNotes("of the chicken variety");
      task2.persist();
    }
    {
      // Task 3.
      Task task3 = new Task();
      task3.setName("Complete all tasks");
      task3.persist();
    }
  }
*/

	
  
	static public List<FriendTable> searchFriend(){
		List<FriendTable> friendResult=new ArrayList<FriendTable>();
		SajuDataTable mySaju ;
		List<SajuDataTable> youSaju;
		
		String i_email = UserServiceWrapper.get().getCurrentUser().getEmail();
		PersistenceManager pm = persistenceManager();
		try{
			
		    
			//1. Query를 이용해 나를 제외한 전체 사용자 리스트 가져오기(사람이 많아지면 성별, 나이로 필터링을 해야한다.)
			//youSaju.add();
		    Query query = pm.newQuery("SELECT  FROM " + SajuDataTable.class.getName()+" WHERE email != :email " );
		  
		    youSaju = (List<SajuDataTable>) query.execute(i_email);
		    
		    //if (youSaju.isEmpty()) {//나 혼자 밖에 없을때
		    	
		    //}
		    
	System.out.println("friend youSaju"+youSaju.size());		
			
			//2. 내 사주정보 가져오기
			//mySaju=pm.getKey();
		    mySaju = pm.getObjectById(SajuDataTable.class, i_email);
	
	        
		    System.out.println("friend mySajuㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");			    
		    //내 정보입력을 하지 않았을때 오류
		   System.out.println("friend mySaju"+mySaju);	    
		    if(mySaju==null){
		    	return null;
		    }
		    System.out.println("imsi");    
	System.out.println("friend mySaju"+mySaju.getEmail());	    
		    
			//3. for문을 돌며 궁합점수 계산하여 friendResult에 저장
		    if (youSaju.isEmpty()) {
		        //return null;
		    	//sajuInfo.setResult_code("1");
		    	//sajuInfo.setResult_val("생년월일을 입력하세요");
		    }else {
		    	        for (SajuDataTable you : youSaju) {
		    	            if (you.getEmail() == null) {//오류
		    	            	//sajuInfo.setResult_code("2");
		    			    	//sajuInfo.setResult_val("이메일 정보가 저장되었지 않습니다.");
		    	            }else{
		    	            	//점수계산하여 friendResult리스트에 저장
		    	            	FriendTable friend=calScore(mySaju, you);
		    	            	friendResult.add(friend);
		    	            }
		    	        }
		    }
		    
		   // 결과 정렬
			Collections.sort(friendResult);
		    
			//4. db에 저장=>저장할 필요가 있나?, 하루동안 은 새로계산하지 않고 이 정보로 함(사람이 많아져서 부하가 걸리면)
			mySaju.setFriends(friendResult);
	System.out.println("friend result"+friendResult.size());		
			//5. pm close
		}catch(JDOObjectNotFoundException e){//값이 없을때 null 리턴
			return null;
		}finally{
			pm.close();
		}
	    
	    
System.out.println("friend result end");			
		
		return friendResult;
	}
	
	
	//친구 점수 계산
	static private FriendTable calScore(SajuDataTable i, SajuDataTable you){
		
		FriendTable friend = new FriendTable();
		int score=0;
		
		friend.setI_email(i.getEmail());
		friend.setFriend_email(you.getEmail());
		friend.setAddr1(you.getAddr1());
		friend.setJob(you.getJob());
		friend.setSex(you.getSex());
		
		//점수 계산후 return
		friend.setScore(makePlusSaju(i,you).getTotalScore());

		return friend;
	}
  
  
  
	
	public SajuDataTable() {}
	/*
	public SajuDataTable(String solar_year, String solar_month, String solar_date, String birth_time) {
		this.solar_year = solar_year;
		this.solar_month = solar_month;
		this.solar_date = solar_date;
		this.birth_time = birth_time;
	}
    */
	
	
	public List<FriendTable> getFriends() {
		return friends;
	}

	public void setFriends(List<FriendTable> friends) {
		this.friends = friends;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getSolar_year() {
		return solar_year;
	}

	public void setSolar_year(String solar_year) {
		this.solar_year = solar_year;
	}

	public String getSolar_month() {
		return solar_month;
	}

	public void setSolar_month(String solar_month) {
		this.solar_month = solar_month;
	}

	public String getSolar_date() {
		return solar_date;
	}

	public void setSolar_date(String solar_date) {
		this.solar_date = solar_date;
	}

	public String getBirth_time() {
		return birth_time;
	}

	public void setBirth_time(String birth_time) {
		this.birth_time = birth_time;
	}

	public String getLunar_year() {
		return lunar_year;
	}

	public void setLunar_year(String lunar_year) {
		this.lunar_year = lunar_year;
	}

	public String getLunar_month() {
		return lunar_month;
	}

	public void setLunar_month(String lunar_month) {
		this.lunar_month = lunar_month;
	}

	public String getLunar_date() {
		return lunar_date;
	}

	public void setLunar_date(String lunar_date) {
		this.lunar_date = lunar_date;
	}

	public Boolean isYunMonth() {
		return isYunMonth;
	}

	public void setYunMonth(Boolean isYunMonth) {
		this.isYunMonth = isYunMonth;
	}

	public String getYearSkyVal() {
		return yearSkyVal;
	}

	public void setYearSkyVal(String yearSkyVal) {
		this.yearSkyVal = yearSkyVal;
	}

	public String getYearLandVal() {
		return yearLandVal;
	}

	public void setYearLandVal(String yearLandVal) {
		this.yearLandVal = yearLandVal;
	}

	public String getMonthSkyVal() {
		return monthSkyVal;
	}

	public void setMonthSkyVal(String monthSkyVal) {
		this.monthSkyVal = monthSkyVal;
	}

	public String getMonthLandVal() {
		return monthLandVal;
	}

	public void setMonthLandVal(String monthLandVal) {
		this.monthLandVal = monthLandVal;
	}

	public String getDateSkyVal() {
		return dateSkyVal;
	}

	public void setDateSkyVal(String dateSkyVal) {
		this.dateSkyVal = dateSkyVal;
	}

	public String getDateLandVal() {
		return dateLandVal;
	}

	public void setDateLandVal(String dateLandVal) {
		this.dateLandVal = dateLandVal;
	}

	public String getYearSkyPM() {
		return yearSkyPM;
	}

	public void setYearSkyPM(String yearSkyPM) {
		this.yearSkyPM = yearSkyPM;
	}

	public String getYearLandPM() {
		return yearLandPM;
	}

	public void setYearLandPM(String yearLandPM) {
		this.yearLandPM = yearLandPM;
	}

	public String getMonthSkyPM() {
		return monthSkyPM;
	}

	public void setMonthSkyPM(String monthSkyPM) {
		this.monthSkyPM = monthSkyPM;
	}

	public String getMonthLandPM() {
		return monthLandPM;
	}

	public void setMonthLandPM(String monthLandPM) {
		this.monthLandPM = monthLandPM;
	}

	public String getDateSkyPM() {
		return dateSkyPM;
	}

	public void setDateSkyPM(String dateSkyPM) {
		this.dateSkyPM = dateSkyPM;
	}

	public String getDateLandPM() {
		return dateLandPM;
	}

	public void setDateLandPM(String dateLandPM) {
		this.dateLandPM = dateLandPM;
	}

	public String getYearSky5hang() {
		return yearSky5hang;
	}

	public void setYearSky5hang(String yearSky5hang) {
		this.yearSky5hang = yearSky5hang;
	}

	public String getYearLand5hang() {
		return yearLand5hang;
	}

	public void setYearLand5hang(String yearLand5hang) {
		this.yearLand5hang = yearLand5hang;
	}

	public String getMonthSky5hang() {
		return monthSky5hang;
	}

	public void setMonthSky5hang(String monthSky5hang) {
		this.monthSky5hang = monthSky5hang;
	}

	public String getMonthLand5hang() {
		return monthLand5hang;
	}

	public void setMonthLand5hang(String monthLand5hang) {
		this.monthLand5hang = monthLand5hang;
	}

	public String getDateSky5hang() {
		return dateSky5hang;
	}

	public void setDateSky5hang(String dateSky5hang) {
		this.dateSky5hang = dateSky5hang;
	}

	public String getDateLand5hang() {
		return dateLand5hang;
	}

	public void setDateLand5hang(String dateLand5hang) {
		this.dateLand5hang = dateLand5hang;
	}

	public String getYoyakDesc() {
		return yoyakDesc;
	}

	public void setYoyakDesc(String yoyakDesc) {
		this.yoyakDesc = yoyakDesc;
	}

	public int getPlusPercent() {
		return plusPercent;
	}

	public void setPlusPercent(int plusPercent) {
		this.plusPercent = plusPercent;
	}

	public int getMinusPercent() {
		return minusPercent;
	}

	public void setMinusPercent(int minusPercent) {
		this.minusPercent = minusPercent;
	}



	public int getMokPlus() {
		return mokPlus;
	}

	public void setMokPlus(int mokPlus) {
		this.mokPlus = mokPlus;
	}

	public int getWhaPlus() {
		return whaPlus;
	}

	public void setWhaPlus(int whaPlus) {
		this.whaPlus = whaPlus;
	}

	public int getGeumPlus() {
		return geumPlus;
	}

	public void setGeumPlus(int geumPlus) {
		this.geumPlus = geumPlus;
	}

	public int getSuPlus() {
		return suPlus;
	}

	public void setSuPlus(int suPlus) {
		this.suPlus = suPlus;
	}

	public int getJamiMinus() {
		return jamiMinus;
	}

	public void setJamiMinus(int jamiMinus) {
		this.jamiMinus = jamiMinus;
	}

	public int getChukohMinus() {
		return chukohMinus;
	}

	public void setChukohMinus(int chukohMinus) {
		this.chukohMinus = chukohMinus;
	}

	public int getInyuMinus() {
		return inyuMinus;
	}

	public void setInyuMinus(int inyuMinus) {
		this.inyuMinus = inyuMinus;
	}

	public int getMyosinMinus() {
		return myosinMinus;
	}

	public void setMyosinMinus(int myosinMinus) {
		this.myosinMinus = myosinMinus;
	}

	public int getJinhaeMinus() {
		return jinhaeMinus;
	}

	public void setJinhaeMinus(int jinhaeMinus) {
		this.jinhaeMinus = jinhaeMinus;
	}

	public int getSasulMinus() {
		return sasulMinus;
	}

	public void setSasulMinus(int sasulMinus) {
		this.sasulMinus = sasulMinus;
	}

	public int[] getOhhangCnt() {
		return ohhangCnt;
	}

	public void setOhhangCnt(int[] ohhangCnt) {
		this.ohhangCnt = ohhangCnt;
	}
	
	//해당 index의 오행값을 ++한다.
	private void incOhhangCnt(int index) {
		this.ohhangCnt[index]++;
	}

	public String getTimeSkyVal() {
		return timeSkyVal;
	}

	public void setTimeSkyVal(String timeSkyVal) {
		this.timeSkyVal = timeSkyVal;
	}

	public String getTimeLandVal() {
		return timeLandVal;
	}

	public void setTimeLandVal(String timeLandVal) {
		this.timeLandVal = timeLandVal;
	}

	public String getTimeSkyPM() {
		return timeSkyPM;
	}

	public void setTimeSkyPM(String timeSkyPM) {
		this.timeSkyPM = timeSkyPM;
	}

	public String getTimeLandPM() {
		return timeLandPM;
	}

	public void setTimeLandPM(String timeLandPM) {
		this.timeLandPM = timeLandPM;
	}

	public String getTimeSky5hang() {
		return timeSky5hang;
	}

	public void setTimeSky5hang(String timeSky5hang) {
		this.timeSky5hang = timeSky5hang;
	}

	public String getTimeLand5hang() {
		return timeLand5hang;
	}

	public void setTimeLand5hang(String timeLand5hang) {
		this.timeLand5hang = timeLand5hang;
	}

	public float[] getOhhangCntAvg() {
		return ohhangCntAvg;
	}

	public void setOhhangCntAvg(float[] ohhangCntAvg) {
		this.ohhangCntAvg = ohhangCntAvg;
	}



	public String getSkyHab() {
		return skyHab;
	}

	public void setSkyHab(String skyHab) {
		this.skyHab = skyHab;
	}

	public String getSkyChung() {
		return skyChung;
	}

	public void setSkyChung(String skyChung) {
		this.skyChung = skyChung;
	}

	public Boolean isNeverMarriage() {
		return neverMarriage;
	}

	public void setNeverMarriage(Boolean neverMarriage) {
		this.neverMarriage = neverMarriage;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
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

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}  

	//request factory가 int[], float형을 허용하지 않아 작성
	public int getOhhangCnt0() {
		return ohhangCnt[0];
	}
	public int getOhhangCnt1() {
		return ohhangCnt[1];
	}
	public int getOhhangCnt2() {
		return ohhangCnt[2];
	}
	public int getOhhangCnt3() {
		return ohhangCnt[3];
	}
	public int getOhhangCnt4() {
		return ohhangCnt[4];
	}
	
	public float getOhhangCntAvg0() {
		return ohhangCntAvg[0];
	}
	public float getOhhangCntAvg1() {
		return ohhangCntAvg[1];
	}
	public float getOhhangCntAvg2() {
		return ohhangCntAvg[2];
	}
	public float getOhhangCntAvg3() {
		return ohhangCntAvg[3];
	}
	public float getOhhangCntAvg4() {
		return ohhangCntAvg[4];
	}
	///////////////////////////////////////
	

	//시간인덱스 코드를 텍스트로 변환해서 리턴
	public String getTransTimeValue() {
		return convertIndexToTime(getBirth_time());
	}
	
	
	//시간인덱스를  1~3시 등으로 변환
	  private String convertIndexToTime(String currentTimeInt){
	    String currentTimeStr="";
	    if(currentTimeInt.equals("0")){
			  currentTimeStr="23~1시";
		  }else if(currentTimeInt.equals("1")){
			  currentTimeStr="1~3시";
		  }else if(currentTimeInt.equals("2")){
			  currentTimeStr="3~5시";
		  }else if(currentTimeInt.equals("3")){
			  currentTimeStr="5~7시";
		  }else if(currentTimeInt.equals("4")){
			  currentTimeStr="7~9시";
		  }else if(currentTimeInt.equals("5")){
			  currentTimeStr="9~11시";
		  }else if(currentTimeInt.equals("6")){
			  currentTimeStr="11~13시";
		  }else if(currentTimeInt.equals("7")){
			  currentTimeStr="13~15시";
		  }else if(currentTimeInt.equals("8")){
			  currentTimeStr="15~17시";
		  }else if(currentTimeInt.equals("9")){
			  currentTimeStr="17~19시";
		  }else if(currentTimeInt.equals("10")){
			  currentTimeStr="19~21시";
		  }else if(currentTimeInt.equals("11")){
			  currentTimeStr="21~23시";
		  }else{
			  currentTimeStr="";
		  }
	    
	    return currentTimeStr;
	  }
 

	  
	  
	  
	//생년월일시를 가져와서 사주결과를 SajuData에 넣어서 return한다.
	    //ex) year : 2002,  month : 3, date : 23, time : 0~11(0:23시~1시, 1:1시~3시 ...)
		static public SajuDataTable getSajuResult(String year, String month, String date, String time){
			SajuDataTable sajuResult = new SajuDataTable(year, month, date,time);
			
			int dateChunganIndex=0;
			
			//System.out.println("year"+year);
			//System.out.println("month"+month);
			//System.out.println("date"+date);
			//System.out.println("time"+time);

			
			//날짜 설정
			CalendarData solar = new CalendarData();
			solar.setYear(Integer.parseInt(year));
			solar.setMonth(Integer.parseInt(month)-1);//음력변환함수는 1월달이 0부터 시작해서 -1시킨후 넘김
			solar.setDate(Integer.parseInt(date));
			solar.setTime(Integer.parseInt(time));
			
			//음력으로 변환
			CalendarData lunar = SolarToLunar(solar);
			
			int tmp_m = lunar.getMonth()+1;//음력변환함수는 1월달이 0부터 시작해서 다시 +1시킴
			lunar.setMonth(tmp_m);
			
			lunar.setTime(Integer.parseInt(time));//시간은 별도 설정
			
			//System.out.println("음력year"+lunar.getYear());
			//System.out.println("음력month"+lunar.getMonth());
			//System.out.println("음력date"+lunar.getDate());
			//System.out.println("음력time"+lunar.getTime());
			//System.out.println("음력윤달"+lunar.isYunMonth());
			
			
			//사주 뽑기
			
			// 1. 년
		
			
			 //천간
			//System.out.println("년천간"+chungan[((lunar.getYear()%10)-1+yearChunganPlus)%10]);//index때문에 -1
			sajuResult.setYearSkyVal(chungan[((lunar.getYear()%10)-1+yearChunganPlus)%10]);//index때문에 -1
			 //지간
			//System.out.println("년지간"+jigan[((lunar.getYear()%12)-1+yearJiganPlus)%12]);
			sajuResult.setYearLandVal(jigan[((lunar.getYear()%12)-1+yearJiganPlus)%12]);
			

			
			// 2. 월
			
			 //천간
			int totalMon = lunar.getYear()*12+lunar.getMonth();
			//System.out.println("월천간"+chungan[((totalMon%10)-1+monthChunganPlus)%10]);
			sajuResult.setMonthSkyVal(chungan[((totalMon%10)-1+monthChunganPlus)%10]);
			 //지간
			//System.out.println("월지간"+jigan[(lunar.getMonth()-1+monthJiganPlus)%12]);//index를 0부터기때문에 -1, 음력1월은 寅부터기때문에 +2
			sajuResult.setMonthLandVal(jigan[(lunar.getMonth()-1+monthJiganPlus)%12]);//index를 0부터기때문에 -1, 음력1월은 寅부터기때문에 +2

			
			
			//3. 일
			int totalDays=totalDays(solar);//양력 1년 1월 1일 이후 지난 날짜수
			//천간
			//System.out.println("일천간"+chungan[(totalDays+dateChunganPlus)%10]);
			sajuResult.setDateSkyVal(chungan[(totalDays+dateChunganPlus)%10]);
			dateChunganIndex=(totalDays+dateChunganPlus)%10;//사주요약 필드를 위해
			//지간
			//System.out.println("일지간"+jigan[(totalDays+dateJiganPlus)%12]);
			sajuResult.setDateLandVal(jigan[(totalDays+dateJiganPlus)%12]);
			
			
			
			//4. 일
			
			//천간
			int time_plus=0;
			if(sajuResult.getDateSkyVal().equals("甲") || sajuResult.getDateSkyVal().equals("己")){
				time_plus=0;
			}else if(sajuResult.getDateSkyVal().equals("乙") || sajuResult.getDateSkyVal().equals("庚")){
				time_plus=2;
			}else if(sajuResult.getDateSkyVal().equals("丙") || sajuResult.getDateSkyVal().equals("辛")){
				time_plus=4;
			}else if(sajuResult.getDateSkyVal().equals("丁") || sajuResult.getDateSkyVal().equals("壬")){
				time_plus=6;
			}else if(sajuResult.getDateSkyVal().equals("戊") || sajuResult.getDateSkyVal().equals("癸")){
				time_plus=8;
			}
			
			//System.out.println("시천간"+chungan[(solar.getTime()+time_plus)%10]);
			sajuResult.setTimeSkyVal(chungan[(solar.getTime()+time_plus)%10]);
			
			//지간
			//System.out.println("시지간"+jigan[solar.getTime()]);
			sajuResult.setTimeLandVal(jigan[solar.getTime()]);
			
			
			
			
			//sajuResult.setYearSkyVal("갑");
			//sajuResult.setYearLandVal("자");
			//sajuResult.setMonthSkyVal("을");
			//sajuResult.setMonthLandVal("축");
			//sajuResult.setDateSkyVal("병");
			//sajuResult.setDateLandVal("인");
			//sajuResult.setTimeSkyVal("정");
			//sajuResult.setTimeLandVal("묘");
			
			//오행 뽑기, 오행 갯수 뽑기
			//음양 뽑기, 음양비율 뽑기
			int[] plusMinus= new int[2];//index:0(-),1(+)
			
			
			
			
			//년 천간
			if(sajuResult.getYearSkyVal().equals("甲")  ){
				sajuResult.setYearSky5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setYearSkyPM("+");
				plusMinus[1]++;
			}else if(sajuResult.getYearSkyVal().equals("乙")){
				sajuResult.setYearSky5hang("木");
				sajuResult.incOhhangCnt(0);
				sajuResult.setYearSkyPM("-");
				plusMinus[0]++;
			}else if(sajuResult.getYearSkyVal().equals("丙") ){
				sajuResult.setYearSky5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setYearSkyPM("+");
				plusMinus[1]++;
			}else if(sajuResult.getYearSkyVal().equals("丁")){
				sajuResult.setYearSky5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setYearSkyPM("-");
				plusMinus[0]++;
			}else if(sajuResult.getYearSkyVal().equals("戊")){
				sajuResult.setYearSky5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setYearSkyPM("+");
				plusMinus[1]++;
			}else if(  sajuResult.getYearSkyVal().equals("己")){
				sajuResult.setYearSky5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setYearSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getYearSkyVal().equals("庚")){
				sajuResult.setYearSky5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setYearSkyPM("+");
				plusMinus[1]++;
			}else if(  sajuResult.getYearSkyVal().equals("辛")){
				sajuResult.setYearSky5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setYearSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getYearSkyVal().equals("壬")){
				sajuResult.setYearSky5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setYearSkyPM("+");
				plusMinus[1]++;
			}else if(  sajuResult.getYearSkyVal().equals("癸")){
				sajuResult.setYearSky5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setYearSkyPM("-");
				plusMinus[0]++;
			}
			
			//년 지간
			if(sajuResult.getYearLandVal().equals("寅")){
				sajuResult.setYearLand5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setYearLandPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getYearLandVal().equals("卯")){
				sajuResult.setYearLand5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setYearLandPM("-");
				plusMinus[0]++;
			}else if(sajuResult.getYearLandVal().equals("巳")){
				sajuResult.setYearLand5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setYearLandPM("-");
				plusMinus[0]++;
			}else if( sajuResult.getYearLandVal().equals("午")){
				sajuResult.setYearLand5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setYearLandPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getYearLandVal().equals("辰")  || sajuResult.getYearLandVal().equals("戌")){
				sajuResult.setYearLand5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setYearLandPM("+");
				plusMinus[1]++;
			}else if(sajuResult.getYearLandVal().equals("丑") || sajuResult.getYearLandVal().equals("未")){
				sajuResult.setYearLand5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setYearLandPM("-");
				plusMinus[0]++;
			}else if(sajuResult.getYearLandVal().equals("申")){
				sajuResult.setYearLand5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setYearLandPM("+");
				plusMinus[1]++;
			}else if(sajuResult.getYearLandVal().equals("酉")){
				sajuResult.setYearLand5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setYearLandPM("-");
				plusMinus[0]++;
			}else if(sajuResult.getYearLandVal().equals("亥")){
				sajuResult.setYearLand5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setYearLandPM("-");
				plusMinus[0]++;
			}else if( sajuResult.getYearLandVal().equals("子")){
				sajuResult.setYearLand5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setYearLandPM("+");
				plusMinus[1]++;
			}
			
			//월 천간
			if(sajuResult.getMonthSkyVal().equals("甲")){
				sajuResult.setMonthSky5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setMonthSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getMonthSkyVal().equals("乙")){
				sajuResult.setMonthSky5hang("木");
				sajuResult.incOhhangCnt(0);
				sajuResult.setMonthSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getMonthSkyVal().equals("丙")){
				sajuResult.setMonthSky5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setMonthSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getMonthSkyVal().equals("丁")){
				sajuResult.setMonthSky5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setMonthSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getMonthSkyVal().equals("戊")){
				sajuResult.setMonthSky5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setMonthSkyPM("+");
				plusMinus[1]++;
			}else if(  sajuResult.getMonthSkyVal().equals("己")){
				sajuResult.setMonthSky5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setMonthSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getMonthSkyVal().equals("庚") ){
				sajuResult.setMonthSky5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setMonthSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getMonthSkyVal().equals("辛")){
				sajuResult.setMonthSky5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setMonthSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getMonthSkyVal().equals("壬")){
				sajuResult.setMonthSky5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setMonthSkyPM("+");
				plusMinus[1]++;
			}else if(  sajuResult.getMonthSkyVal().equals("癸")){
				sajuResult.setMonthSky5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setMonthSkyPM("-");
				plusMinus[0]++;
			}
			
			//월 지간
			if(sajuResult.getMonthLandVal().equals("寅")){
				sajuResult.setMonthLand5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setMonthLandPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getMonthLandVal().equals("卯")){
				sajuResult.setMonthLand5hang("木");
				sajuResult.incOhhangCnt(0);
				sajuResult.setMonthLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getMonthLandVal().equals("巳")){
				sajuResult.setMonthLand5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setMonthLandPM("-");
				plusMinus[0]++;
			}else if( sajuResult.getMonthLandVal().equals("午")){
				sajuResult.setMonthLand5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setMonthLandPM("+");
				plusMinus[1]++;
			}
			else if( sajuResult.getMonthLandVal().equals("辰")  || sajuResult.getMonthLandVal().equals("戌")){
				sajuResult.setMonthLand5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setMonthLandPM("+");
				plusMinus[1]++;
			}else if(sajuResult.getMonthLandVal().equals("丑") || sajuResult.getMonthLandVal().equals("未")){
				sajuResult.setMonthLand5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setMonthLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getMonthLandVal().equals("申") ){
				sajuResult.setMonthLand5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setMonthLandPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getMonthLandVal().equals("酉")){
				sajuResult.setMonthLand5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setMonthLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getMonthLandVal().equals("亥")){
				sajuResult.setMonthLand5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setMonthLandPM("-");
				plusMinus[0]++;
			}else if( sajuResult.getMonthLandVal().equals("子")){
				sajuResult.setMonthLand5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setMonthLandPM("+");
				plusMinus[1]++;
			}
			
			//일 천간
			if(sajuResult.getDateSkyVal().equals("甲")){
				sajuResult.setDateSky5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setDateSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getDateSkyVal().equals("乙")){
				sajuResult.setDateSky5hang("木");
				sajuResult.incOhhangCnt(0);
				sajuResult.setDateSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getDateSkyVal().equals("丙") ){
				sajuResult.setDateSky5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setDateSkyPM("+");
				plusMinus[1]++;
			}else if(sajuResult.getDateSkyVal().equals("丁")){
				sajuResult.setDateSky5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setDateSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getDateSkyVal().equals("戊") ){
				sajuResult.setDateSky5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setDateSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getDateSkyVal().equals("己")){
				sajuResult.setDateSky5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setDateSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getDateSkyVal().equals("庚") ){
				sajuResult.setDateSky5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setDateSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getDateSkyVal().equals("辛")){
				sajuResult.setDateSky5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setDateSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getDateSkyVal().equals("壬") ){
				sajuResult.setDateSky5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setDateSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getDateSkyVal().equals("癸")){
				sajuResult.setDateSky5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setDateSkyPM("-");
				plusMinus[0]++;
			}
			
			//일 지간
			if(sajuResult.getDateLandVal().equals("寅")){
				sajuResult.setDateLand5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setDateLandPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getDateLandVal().equals("卯")){
				sajuResult.setDateLand5hang("木");
				sajuResult.incOhhangCnt(0);
				sajuResult.setDateLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getDateLandVal().equals("巳") ){
				sajuResult.setDateLand5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setDateLandPM("-");
				plusMinus[0]++;
			}else if( sajuResult.getDateLandVal().equals("午")){
				sajuResult.setDateLand5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setDateLandPM("+");
				plusMinus[1]++;
			}
			else if(sajuResult.getDateLandVal().equals("辰")  || sajuResult.getDateLandVal().equals("戌")){
				sajuResult.setDateLand5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setDateLandPM("+");
				plusMinus[1]++;
			}else if(sajuResult.getDateLandVal().equals("丑") || sajuResult.getDateLandVal().equals("未") ){
				sajuResult.setDateLand5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setDateLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getDateLandVal().equals("申")){
				sajuResult.setDateLand5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setDateLandPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getDateLandVal().equals("酉")){
				sajuResult.setDateLand5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setDateLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getDateLandVal().equals("亥")){
				sajuResult.setDateLand5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setDateLandPM("-");
				plusMinus[0]++;
			}else if(  sajuResult.getDateLandVal().equals("子")){
				sajuResult.setDateLand5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setDateLandPM("+");
				plusMinus[1]++;
			}
			
			//시 천간
			if(sajuResult.getTimeSkyVal().equals("甲")){
				sajuResult.setTimeSky5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setTimeSkyPM("+");
				plusMinus[1]++;
			}else if(  sajuResult.getTimeSkyVal().equals("乙")){
				sajuResult.setTimeSky5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setTimeSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getTimeSkyVal().equals("丙") ){
				sajuResult.setTimeSky5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setTimeSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getTimeSkyVal().equals("丁")){
				sajuResult.setTimeSky5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setTimeSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getTimeSkyVal().equals("戊")){
				sajuResult.setTimeSky5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setTimeSkyPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getTimeSkyVal().equals("己")){
				sajuResult.setTimeSky5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setTimeSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getTimeSkyVal().equals("庚")){
				sajuResult.setTimeSky5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setTimeSkyPM("+");
				plusMinus[1]++;
			}else if(  sajuResult.getTimeSkyVal().equals("辛")){
				sajuResult.setTimeSky5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setTimeSkyPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getTimeSkyVal().equals("壬")){
				sajuResult.setTimeSky5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setTimeSkyPM("+");
				plusMinus[1]++;
			}else if(  sajuResult.getTimeSkyVal().equals("癸")){
				sajuResult.setTimeSky5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setTimeSkyPM("-");
				plusMinus[0]++;
			}
			
			//시 지간
			if(sajuResult.getTimeLandVal().equals("寅") ){
				sajuResult.setTimeLand5hang("木");
				sajuResult.incOhhangCnt(0);	
				sajuResult.setTimeLandPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getTimeLandVal().equals("卯")){
				sajuResult.setTimeLand5hang("木");
				sajuResult.incOhhangCnt(0);
				sajuResult.setTimeLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getTimeLandVal().equals("巳") ){
				sajuResult.setTimeLand5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setTimeLandPM("-");
				plusMinus[0]++;
			}else if( sajuResult.getTimeLandVal().equals("午")){
				sajuResult.setTimeLand5hang("火");
				sajuResult.incOhhangCnt(1);
				sajuResult.setTimeLandPM("+");
				plusMinus[1]++;
			}
			else if( sajuResult.getTimeLandVal().equals("辰") || sajuResult.getTimeLandVal().equals("戌")){
				sajuResult.setTimeLand5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setTimeLandPM("+");
				plusMinus[1]++;
			}else if(sajuResult.getTimeLandVal().equals("丑")  || sajuResult.getTimeLandVal().equals("未")){
				sajuResult.setTimeLand5hang("土");
				sajuResult.incOhhangCnt(2);
				sajuResult.setTimeLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getTimeLandVal().equals("申") ){
				sajuResult.setTimeLand5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setTimeLandPM("+");
				plusMinus[1]++;
			}else if( sajuResult.getTimeLandVal().equals("酉")){
				sajuResult.setTimeLand5hang("金");
				sajuResult.incOhhangCnt(3);
				sajuResult.setTimeLandPM("-");
				plusMinus[0]++;
			}
			else if(sajuResult.getTimeLandVal().equals("亥")){
				sajuResult.setTimeLand5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setTimeLandPM("-");
				plusMinus[0]++;
			}else if(  sajuResult.getTimeLandVal().equals("子")){
				sajuResult.setTimeLand5hang("水");
				sajuResult.incOhhangCnt(4);
				sajuResult.setTimeLandPM("+");
				plusMinus[1]++;
			}

			
			
			
			if(plusMinus[0]==0){
				sajuResult.setPlusPercent(100);
				sajuResult.setMinusPercent(0);
			}else if(plusMinus[0]==1){
				sajuResult.setPlusPercent(87);
				sajuResult.setMinusPercent(13);
			}else if(plusMinus[0]==2){
				sajuResult.setPlusPercent(75);
				sajuResult.setMinusPercent(25);
			}else if(plusMinus[0]==3){
				sajuResult.setPlusPercent(62);
				sajuResult.setMinusPercent(38);
			}else if(plusMinus[0]==4){
				sajuResult.setPlusPercent(50);
				sajuResult.setMinusPercent(50);
			}else if(plusMinus[0]==5){
				sajuResult.setPlusPercent(37);
				sajuResult.setMinusPercent(63);
			}else if(plusMinus[0]==6){
				sajuResult.setPlusPercent(25);
				sajuResult.setMinusPercent(75);
			}else if(plusMinus[0]==7){
				sajuResult.setPlusPercent(13);
				sajuResult.setMinusPercent(87);
			}else if(plusMinus[0]==8){
				sajuResult.setPlusPercent(0);
				sajuResult.setMinusPercent(100);
			}else {
				sajuResult.setPlusPercent(0);
				sajuResult.setMinusPercent(0);
			}
			
			
			
			
			
			//사주요약 뽑기
			sajuResult.setYoyakDesc(sajuResult.yoyak_desc[dateChunganIndex]);
			
			
			
			return sajuResult;
		}
	  
	  
	  
	  
	  
	//현재사주, 내사주, 사주정보(현재사주,내사주 통합) 를 가져온다.
	 static public List<SajuDataTable> getMyCurrentDetailSajuResult(){
		 ArrayList<SajuDataTable> result = new ArrayList(); 
		 //HashMap result = new HashMap();
		 
		  //String loginUrl="";
		 // UserService userService = UserServiceFactory.getUserService();
		  //User user = userService.getCurrentUser();
		  

			 // PersistenceManager pm = PMF.get().getPersistenceManager();
			    //String query = "select from " + SajuDataTable.class.getName()+" where email==?";
			    //List<SajuDataTable> greetings = (List<SajuDataTable>) pm.newQuery(query).execute(user.getEmail());
			  //Query query = pm.newQuery("SELECT  FROM " + SajuDataTable.class.getName()+" WHERE email == :email " );
			  
			  //List<SajuDataTable> greetings = (List<SajuDataTable>) query.execute(user.getEmail());
			  
			  SajuDataTable mySaju =findSajuDataTableByEmail();
			  
			    if (mySaju==null) {//내 정보가 입력되어있지 않을때
			    	result=null;
			    }else {
			    	            	//현재 시간에 대한 사주정보와 내 사주정보를 조합해서 가져옴
			    	            	//ex) year : 2002,  month : 3, date : 23, time : 0~11(0:23시~1시, 1:1시~3시 ...)
			    	            	TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
			    	            	Calendar oCalendar = Calendar.getInstance(tz);  // 현재 날짜/시간 등의 각종 정보 얻기
			    	            	String year =Integer.toString(oCalendar.get(Calendar.YEAR)); 
			    	                String month = Integer.toString(oCalendar.get(Calendar.MONTH) + 1);
			    	                String day=Integer.toString(oCalendar.get(Calendar.DAY_OF_MONTH));
			    	                int real_time=oCalendar.get(Calendar.HOUR_OF_DAY);
			    	                int int_time;
			    	                if(real_time>=23 && real_time<1){
			    	                	int_time=0;
			    	                }else if(real_time>=1 && real_time<3){
			    	                	int_time=1;
			    	                }else if(real_time>=3 && real_time<5){
			    	                	int_time=2;
			    	                }else if(real_time>=5 && real_time<7){
			    	                	int_time=3;
			    	                }else if(real_time>=7 && real_time<9){
			    	                	int_time=4;
			    	                }else if(real_time>=9 && real_time<11){
			    	                	int_time=5;
			    	                }else if(real_time>=11 && real_time<13){
			    	                	int_time=6;
			    	                }else if(real_time>=13 && real_time<15){
			    	                	int_time=7;
			    	                }else if(real_time>=15 && real_time<17){
			    	                	int_time=8;
			    	                }else if(real_time>=17 && real_time<19){
			    	                	int_time=9;
			    	                }else if(real_time>=19 && real_time<21){
			    	                	int_time=10;
			    	                }else if(real_time>=21 && real_time<23){
			    	                	int_time=11;
			    	                }else{//시간 오류일때는 0으로 셋팅
			    	                	int_time=0;
			    	                }
			    	                
			    	                String time=Integer.toString(int_time);
			    	                	
			    	            	SajuDataTable currentSaju = getSajuResult(year, month, day, time);
			    	            	SajuDataTable plus = makePlusSaju(mySaju, currentSaju);
			    	            	
			    	            	
			    	            	//내 사주를 SajuData형식으로 변환
			    	            	//SajuData mySaju = convertSajuDataTableToSajuData(g);
			    	            	
			    	            	//return 값 설정
			    	            	//result.put("sajuInfo", sajuInfo);
			    	            	result.add(mySaju);
			    	            	result.add(currentSaju);
			    	            	result.add(plus);
			    	            	
			    	        
			    	        }
			  
		
		  
		  //sajuInfo.setResult_code("2");
		  //sajuInfo.setResult_val("목2 화2 토3 금2 수2  합 2  살2");
		  
		  return result;
	 }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
  
  
  //생년월일시를 가져와서 사주결과를 SajuData에 넣어서 return한다.
  //ex) year : 2002,  month : 3, date : 23, time : 0~11(0:23시~1시, 1:1시~3시 ...)
	private void calSaju(){
		//sajuResult = new SajuDataTable(year, month, date,time);
		
		int dateChunganIndex=0;
		
		//System.out.println("year"+year);
		//System.out.println("month"+month);
		//System.out.println("date"+date);
		//System.out.println("time"+time);

		
		//날짜 설정
		CalendarData solar = new CalendarData();
		solar.setYear(Integer.parseInt(solar_year));
		solar.setMonth(Integer.parseInt(solar_month)-1);//음력변환함수는 1월달이 0부터 시작해서 -1시킨후 넘김
		solar.setDate(Integer.parseInt(solar_date));
		solar.setTime(Integer.parseInt(birth_time));
		
		//음력으로 변환
		CalendarData lunar = SolarToLunar(solar);
		
		int tmp_m = lunar.getMonth()+1;//음력변환함수는 1월달이 0부터 시작해서 다시 +1시킴
		lunar.setMonth(tmp_m);
		
		lunar.setTime(Integer.parseInt(birth_time));//시간은 별도 설정
		
		//System.out.println("음력year"+lunar.getYear());
		//System.out.println("음력month"+lunar.getMonth());
		//System.out.println("음력date"+lunar.getDate());
		//System.out.println("음력time"+lunar.getTime());
		//System.out.println("음력윤달"+lunar.isYunMonth());
		
		
		//사주 뽑기
		
		// 1. 년
	
		
		 //천간
		//System.out.println("년천간"+chungan[((lunar.getYear()%10)-1+yearChunganPlus)%10]);//index때문에 -1
		yearSkyVal = chungan[((lunar.getYear()%10)-1+yearChunganPlus)%10];//index때문에 -1
		 //지간
		//System.out.println("년지간"+jigan[((lunar.getYear()%12)-1+yearJiganPlus)%12]);
		yearLandVal = jigan[((lunar.getYear()%12)-1+yearJiganPlus)%12];
		

		
		// 2. 월
		
		 //천간
		int totalMon = lunar.getYear()*12+lunar.getMonth();
		//System.out.println("월천간"+chungan[((totalMon%10)-1+monthChunganPlus)%10]);
		monthSkyVal = chungan[((totalMon%10)-1+monthChunganPlus)%10];
		 //지간
		//System.out.println("월지간"+jigan[(lunar.getMonth()-1+monthJiganPlus)%12]);//index를 0부터기때문에 -1, 음력1월은 寅부터기때문에 +2
		monthLandVal = jigan[(lunar.getMonth()-1+monthJiganPlus)%12];//index를 0부터기때문에 -1, 음력1월은 寅부터기때문에 +2

		
		
		//3. 일
		int totalDays=totalDays(solar);//양력 1년 1월 1일 이후 지난 날짜수
		//천간
		//System.out.println("일천간"+chungan[(totalDays+dateChunganPlus)%10]);
		dateSkyVal = chungan[(totalDays+dateChunganPlus)%10];
		dateChunganIndex=(totalDays+dateChunganPlus)%10;//사주요약 필드를 위해
		//지간
		//System.out.println("일지간"+jigan[(totalDays+dateJiganPlus)%12]);
		dateLandVal = jigan[(totalDays+dateJiganPlus)%12];
		
		
		
		//4. 일
		
		//천간
		int time_plus=0;
		if(dateSkyVal.equals("甲") || dateSkyVal.equals("己")){
			time_plus=0;
		}else if(dateSkyVal.equals("乙") || dateSkyVal.equals("庚")){
			time_plus=2;
		}else if(dateSkyVal.equals("丙") || dateSkyVal.equals("辛")){
			time_plus=4;
		}else if(dateSkyVal.equals("丁") || dateSkyVal.equals("壬")){
			time_plus=6;
		}else if(dateSkyVal.equals("戊") || dateSkyVal.equals("癸")){
			time_plus=8;
		}
		
		//System.out.println("시천간"+chungan[(solar.getTime()+time_plus)%10]);
		timeSkyVal = chungan[(solar.getTime()+time_plus)%10];
		
		//지간
		//System.out.println("시지간"+jigan[solar.getTime()]);
		timeLandVal = jigan[solar.getTime()];
		
		
		
		
		//sajuResult.setYearSkyVal("갑");
		//sajuResult.setYearLandVal("자");
		//sajuResult.setMonthSkyVal("을");
		//sajuResult.setMonthLandVal("축");
		//sajuResult.setDateSkyVal("병");
		//sajuResult.setDateLandVal("인");
		//sajuResult.setTimeSkyVal("정");
		//sajuResult.setTimeLandVal("묘");
		
		//오행 뽑기, 오행 갯수 뽑기
		//음양 뽑기, 음양비율 뽑기
		int[] plusMinus= new int[2];//index:0(-),1(+)
		
		
		
		
		//년 천간
		if(yearSkyVal.equals("甲")  ){
			yearSky5hang="木";
			incOhhangCnt(0);	
			yearSkyPM="+";
			plusMinus[1]++;
		}else if(yearSkyVal.equals("乙")){
			yearSky5hang="木";
			incOhhangCnt(0);
			yearSkyPM="-";
			plusMinus[0]++;
		}else if(yearSkyVal.equals("丙") ){
			yearSky5hang="火";
			incOhhangCnt(1);
			yearSkyPM="+";
			plusMinus[1]++;
		}else if(yearSkyVal.equals("丁")){
			yearSky5hang="火";
			incOhhangCnt(1);
			yearSkyPM="-";
			plusMinus[0]++;
		}else if(yearSkyVal.equals("戊")){
			yearSky5hang="土";
			incOhhangCnt(2);
			yearSkyPM="+";
			plusMinus[1]++;
		}else if(  yearSkyVal.equals("己")){
			yearSky5hang="土";
			incOhhangCnt(2);
			yearSkyPM="-";
			plusMinus[0]++;
		}
		else if(yearSkyVal.equals("庚")){
			yearSky5hang="金";
			incOhhangCnt(3);
			yearSkyPM="+";
			plusMinus[1]++;
		}else if(  yearSkyVal.equals("辛")){
			yearSky5hang="金";
			incOhhangCnt(3);
			yearSkyPM="-";
			plusMinus[0]++;
		}
		else if(yearSkyVal.equals("壬")){
			yearSky5hang="水";
			incOhhangCnt(4);
			yearSkyPM="+";
			plusMinus[1]++;
		}else if(  yearSkyVal.equals("癸")){
			yearSky5hang="水";
			incOhhangCnt(4);
			yearSkyPM="-";
			plusMinus[0]++;
		}
		
		//년 지간
		if(yearLandVal.equals("寅")){
			yearLand5hang="木";
			incOhhangCnt(0);	
			yearLandPM="+";
			plusMinus[1]++;
		}else if( yearLandVal.equals("卯")){
			yearLand5hang="木";
			incOhhangCnt(0);	
			yearLandPM="-";
			plusMinus[0]++;
		}else if(yearLandVal.equals("巳")){
			yearLand5hang="火";
			incOhhangCnt(1);
			yearLandPM="-";
			plusMinus[0]++;
		}else if( yearLandVal.equals("午")){
			yearLand5hang="火";
			incOhhangCnt(1);
			yearLandPM="+";
			plusMinus[1]++;
		}else if( yearLandVal.equals("辰")  || yearLandVal.equals("戌")){
			yearLand5hang="土";
			incOhhangCnt(2);
			yearLandPM="+";
			plusMinus[1]++;
		}else if(yearLandVal.equals("丑") || yearLandVal.equals("未")){
			yearLand5hang="土";
			incOhhangCnt(2);
			yearLandPM="-";
			plusMinus[0]++;
		}else if(yearLandVal.equals("申")){
			yearLand5hang="金";
			incOhhangCnt(3);
			yearLandPM="+";
			plusMinus[1]++;
		}else if(yearLandVal.equals("酉")){
			yearLand5hang="金";
			incOhhangCnt(3);
			yearLandPM="-";
			plusMinus[0]++;
		}else if(yearLandVal.equals("亥")){
			yearLand5hang="水";
			incOhhangCnt(4);
			yearLandPM="-";
			plusMinus[0]++;
		}else if( yearLandVal.equals("子")){
			yearLand5hang="水";
			incOhhangCnt(4);
			yearLandPM="+";
			plusMinus[1]++;
		}
		
		//월 천간
		if(monthSkyVal.equals("甲")){
			monthSky5hang="木";
			incOhhangCnt(0);	
			monthSkyPM="+";
			plusMinus[1]++;
		}else if( monthSkyVal.equals("乙")){
			monthSky5hang="木";
			incOhhangCnt(0);
			monthSkyPM="-";
			plusMinus[0]++;
		}
		else if(monthSkyVal.equals("丙")){
			monthSky5hang="火";
			incOhhangCnt(1);
			monthSkyPM="+";
			plusMinus[1]++;
		}else if( monthSkyVal.equals("丁")){
			monthSky5hang="火";
			incOhhangCnt(1);
			monthSkyPM="-";
			plusMinus[0]++;
		}
		else if(monthSkyVal.equals("戊")){
			monthSky5hang="土";
			incOhhangCnt(2);
			monthSkyPM="+";
			plusMinus[1]++;
		}else if(  monthSkyVal.equals("己")){
			monthSky5hang="土";
			incOhhangCnt(2);
			monthSkyPM="-";
			plusMinus[0]++;
		}
		else if(monthSkyVal.equals("庚") ){
			monthSky5hang="金";
			incOhhangCnt(3);
			monthSkyPM="+";
			plusMinus[1]++;
		}else if( monthSkyVal.equals("辛")){
			monthSky5hang="金";
			incOhhangCnt(3);
			monthSkyPM="-";
			plusMinus[0]++;
		}
		else if(monthSkyVal.equals("壬")){
			monthSky5hang="水";
			incOhhangCnt(4);
			monthSkyPM="+";
			plusMinus[1]++;
		}else if(  monthSkyVal.equals("癸")){
			monthSky5hang="水";
			incOhhangCnt(4);
			monthSkyPM="-";
			plusMinus[0]++;
		}
		
		//월 지간
		if(monthLandVal.equals("寅")){
			monthLand5hang="木";
			incOhhangCnt(0);	
			monthLandPM="+";
			plusMinus[1]++;
		}else if( monthLandVal.equals("卯")){
			monthLand5hang="木";
			incOhhangCnt(0);
			monthLandPM="-";
			plusMinus[0]++;
		}
		else if(monthLandVal.equals("巳")){
			monthLand5hang="火";
			incOhhangCnt(1);
			monthLandPM="-";
			plusMinus[0]++;
		}else if( monthLandVal.equals("午")){
			monthLand5hang="火";
			incOhhangCnt(1);
			monthLandPM="+";
			plusMinus[1]++;
		}
		else if( monthLandVal.equals("辰")  || monthLandVal.equals("戌")){
			monthLand5hang="土";
			incOhhangCnt(2);
			monthLandPM="+";
			plusMinus[1]++;
		}else if(monthLandVal.equals("丑") || monthLandVal.equals("未")){
			monthLand5hang="土";
			incOhhangCnt(2);
			monthLandPM="-";
			plusMinus[0]++;
		}
		else if(monthLandVal.equals("申") ){
			monthLand5hang="金";
			incOhhangCnt(3);
			monthLandPM="+";
			plusMinus[1]++;
		}else if( monthLandVal.equals("酉")){
			monthLand5hang="金";
			incOhhangCnt(3);
			monthLandPM="-";
			plusMinus[0]++;
		}
		else if(monthLandVal.equals("亥")){
			monthLand5hang="水";
			incOhhangCnt(4);
			monthLandPM="-";
			plusMinus[0]++;
		}else if( monthLandVal.equals("子")){
			monthLand5hang="水";
			incOhhangCnt(4);
			monthLandPM="+";
			plusMinus[1]++;
		}
		
		//일 천간
		if(dateSkyVal.equals("甲")){
			dateSky5hang="木";
			incOhhangCnt(0);	
			dateSkyPM="+";
			plusMinus[1]++;
		}else if( dateSkyVal.equals("乙")){
			dateSky5hang="木";
			incOhhangCnt(0);
			dateSkyPM="-";
			plusMinus[0]++;
		}
		else if(dateSkyVal.equals("丙") ){
			dateSky5hang="火";
			incOhhangCnt(1);
			dateSkyPM="+";
			plusMinus[1]++;
		}else if(dateSkyVal.equals("丁")){
			dateSky5hang="火";
			incOhhangCnt(1);
			dateSkyPM="-";
			plusMinus[0]++;
		}
		else if(dateSkyVal.equals("戊") ){
			dateSky5hang="土";
			incOhhangCnt(2);
			dateSkyPM="+";
			plusMinus[1]++;
		}else if( dateSkyVal.equals("己")){
			dateSky5hang="土";
			incOhhangCnt(2);
			dateSkyPM="-";
			plusMinus[0]++;
		}
		else if(dateSkyVal.equals("庚") ){
			dateSky5hang="金";
			incOhhangCnt(3);
			dateSkyPM="+";
			plusMinus[1]++;
		}else if( dateSkyVal.equals("辛")){
			dateSky5hang="金";
			incOhhangCnt(3);
			dateSkyPM="-";
			plusMinus[0]++;
		}
		else if(dateSkyVal.equals("壬") ){
			dateSky5hang="水";
			incOhhangCnt(4);
			dateSkyPM="+";
			plusMinus[1]++;
		}else if( dateSkyVal.equals("癸")){
			dateSky5hang="水";
			incOhhangCnt(4);
			dateSkyPM="-";
			plusMinus[0]++;
		}
		
		//일 지간
		if(dateLandVal.equals("寅")){
			dateLand5hang="木";
			incOhhangCnt(0);	
			dateLandPM="+";
			plusMinus[1]++;
		}else if( dateLandVal.equals("卯")){
			dateLand5hang="木";
			incOhhangCnt(0);
			dateLandPM="-";
			plusMinus[0]++;
		}
		else if(dateLandVal.equals("巳") ){
			dateLand5hang="火";
			incOhhangCnt(1);
			dateLandPM="-";
			plusMinus[0]++;
		}else if( dateLandVal.equals("午")){
			dateLand5hang="火";
			incOhhangCnt(1);
			dateLandPM="+";
			plusMinus[1]++;
		}
		else if(dateLandVal.equals("辰")  || dateLandVal.equals("戌")){
			dateLand5hang="土";
			incOhhangCnt(2);
			dateLandPM="+";
			plusMinus[1]++;
		}else if(dateLandVal.equals("丑") || dateLandVal.equals("未") ){
			dateLand5hang="土";
			incOhhangCnt(2);
			dateLandPM="-";
			plusMinus[0]++;
		}
		else if(dateLandVal.equals("申")){
			dateLand5hang="金";
			incOhhangCnt(3);
			dateLandPM="+";
			plusMinus[1]++;
		}else if( dateLandVal.equals("酉")){
			dateLand5hang="金";
			incOhhangCnt(3);
			dateLandPM="-";
			plusMinus[0]++;
		}
		else if(dateLandVal.equals("亥")){
			dateLand5hang="水";
			incOhhangCnt(4);
			dateLandPM="-";
			plusMinus[0]++;
		}else if(  dateLandVal.equals("子")){
			dateLand5hang="水";
			incOhhangCnt(4);
			dateLandPM="+";
			plusMinus[1]++;
		}
		
		//시 천간
		if(timeSkyVal.equals("甲")){
			timeSky5hang="木";
			incOhhangCnt(0);	
			timeSkyPM="+";
			plusMinus[1]++;
		}else if(  timeSkyVal.equals("乙")){
			timeSky5hang="木";
			incOhhangCnt(0);	
			timeSkyPM="-";
			plusMinus[0]++;
		}
		else if(timeSkyVal.equals("丙") ){
			timeSky5hang="火";
			incOhhangCnt(1);
			timeSkyPM="+";
			plusMinus[1]++;
		}else if( timeSkyVal.equals("丁")){
			timeSky5hang="火";
			incOhhangCnt(1);
			timeSkyPM="-";
			plusMinus[0]++;
		}
		else if(timeSkyVal.equals("戊")){
			timeSky5hang="土";
			incOhhangCnt(2);
			timeSkyPM="+";
			plusMinus[1]++;
		}else if( timeSkyVal.equals("己")){
			timeSky5hang="土";
			incOhhangCnt(2);
			timeSkyPM="-";
			plusMinus[0]++;
		}
		else if(timeSkyVal.equals("庚")){
			timeSky5hang="金";
			incOhhangCnt(3);
			timeSkyPM="+";
			plusMinus[1]++;
		}else if(  timeSkyVal.equals("辛")){
			timeSky5hang="金";
			incOhhangCnt(3);
			timeSkyPM="-";
			plusMinus[0]++;
		}
		else if(timeSkyVal.equals("壬")){
			timeSky5hang="水";
			incOhhangCnt(4);
			timeSkyPM="+";
			plusMinus[1]++;
		}else if(  timeSkyVal.equals("癸")){
			timeSky5hang="水";
			incOhhangCnt(4);
			timeSkyPM="-";
			plusMinus[0]++;
		}
		
		//시 지간
		if(timeLandVal.equals("寅") ){
			timeLand5hang="木";
			incOhhangCnt(0);	
			timeLandPM="+";
			plusMinus[1]++;
		}else if( timeLandVal.equals("卯")){
			timeLand5hang="木";
			incOhhangCnt(0);
			timeLandPM="-";
			plusMinus[0]++;
		}
		else if(timeLandVal.equals("巳") ){
			timeLand5hang="火";
			incOhhangCnt(1);
			timeLandPM="-";
			plusMinus[0]++;
		}else if( timeLandVal.equals("午")){
			timeLand5hang="火";
			incOhhangCnt(1);
			timeLandPM="+";
			plusMinus[1]++;
		}
		else if( timeLandVal.equals("辰") || timeLandVal.equals("戌")){
			timeLand5hang="土";
			incOhhangCnt(2);
			timeLandPM="+";
			plusMinus[1]++;
		}else if(timeLandVal.equals("丑")  || timeLandVal.equals("未")){
			timeLand5hang="土";
			incOhhangCnt(2);
			timeLandPM="-";
			plusMinus[0]++;
		}
		else if(timeLandVal.equals("申") ){
			timeLand5hang="金";
			incOhhangCnt(3);
			timeLandPM="+";
			plusMinus[1]++;
		}else if( timeLandVal.equals("酉")){
			timeLand5hang="金";
			incOhhangCnt(3);
			timeLandPM="-";
			plusMinus[0]++;
		}
		else if(timeLandVal.equals("亥")){
			timeLand5hang="水";
			incOhhangCnt(4);
			timeLandPM="-";
			plusMinus[0]++;
		}else if(  timeLandVal.equals("子")){
			timeLand5hang="水";
			incOhhangCnt(4);
			timeLandPM="+";
			plusMinus[1]++;
		}

		
		
		
		if(plusMinus[0]==0){
			plusPercent=100;
			minusPercent=0;
		}else if(plusMinus[0]==1){
			plusPercent=87;
			minusPercent=13;
		}else if(plusMinus[0]==2){
			plusPercent=75;
			minusPercent=25;
		}else if(plusMinus[0]==3){
			plusPercent=62;
			minusPercent=38;
		}else if(plusMinus[0]==4){
			plusPercent=50;
			minusPercent=50;
		}else if(plusMinus[0]==5){
			plusPercent=37;
			minusPercent=63;
		}else if(plusMinus[0]==6){
			plusPercent=25;
			minusPercent=75;
		}else if(plusMinus[0]==7){
			plusPercent=13;
			minusPercent=87;
		}else if(plusMinus[0]==8){
			plusPercent=0;
			minusPercent=100;
		}else {
			plusPercent=0;
			minusPercent=0;
		}
		
		
		
		
		
		//사주요약 뽑기
		yoyakDesc = yoyak_desc[dateChunganIndex];
		
		
		
		
	}
	
	
	
	
	// 서기 1년 1월 1일 이후 지난 날짜수를 반환
	static private int totalDays(CalendarData solar) {
		int i, sum, tdays, nYears366;
		
		if (((solar.getYear() % 4 == 0) && (solar.getYear() % 100 != 0)) || (solar.getYear() % 400 == 0)) MonthTable[1] = 29;
		else MonthTable[1] = 28;
		
		sum = 0;
		for (i=0;i<solar.getMonth();i++) {
			sum = sum + MonthTable[i];
		}
		
		nYears366 = (solar.getYear() - 1) / 4 - (solar.getYear() - 1) / 100 + (solar.getYear() - 1) / 400;

		tdays = (solar.getYear() - 1) * 365 + sum + nYears366 + solar.getDate() - 1;

		return tdays;
	}
	
	
	// 양력날짜를 음력데이터형식의 날짜로 반환
	static private CalendarData SolarToLunar(CalendarData solar) {
		int i, nDays, tmp;
		int FIRST_DAY;					// 서기 1년 1월 1일부터 음력 1881년 1월 1일까지 총 지난 날짜에 관한 변수
		
		FIRST_DAY = 686685;
		nDays = totalDays(solar) - FIRST_DAY;	//음력 1881년 1월 1일 이후 지난 날짜
		
		CalendarData  lunar_date = new CalendarData();			// 반환할 음력 날짜를 선언. 음력 첫날로 초기화
		lunar_date.setYear(1881);
		lunar_date.setMonth(0);
		lunar_date.setDate(1);
		lunar_date.setYunMonth(false);

		// nDays가 0보다 작아질때 까지, 각년도의 총 날짜수를 빼는 걸 반복해 그 루프횟수로서 현재 년도를 계산.
		// 이 루프가 종료됨과 동시에 음력데이터의 year속성은 현재 년도가 저장되게 된다.
		int tmpYear;
		do {
			tmp = nDays;
			nDays -= nDaysYear(lunar_date.getYear());
			if (nDays < 0) {
				nDays = tmp;
				break;
			}
			tmpYear = lunar_date.getYear()+1;
			lunar_date.setYear(tmpYear);  
		} while (true);
		
		// 1년총날짜 이하로 작아지 nDays를 마찬가지로 월 단위로 빼는걸 반복해 현재 월을 계산.
		// 만약에 다음루프에서 윤달이면 월을 증가시키는게 아니라 윤달 속성만 true로 설정.
		do {
			tmp = nDays;
			nDays -= nDaysMonth(lunar_date);;
			if (nDays < 0) {
				nDays = tmp;
				break;
			}
			
			if (lunar_date.getMonth() == YunMonth(lunar_date.getYear())&&!lunar_date.isYunMonth()) {
				lunar_date.setYunMonth(true);
			}
			else {
				int tmp_m = lunar_date.getMonth()+1;
				lunar_date.setMonth(tmp_m);
				lunar_date.setYunMonth(false);
			}
		} while (true);
		
		// 마지막으로 월단위 날짜수 이하로 작아진 nDays를 이용해 날짜를 계산
		lunar_date.setDate(nDays + 1);
		
		return lunar_date;
	}
	
	//해당 음력년도의 전체 날짜를 반환하는 함수
	static private int nDaysYear(int year) {
		int i, sum;
		
		sum = 0;
		for (i=0;i<13;i++) {
			if (Integer.parseInt(LunarTable[year-1881].substring(i, i+1))!=0) {
				sum += 29 + (Integer.parseInt(LunarTable[year - 1881].substring(i, i+1)) + 1) % 2;
			}
		}
		
		return sum;
	}
	
	
	// 해당 음력년도의 윤달넘버를 반환. 윤달이 없으면 12를 반환
	// 6월이 윤달이면 5반환
	static private int YunMonth(int year) {
		int  yun;
		
		yun = 0;
		do {
			if (Integer.parseInt(LunarTable[year-1881].substring(yun,yun+1)) > 2) {
				break;
			}
			yun++;
		} while (yun <= 12);
		
		return yun - 1;
	}
	
	//해당 음력 월의 날짜수를 반환하는 함수
	//윤달이면 한달을 건너띄도록 함 : 해당년에 윤6월이 있는데, 7월달의 날짜수를 구할때
	//index를 하나건너띄어서 찾음(7월은 원래 6번째index에서 값을 가져오는데 7번째index에서 가져오도록함)
	static private int nDaysMonth(CalendarData lunar_date) {
		int nDays, yun;

		if (lunar_date.getMonth() <= YunMonth(lunar_date.getYear()) && !lunar_date.isYunMonth()) yun = 0;
		else yun = 1;
		
		
		
		
		nDays = 29 + (Integer.parseInt(LunarTable[lunar_date.getYear() - 1881].substring(lunar_date.getMonth() + yun, lunar_date.getMonth() + yun+1) ) + 1) % 2;

		return nDays;
	}
  
	
	
	
	
	 static private String isPlusMinusSky(String value){
			//년 천간
				if(value.equals("甲")  ){
					return "+";
				}else if(value.equals("乙")){
					return "-";
				}else if(value.equals("丙") ){
					return "+";
				}else if(value.equals("丁")){
					return "-";
				}else if(value.equals("戊")){
					return "+";
				}else if(value.equals("己")){
					return "-";
				}
				else if(value.equals("庚")){
					return "+";
				}else if(value.equals("辛")){
					return "-";
				}else if(value.equals("壬")){
					return "+";
				}else if(value.equals("癸")){
					return "-";
				}else{
					return "error";
				}
		  }
		  
		  private String isPlusMinusLand(String value){
				
					
					//년 지간
					if(value.equals("寅")){
						return "+";
					}else if( value.equals("卯")){
						return "-";
					}else if(value.equals("巳")){
						return "-";
					}else if( value.equals("午")){
						return "+";
					}else if( value.equals("辰")){
						return "+";
					}else if(  value.equals("戌")){
						return "+";
					}else if(value.equals("丑") ){
						return "-";
					}else if( value.equals("未")){
						return "-";
					}
					else if(value.equals("申")){
						return "+";
					}else if(value.equals("酉")){
						return "-";
					}else if(value.equals("亥")){
						return "-";
					}else if( value.equals("子")){
						return "+";
					}else{
						return "error";
					}
		  
		  }
	
	
	
	 //
	  static private SajuDataTable makePlusSaju(SajuDataTable mySaju, SajuDataTable currentSaju){
		  SajuDataTable result = new SajuDataTable();
		  //내 사주, 현재시간 사주를 조합한 사주를 계산한다.
		  
		  
		  
		  //new
		  //일간 합, 충
		  if(mySaju.getDateSkyVal().equals("甲")){
			  if(currentSaju.getDateSkyVal().equals("戊")){
				  result.setSkyHab("甲戊");
			  }else if(currentSaju.getDateSkyVal().equals("庚")){
				  result.setSkyChung("甲庚");
			  }
		  }else if(mySaju.getDateSkyVal().equals("乙")){
			  if(currentSaju.getDateSkyVal().equals("庚")){
				  result.setSkyHab("乙庚");
			  }else if(currentSaju.getDateSkyVal().equals("辛")){
				  result.setSkyChung("乙辛");
			  }
		  }else if(mySaju.getDateSkyVal().equals("丙")){
			  if(currentSaju.getDateSkyVal().equals("辛")){
				  result.setSkyHab("丙辛");
			  }else if(currentSaju.getDateSkyVal().equals("壬")){
				  result.setSkyChung("丙壬");
			  }
		  }else if(mySaju.getDateSkyVal().equals("丁")){
			  if(currentSaju.getDateSkyVal().equals("壬")){
				  result.setSkyHab("丁壬");
			  }else if(currentSaju.getDateSkyVal().equals("癸")){
				  result.setSkyChung("丁癸");
			  }
		  }else if(mySaju.getDateSkyVal().equals("戊")){
			  if(currentSaju.getDateSkyVal().equals("癸")){
				  result.setSkyChung("戊癸");
			  }
		  }
		  
		  //음양
		
			int[] plusMinus= new int[2];//index:0(-),1(+)
			
			
			//사주 1
			String resultPM = isPlusMinusSky(mySaju.getYearSkyVal());
			if(resultPM.equals("-")){
				plusMinus[0]++;
			}else if(resultPM.equals("+")){
				plusMinus[1]++;
			} 
			resultPM = isPlusMinusSky(mySaju.getMonthSkyVal());
			if(resultPM.equals("-")){
				plusMinus[0]++;
			}else if(resultPM.equals("+")){
				plusMinus[1]++;
			} 
			resultPM = isPlusMinusSky(mySaju.getDateSkyVal());
			if(resultPM.equals("-")){
				plusMinus[0]++;
			}else if(resultPM.equals("+")){
				plusMinus[1]++;
			} 
			resultPM = isPlusMinusSky(mySaju.getTimeSkyVal());
			if(resultPM.equals("-")){
				plusMinus[0]++;
			}else if(resultPM.equals("+")){
				plusMinus[1]++;
			} 
			
			//사주2
			resultPM = isPlusMinusSky(currentSaju.getYearSkyVal());
			if(resultPM.equals("-")){
				plusMinus[0]++;
			}else if(resultPM.equals("+")){
				plusMinus[1]++;
			} 
			resultPM = isPlusMinusSky(currentSaju.getMonthSkyVal());
			if(resultPM.equals("-")){
				plusMinus[0]++;
			}else if(resultPM.equals("+")){
				plusMinus[1]++;
			} 
			resultPM = isPlusMinusSky(currentSaju.getDateSkyVal());
			if(resultPM.equals("-")){
				plusMinus[0]++;
			}else if(resultPM.equals("+")){
				plusMinus[1]++;
			} 
			resultPM = isPlusMinusSky(currentSaju.getTimeSkyVal());
			if(resultPM.equals("-")){
				plusMinus[0]++;
			}else if(resultPM.equals("+")){
				plusMinus[1]++;
			} 
			
			//System.out.println(plusMinus[0]+"-"+plusMinus[1]);
		  if(plusMinus[0]==0){
				result.setPlusPercent(100);
				result.setMinusPercent(0);
			}else if(plusMinus[0]==1){
				result.setPlusPercent(87);
				result.setMinusPercent(13);
			}else if(plusMinus[0]==2){
				result.setPlusPercent(75);
				result.setMinusPercent(25);
			}else if(plusMinus[0]==3){
				result.setPlusPercent(62);
				result.setMinusPercent(38);
			}else if(plusMinus[0]==4){
				result.setPlusPercent(50);
				result.setMinusPercent(50);
			}else if(plusMinus[0]==5){
				result.setPlusPercent(37);
				result.setMinusPercent(63);
			}else if(plusMinus[0]==6){
				result.setPlusPercent(25);
				result.setMinusPercent(75);
			}else if(plusMinus[0]==7){
				result.setPlusPercent(13);
				result.setMinusPercent(87);
			}else if(plusMinus[0]==8){
				result.setPlusPercent(0);
				result.setMinusPercent(100);
			}else {
				result.setPlusPercent(0);
				result.setMinusPercent(0);
			}
		  
		  //불혼개폐살 여부
		  if(mySaju.getMonthLandVal().equals("寅") ){
			  if(currentSaju.getMonthLandVal().equals("酉")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("酉") ){
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("卯") ){
			  if(currentSaju.getMonthLandVal().equals("申")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("申") ){
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("子") ){
			  if(currentSaju.getMonthLandVal().equals("未")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("未") ){
			  if(currentSaju.getMonthLandVal().equals("子")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("丑") ){
			  if(currentSaju.getMonthLandVal().equals("午")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("午") ){
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("辰") ){
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("亥") ){
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("巳") ){
			  if(currentSaju.getMonthLandVal().equals("戌")){
				  result.setNeverMarriage(true);
			  }
		  }else if(mySaju.getMonthLandVal().equals("戌") ){
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  result.setNeverMarriage(true);
			  }
		  }
		  
		  
		  
		  
		  //5행 갯수
		  float[] ohhangAvg = new float[5];
		  ohhangAvg[0]=(mySaju.getOhhangCnt()[0]+currentSaju.getOhhangCnt()[0])/2.0f;
		  ohhangAvg[1]=(mySaju.getOhhangCnt()[1]+currentSaju.getOhhangCnt()[1])/2.0f;
		  ohhangAvg[2]=(mySaju.getOhhangCnt()[2]+currentSaju.getOhhangCnt()[2])/2.0f;
		  ohhangAvg[3]=(mySaju.getOhhangCnt()[3]+currentSaju.getOhhangCnt()[3])/2.0f;
		  ohhangAvg[4]=(mySaju.getOhhangCnt()[4]+currentSaju.getOhhangCnt()[4])/2.0f;
		  result.setOhhangCntAvg(ohhangAvg);
		  
		  //합계산(상대방끼리 합)
		  //내사주에 해가 있고 현재시간사주에 해, 묘가 있다면 2개로 계산
		  //내사주에 해가 있고 현재시간사주에 해가 있다면 0개로 계산  =>합이 존재할 경우만 같은 동물도 합으로 계산 하였음
		  //같은 동물만 있는경우는 제외
		  int mokPlus=0;
		  int whaPlus=0;
		  int geumPlus=0;
		  int suPlus=0;
		  
		  int mokPlusArray[]={0,0,0};
		  int whaPlusArray[]={0,0,0};
		  int geumPlusArray[]={0,0,0};//사, 유, 축
		  int suPlusArray[]={0,0,0};//신, 자, 진
		  
		  
		  
		  
		  //년
		  if(mySaju.getYearLandVal().equals("子")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getYearLandVal().equals("丑")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getYearLandVal().equals("寅")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getYearLandVal().equals("卯")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
		  }else if(mySaju.getYearLandVal().equals("辰")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getYearLandVal().equals("巳")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getYearLandVal().equals("午")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getYearLandVal().equals("未")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
		  }else if(mySaju.getYearLandVal().equals("申")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getYearLandVal().equals("酉")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getYearLandVal().equals("戌")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getYearLandVal().equals("亥")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
		  }
		  
		  
		  
		//월
		  if(mySaju.getMonthLandVal().equals("子")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getMonthLandVal().equals("丑")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getMonthLandVal().equals("寅")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getMonthLandVal().equals("卯")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
		  }else if(mySaju.getMonthLandVal().equals("辰")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getMonthLandVal().equals("巳")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getMonthLandVal().equals("午")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getMonthLandVal().equals("未")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
		  }else if(mySaju.getMonthLandVal().equals("申")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getMonthLandVal().equals("酉")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getMonthLandVal().equals("戌")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getMonthLandVal().equals("亥")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
		  }
		  
		  
		  
		  
		//일
		  if(mySaju.getDateLandVal().equals("子")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getDateLandVal().equals("丑")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getDateLandVal().equals("寅")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getDateLandVal().equals("卯")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
		  }else if(mySaju.getDateLandVal().equals("辰")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getDateLandVal().equals("巳")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getDateLandVal().equals("午")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getDateLandVal().equals("未")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
		  }else if(mySaju.getDateLandVal().equals("申")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getDateLandVal().equals("酉")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getDateLandVal().equals("戌")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getDateLandVal().equals("亥")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
		  }
		  
		  
		  
		  
		//시
		  if(mySaju.getTimeLandVal().equals("子")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[1]++;//자
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getTimeLandVal().equals("丑")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[2]++;//축
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getTimeLandVal().equals("寅")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[0]++;//인
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getTimeLandVal().equals("卯")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[1]++;//묘
				  mokPlusArray[2]++;//미
			  }
		  }else if(mySaju.getTimeLandVal().equals("辰")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[2]++;//진
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getTimeLandVal().equals("巳")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[0]++;//사
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getTimeLandVal().equals("午")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[1]++;//오
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getTimeLandVal().equals("未")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[2]++;//미
				  mokPlusArray[2]++;//미
			  }
		  }else if(mySaju.getTimeLandVal().equals("申")){
			  if(currentSaju.getYearLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getYearLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getYearLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getMonthLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getDateLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getDateLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getDateLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("申")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[0]++;//신
			  }else if(currentSaju.getTimeLandVal().equals("子")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[1]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("辰")){
				  suPlus++;
				  suPlusArray[0]++;//신
				  suPlusArray[2]++;//진
			  }
		  }else if(mySaju.getTimeLandVal().equals("酉")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getYearLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getMonthLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getDateLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[1]++;//유
			  }else if(currentSaju.getTimeLandVal().equals("丑")){
				  geumPlus++;
				  geumPlusArray[1]++;//유
				  geumPlusArray[2]++;//축
			  }
		  }else if(mySaju.getTimeLandVal().equals("戌")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[1]++;//오
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  whaPlus++;
				  whaPlusArray[2]++;//술
				  whaPlusArray[2]++;//술
			  }
		  }else if(mySaju.getTimeLandVal().equals("亥")){
			  if(currentSaju.getYearLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getYearLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getMonthLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getDateLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("亥")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[0]++;//해
			  }else if(currentSaju.getTimeLandVal().equals("卯")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[1]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  mokPlus++;
				  mokPlusArray[0]++;//해
				  mokPlusArray[2]++;//미
			  }
		  }
		  
		  //계산된 합중 하나의 동물로만 합이 계산된 것이 있다면 제외
		  if(mokPlus!=0){
			  int cnt=0;
			  for(int a=0;a<3;a++){
				  if(mokPlusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==2){//3개 동물중 하나로만 합이 계산되었을 경우 합은 없던 것으로 함
				  mokPlus=0;
			  }
		  }
		  
		  if(whaPlus!=0){
			  int cnt=0;
			  for(int a=0;a<3;a++){
				  if(whaPlusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==2){//3개 동물중 하나로만 합이 계산되었을 경우 합은 없던 것으로 함
				  whaPlus=0;
			  }
		  }
		  
		  if(geumPlus!=0){
			  int cnt=0;
			  for(int a=0;a<3;a++){
				  if(geumPlusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==2){//3개 동물중 하나로만 합이 계산되었을 경우 합은 없던 것으로 함
				  geumPlus=0;
			  }
		  }
		  
		  if(suPlus!=0){
			  int cnt=0;
			  for(int a=0;a<3;a++){
				  if(suPlusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==2){//3개 동물중 하나로만 합이 계산되었을 경우 합은 없던 것으로 함
				  suPlus=0;
			  }
		  }
		  
		  
		  result.setMokPlus(mokPlus);
		  result.setWhaPlus(whaPlus);
		  result.setGeumPlus(geumPlus);
		  result.setSuPlus(suPlus);
		  
		  
		  
		  //살 계산
		  int jamiMinus=0;
		  int chukohMinus=0;
		  int inyuMinus=0;
		  int myosinMinus=0;
		  int jinhaeMinus=0;
		  int sasulMinus=0;
		  
		  int jamiMinusArray[]={0,0};//자, 미
		  int chukohMinusArray[]={0,0};
		  int inyuMinusArray[]={0,0};
		  int myosinMinusArray[]={0,0};
		  int jinhaeMinusArray[]={0,0};
		  int sasulMinusArray[]={0,0};
		  
		//년
		  if(mySaju.getYearLandVal().equals("子")){
			  if(currentSaju.getYearLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
		  }else if(mySaju.getYearLandVal().equals("丑")){
			  if(currentSaju.getYearLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getDateLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
		  }else if(mySaju.getYearLandVal().equals("寅")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
		  }else if(mySaju.getYearLandVal().equals("卯")){
			  if(currentSaju.getYearLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getDateLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
		  }else if(mySaju.getYearLandVal().equals("辰")){
			  if(currentSaju.getYearLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getYearLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getMonthLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getDateLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getDateLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getTimeLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
		  }else if(mySaju.getYearLandVal().equals("巳")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
		  }else if(mySaju.getYearLandVal().equals("午")){
			  if(currentSaju.getYearLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getDateLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
		  }else if(mySaju.getYearLandVal().equals("未")){
			  if(currentSaju.getYearLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
		  }else if(mySaju.getYearLandVal().equals("申")){
			  if(currentSaju.getYearLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getDateLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
		  }else if(mySaju.getYearLandVal().equals("酉")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
		  }else if(mySaju.getYearLandVal().equals("戌")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
		  }else if(mySaju.getYearLandVal().equals("亥")){
			  if(currentSaju.getYearLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getYearLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getMonthLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getDateLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getDateLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getTimeLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
		  }
		  
		  
			//월
		  if(mySaju.getMonthLandVal().equals("子")){
			  if(currentSaju.getYearLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
		  }else if(mySaju.getMonthLandVal().equals("丑")){
			  if(currentSaju.getYearLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getDateLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
		  }else if(mySaju.getMonthLandVal().equals("寅")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
		  }else if(mySaju.getMonthLandVal().equals("卯")){
			  if(currentSaju.getYearLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getDateLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
		  }else if(mySaju.getMonthLandVal().equals("辰")){
			  if(currentSaju.getYearLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getYearLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getMonthLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getDateLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getDateLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getTimeLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
		  }else if(mySaju.getMonthLandVal().equals("巳")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
		  }else if(mySaju.getMonthLandVal().equals("午")){
			  if(currentSaju.getYearLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getDateLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
		  }else if(mySaju.getMonthLandVal().equals("未")){
			  if(currentSaju.getYearLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
		  }else if(mySaju.getMonthLandVal().equals("申")){
			  if(currentSaju.getYearLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getDateLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
		  }else if(mySaju.getMonthLandVal().equals("酉")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
		  }else if(mySaju.getMonthLandVal().equals("戌")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
		  }else if(mySaju.getMonthLandVal().equals("亥")){
			  if(currentSaju.getYearLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getYearLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getMonthLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getDateLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getDateLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getTimeLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
		  }
		  
		  
		//일
		  if(mySaju.getDateLandVal().equals("子")){
			  if(currentSaju.getYearLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
		  }else if(mySaju.getDateLandVal().equals("丑")){
			  if(currentSaju.getYearLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getDateLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
		  }else if(mySaju.getDateLandVal().equals("寅")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
		  }else if(mySaju.getDateLandVal().equals("卯")){
			  if(currentSaju.getYearLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getDateLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
		  }else if(mySaju.getDateLandVal().equals("辰")){
			  if(currentSaju.getYearLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getYearLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getMonthLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getDateLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getDateLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getTimeLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
		  }else if(mySaju.getDateLandVal().equals("巳")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
		  }else if(mySaju.getDateLandVal().equals("午")){
			  if(currentSaju.getYearLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getDateLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
		  }else if(mySaju.getDateLandVal().equals("未")){
			  if(currentSaju.getYearLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
		  }else if(mySaju.getDateLandVal().equals("申")){
			  if(currentSaju.getYearLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getDateLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
		  }else if(mySaju.getDateLandVal().equals("酉")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
		  }else if(mySaju.getDateLandVal().equals("戌")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
		  }else if(mySaju.getDateLandVal().equals("亥")){
			  if(currentSaju.getYearLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getYearLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getMonthLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getDateLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getDateLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getTimeLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
		  }
		  
		  
		//시
		  if(mySaju.getTimeLandVal().equals("子")){
			  if(currentSaju.getYearLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[0]++;//자
				  jamiMinusArray[1]++;//미
			  }
		  }else if(mySaju.getTimeLandVal().equals("丑")){
			  if(currentSaju.getYearLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getDateLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[0]++;//축
				  chukohMinusArray[1]++;//오
			  }
		  }else if(mySaju.getTimeLandVal().equals("寅")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[0]++;//인
				  inyuMinusArray[1]++;//유
			  }
		  }else if(mySaju.getTimeLandVal().equals("卯")){
			  if(currentSaju.getYearLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getDateLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[0]++;//묘
				  myosinMinusArray[1]++;//신
			  }
		  }else if(mySaju.getTimeLandVal().equals("辰")){
			  if(currentSaju.getYearLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getYearLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getMonthLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getDateLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getDateLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getTimeLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[0]++;//진
				  jinhaeMinusArray[1]++;//해
			  }
		  }else if(mySaju.getTimeLandVal().equals("巳")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[0]++;//사
				  sasulMinusArray[1]++;//술
			  }
		  }else if(mySaju.getTimeLandVal().equals("午")){
			  if(currentSaju.getYearLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getYearLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getMonthLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getDateLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getDateLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("丑")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[0]++;//축
			  }else if(currentSaju.getTimeLandVal().equals("午")){
				  chukohMinus++;
				  chukohMinusArray[1]++;//오
				  chukohMinusArray[1]++;//오
			  }
		  }else if(mySaju.getTimeLandVal().equals("未")){
			  if(currentSaju.getYearLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getYearLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getMonthLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getDateLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getDateLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("子")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[0]++;//자
			  }else if(currentSaju.getTimeLandVal().equals("未")){
				  jamiMinus++;
				  jamiMinusArray[1]++;//미
				  jamiMinusArray[1]++;//미
			  }
		  }else if(mySaju.getTimeLandVal().equals("申")){
			  if(currentSaju.getYearLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getYearLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getMonthLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getDateLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getDateLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("卯")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[0]++;//묘
			  }else if(currentSaju.getTimeLandVal().equals("申")){
				  myosinMinus++;
				  myosinMinusArray[1]++;//신
				  myosinMinusArray[1]++;//신
			  }
		  }else if(mySaju.getTimeLandVal().equals("酉")){
			  if(currentSaju.getYearLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getYearLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getMonthLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getDateLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getDateLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("寅")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[0]++;//인
			  }else if(currentSaju.getTimeLandVal().equals("酉")){
				  inyuMinus++;
				  inyuMinusArray[1]++;//유
				  inyuMinusArray[1]++;//유
			  }
		  }else if(mySaju.getTimeLandVal().equals("戌")){
			  if(currentSaju.getYearLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getYearLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getMonthLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getDateLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getDateLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("巳")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[0]++;//사
			  }else if(currentSaju.getTimeLandVal().equals("戌")){
				  sasulMinus++;
				  sasulMinusArray[1]++;//술
				  sasulMinusArray[1]++;//술
			  }
		  }else if(mySaju.getTimeLandVal().equals("亥")){
			  if(currentSaju.getYearLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getYearLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getMonthLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getMonthLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getDateLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getDateLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
			  
			  if(currentSaju.getTimeLandVal().equals("辰")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[0]++;//진
			  }else if(currentSaju.getTimeLandVal().equals("亥")){
				  jinhaeMinus++;
				  jinhaeMinusArray[1]++;//해
				  jinhaeMinusArray[1]++;//해
			  }
		  }
		  
		  
		//계산된 살중 하나의 동물로만 살이 계산된 것이 있다면 제외
		  if(jamiMinus!=0){
			  int cnt=0;
			  for(int a=0;a<2;a++){
				  if(jamiMinusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==1){//2개 동물중 하나로만 살이 계산되었을 경우 살은 없던 것으로 함
				  jamiMinus=0;
			  }
		  }
		  
		  if(inyuMinus!=0){
			  int cnt=0;
			  for(int a=0;a<2;a++){
				  if(inyuMinusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==1){//2개 동물중 하나로만 살이 계산되었을 경우 살은 없던 것으로 함
				  inyuMinus=0;
			  }
		  }
		  
		  if(myosinMinus!=0){
			  int cnt=0;
			  for(int a=0;a<2;a++){
				  if(myosinMinusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==1){//2개 동물중 하나로만 살이 계산되었을 경우 살은 없던 것으로 함
				  myosinMinus=0;
			  }
		  }
		  
		  if(jinhaeMinus!=0){
			  int cnt=0;
			  for(int a=0;a<2;a++){
				  if(jinhaeMinusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==1){//2개 동물중 하나로만 살이 계산되었을 경우 살은 없던 것으로 함
				  jinhaeMinus=0;
			  }
		  }
		  
		  if(sasulMinus!=0){
			  int cnt=0;
			  for(int a=0;a<2;a++){
				  if(sasulMinusArray[a]==0){
					  cnt++;
				  }
			  }
			  if(cnt==1){//2개 동물중 하나로만 살이 계산되었을 경우 살은 없던 것으로 함
				  sasulMinus=0;
			  }
		  }
		  
		  result.setJamiMinus(jamiMinus);
		  result.setInyuMinus(inyuMinus);
		  result.setMyosinMinus(myosinMinus);
		  result.setJinhaeMinus(jinhaeMinus);
		  result.setSasulMinus(sasulMinus);
		  result.setChukohMinus(chukohMinus);
		  
		  
		  
		  
		//점수 계산후 return
			int totalScore=0;
			int skyHab=0;
			int skyChung=0;
			int minusPlusPercent=0;
			int hab=0;
			int sal=0;
			int ohhang=0;
			
			//총점 100점
			// 일간 맞는것
			if(result.getSkyHab().length()==2){
				skyHab=100;
			}
			
			// 음양의 조화
			if(result.getMinusPercent()==0){
				minusPlusPercent=0;
			}else if(result.getMinusPercent()==13){
				minusPlusPercent=25;
			}else if(result.getMinusPercent()==25){
				minusPlusPercent=50;
			}else if(result.getMinusPercent()==38){
				minusPlusPercent=75;
			}else if(result.getMinusPercent()==50){
				minusPlusPercent=100;
			}else if(result.getMinusPercent()==62){
				minusPlusPercent=75;
			}else if(result.getMinusPercent()==75){
				minusPlusPercent=50;
			}else if(result.getMinusPercent()==87){
				minusPlusPercent=25;
			}else if(result.getMinusPercent()==100){
				minusPlusPercent=0;
			}
			
			// 합
			int sumPlus = result.getMokPlus()+result.getWhaPlus()+result.getGeumPlus()+result.getSuPlus();
			if(sumPlus>=8){
				hab=100;
			}else if(sumPlus>=6){
				hab=80;
			}else if(sumPlus>=4){
				hab=60;
			}else if(sumPlus>=2){
				hab=40;
			}else if(sumPlus>=1){
				hab=20;
			}else{
				hab=0;
			}
			
			
			
			// 오행의 조화
		    if(result.getOhhangCntAvg()[0]>=4){
		    	ohhang=0;
		    }else if(result.getOhhangCntAvg()[0]==3.5){
		    	ohhang=20;
		    }else if(result.getOhhangCntAvg()[0]==3 ){
		    	ohhang=50;
		    }else if(result.getOhhangCntAvg()[0]==2.5){
		    	ohhang=80;
		    }else if(result.getOhhangCntAvg()[0]==2){
		    	ohhang=100;
		    }else if(result.getOhhangCntAvg()[0]==1.5){
		    	ohhang=80;
		    }else if(result.getOhhangCntAvg()[0]==1){
		    	ohhang=50;
		    }else if(result.getOhhangCntAvg()[0]==0.5){
		    	ohhang=20;
		    }else{
		    	ohhang=0;
		    }
			
		    
		    
		    ////////////감점
		   //일간
		    if(result.getSkyChung().length()==2){
				skyChung=-100;
			}
		    //살
			int salPlus = result.getJamiMinus()+result.getChukohMinus()+result.getInyuMinus()+result.getMyosinMinus()+result.getJinhaeMinus()+result.getSasulMinus();
			if(salPlus>=8){
				sal=-100;
			}else if(salPlus>=6){
				sal=-80;
			}else if(salPlus>=4){
				sal=-60;
			}else if(salPlus>=2){
				sal=-40;
			}else if(salPlus>=1){
				sal=-20;
			}else{
				sal=0;
			}
			
			if(result.isNeverMarriage()){
				result.setTotalScore(0);
			}else{
				totalScore=(int)Math.round((skyHab*0.2)+(minusPlusPercent*0.2)+(hab*0.3)+(ohhang*0.3)+(sal*0.3)+(skyChung*0.3));
			    result.setTotalScore(totalScore);
			}
			
		    System.out.println(currentSaju.getEmail()+"_"+Integer.toString(skyHab)+"_"+Integer.toString(minusPlusPercent)+"_"+Integer.toString(hab)+"_"+Integer.toString(ohhang)+"_"+Integer.toString(sal)+"_"+Integer.toString(skyChung));
		    
		  return result;
	  }
	  
	
	
	
	
	
  /**
   * Remove this object from the data store.
   */
  /*
  public void remove() {
    EntityManager em = entityManager();
    try {
      Task task = em.find(Task.class, this.id);

      // Verify the current user owns the task before removing it.
      if (UserServiceWrapper.get().getCurrentUserId().equals(task.userId)) {
        em.remove(task);
      }
    } finally {
      em.close();
    }
  }
  */
  
  
}
