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
package com.google.gwt.homepage.lovelicense.shared;

import com.google.gwt.homepage.lovelicense.server.SajuDataTable;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import java.util.Date;

/**
 * A task used in the task list.
 */
@ProxyFor(SajuDataTable.class)
public interface SajuDataTableProxy extends EntityProxy {

	public String getEmail() ;

	public String getSolar_year();

	public void setSolar_year(String solar_year) ;

	public String getSolar_month() ;

	public void setSolar_month(String solar_month);

	public String getSolar_date();

	public void setSolar_date(String solar_date);

	//시간인덱스 코드를 텍스트로 변환해서 리턴(사주보기,현재사주에서 사용)
	public String getTransTimeValue();
	
	//내정보수정에서 사용
	public String getBirth_time() ;

	
	public void setBirth_time(String birth_time) ;

	public String getLunar_year();

	

	public String getLunar_month();

	

	public String getLunar_date();

	

	

	

	public String getYearSkyVal() ;

	

	public String getYearLandVal() ;

	

	public String getMonthSkyVal() ;

	
	public String getMonthLandVal() ;

	

	public String getDateSkyVal() ;

	

	public String getDateLandVal() ;
	

	public String getYearSkyPM() ;

	

	public String getYearLandPM() ;

	

	public String getMonthSkyPM() ;

	

	public String getMonthLandPM() ;

	

	public String getDateSkyPM() ;

	

	public String getDateLandPM() ;

	

	public String getYearSky5hang() ;

	

	public String getYearLand5hang() ;

	

	public String getMonthSky5hang() ;

	

	public String getMonthLand5hang() ;

	

	public String getDateSky5hang() ;

	

	public String getDateLand5hang() ;

	

	public String getYoyakDesc() ;


	public int getPlusPercent() ;

	

	public int getMinusPercent() ;

	



	public int getMokPlus() ;

	

	public int getWhaPlus();

	

	public int getGeumPlus() ;

	

	public int getSuPlus() ;

	

	public int getJamiMinus() ;

	

	public int getChukohMinus() ;

	

	public int getInyuMinus() ;

	

	public int getMyosinMinus() ;

	

	public int getJinhaeMinus() ;

	
	public int getSasulMinus() ;

	

	//public int[] getOhhangCnt() ;

	
	
	

	public String getTimeSkyVal() ;

	

	public String getTimeLandVal();

	

	public String getTimeSkyPM() ;

	

	public String getTimeLandPM() ;



	public String getTimeSky5hang() ;

	

	public String getTimeLand5hang() ;

	

	//public float[] getOhhangCntAvg() ;
	

	public int getOhhangCnt0();
	public int getOhhangCnt1();
	public int getOhhangCnt2();
	public int getOhhangCnt3();
	public int getOhhangCnt4();
	
	public float getOhhangCntAvg0();
	public float getOhhangCntAvg1();
	public float getOhhangCntAvg2();
	public float getOhhangCntAvg3();
	public float getOhhangCntAvg4();


	public String getSkyHab() ;

	

	public String getSkyChung() ;

	

	public Boolean isNeverMarriage() ;

	

	public Integer getTotalScore() ;

	

	public String getSex() ;

	public void setSex(String sex) ;

	public String getJob() ;

	public void setJob(String job) ;

	public String getAddr1() ;

	public void setAddr1(String addr1) ;
}
