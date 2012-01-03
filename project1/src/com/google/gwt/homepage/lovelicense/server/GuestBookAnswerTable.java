
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
public class GuestBookAnswerTable {

	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	
		
	@Persistent
	private Integer version;
	
	
	
	
	@Persistent
	private Date reg_dt;//등록일
	
	@Persistent
	private Date modi_dt;//수정일
	
	@Persistent
	private String email;//등록자
	
	
	
	@Persistent
	private Boolean is_deleted;//삭제여부
	
	@Persistent
	private Text contents;//본문
	
	
	
	
	
	
	 
	 
	 
	 
	

 

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


	


	public Boolean getIs_deleted() {
		return is_deleted;
	}


	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}


	public Text getContents() {
		return contents;
	}


	public void setContents(Text contents) {
		this.contents = contents;
	}


	public GuestBookAnswerTable() {}

  
	 public static GuestBookAnswerTable findGuestBookAnswerTable(Long id) {
		 //System.out.println("3333333333333333333333333");   

		     PersistenceManager pm = persistenceManager();
		   //  System.out.println("44444444444444444444");  
		     try {
		     	 
		    	 GuestBookAnswerTable guestAnswerBook = pm.getObjectById(GuestBookAnswerTable.class, id);
		     //	System.out.println("5555555555555555");  
		         return guestAnswerBook;
		      
		      
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
    	  reg_dt = new Date();
      }else{//update
    	  modi_dt = new Date();
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
   * Remove this object from the data store.
   */
  public void remove() {
	  PersistenceManager pm = persistenceManager();
    try {
      GuestBookAnswerTable guestAnswerBook = pm.getObjectById(GuestBookAnswerTable.class, id);

      // Verify the current user owns the task before removing it.
      if (UserServiceWrapper.get().getCurrentUser().getEmail().equals(guestAnswerBook.getEmail())) {
    	  pm.deletePersistent(guestAnswerBook);
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

  
 

	
  
	
	
	
	
	
	
	
	
	
	    
  
}
