package com.wol.ids.console.web;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wol.ids.console.model.Message;
import com.wol.ids.console.model.User;
import com.wol.ids.console.service.MessageService;

@Controller
public class DashboardController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	private MessageService messageService;
	
	public class DashboardModel {
		private User activeUser;
		private int newMsgCount = 0;
		private Set<Message> msgs;
		
		public DashboardModel() {}
		
		public User getActiveUser() {
			return activeUser;
		}
		
		public void setActiveUser(User activeUser) {
			this.activeUser = activeUser;
		}
		
		public Set<Message> getMsgs() {
			return msgs;
		}
		
		public void setMsgs(Set<Message> msgs) {
			this.msgs = msgs;
		}

		public int getNewMsgCount() {
			return newMsgCount;
		}

		public void setNewMsgCount(int newMsgCount) {
			this.newMsgCount = newMsgCount;
		}
		
	}
	
	@GetMapping("/dashboard")
	public ModelAndView getDashboard(ModelMap modelMap, HttpSession httpSession) {
		LOGGER.debug("Received getDashboardRequest");
		User activeUser = (User) httpSession.getAttribute("activeUser");
		
//		if(activeUser == null || !User.State.LOGGED_IN.equals(activeUser.getState())) {
//			LOGGER.warn("User is not currently logged in, redirecting to login.");
//			return new ModelAndView("redirect:/login");
//		}
		
		DashboardModel model = getDashboardModel(modelMap, activeUser);
		ModelAndView mav = new ModelAndView("pages/dashboard");
		mav.addObject("dashboard", model);
		mav.addObject("activeUser", activeUser);
		return mav;
	}
	
	private DashboardModel getDashboardModel(ModelMap modelMap, User activeUser) {
		if(modelMap.containsKey("dashboardModel")) {
			return (DashboardModel) modelMap.get("dashboardModel");
		}

		DashboardModel dashboardModel = new DashboardModel();
		if(activeUser == null) {
			return dashboardModel;
		}
		
		dashboardModel.setActiveUser(activeUser);
		Set<Message> unreadMsgs = messageService.filterUnreadByRecipientEmail(activeUser.getEmail());
		dashboardModel.setMsgs(unreadMsgs);
		dashboardModel.setNewMsgCount(unreadMsgs.size());
		
		return dashboardModel;
	}
}
