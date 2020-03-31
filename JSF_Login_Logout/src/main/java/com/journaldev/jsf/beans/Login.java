package com.journaldev.jsf.beans;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.journaldev.jsf.dao.LoginDAO;
import com.journaldev.jsf.util.SessionUtils;

@ManagedBean
@SessionScoped
public class Login implements Serializable {
private static final long serialVersionUID = 1094801825228386363L;
	




	private String url;
	private int timex;
	private String starttime;
	private String stoptime;
	public String getStarttime() {
		return starttime;
	}

	
	


	public String getStoptime() {
		return stoptime;
	}

	

	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}

	

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
private String msg;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public int getTimex() {
		return timex;
	}

	public void setTimex(int timex) {
		this.timex = timex;
	}

	public String validateUsernamePassword(String action) {
		System.out.println("Actionnn :: " + action);
		 Vector vectorvalues = LoginDAO.validate(url, timex,action);
		 starttime ="Timestamp from: "+(String) vectorvalues.get(0)+" to: "+(String) vectorvalues.get(2);
		 stoptime = "Average Response time: "+(String) vectorvalues.get(3)+" of sample: "+(String) vectorvalues.get(1);
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("URL", url);
			session.setAttribute("starttime", starttime);
			session.setAttribute("stoptime", stoptime);

			return "admin";
		
	}

	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}
	
	
	
	
}


