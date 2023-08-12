package com.visenti.test.automation.helpers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.visenti.test.automation.api.modules.healthmonitor.DataLoadHealthAPI;
import com.visenti.test.automation.api.modules.plotter.dma.DataLoadDmaSensors;
import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.hooks.ServiceHooks;
import com.visenti.test.automation.utils.CommonUtils;

import cucumber.api.Scenario;
import jakarta.activation.DataHandler;
import jakarta.activation.*;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import static com.visenti.test.automation.constants.APIConstants.*;

public class EmailSender {
	static Properties emailProperties;
	// public static String TRANSPORT_STRATEGY = "SSL";
	Session session;
	MimeMessage emailMessage;

	public String[] getReceiversEmailList(String emailListInString) {
		String[] emailList = emailListInString.split(",");
		return emailList;
	}

	/*
	 * public void setMailServerProperties() {
	 * 
	 * emailProperties = System.getProperties();
	 * emailProperties.put("mail.smtp.port",
	 * FileReaderManager.getInstance().getConfigReader().getEmailPort());
	 * emailProperties.put("mail.smtp.auth", "true");
	 * emailProperties.put("mail.smtp.starttls.enable", "true");
	 * 
	 * }
	 */

	public void createErrorEmailMessage(String testResult, String poratl, String[] sendingEmailList, String error)
			throws MessagingException {
		// String errorStackTrace =
		// ExceptionUtils.getStackTrace(error.fillInStackTrace());
		String emailSubject = "Automation Test Run - " + testResult;
		String emailBody = "Test Run " + testResult + " in " + poratl + System.lineSeparator() + System.lineSeparator()
				+ "API : " + TestRailTestManagement.getAPI() + System.lineSeparator() + System.lineSeparator()
				+ "Action : " + TestRailTestManagement.getAction() + System.lineSeparator() + System.lineSeparator()
				+ error + System.lineSeparator() + System.lineSeparator()
				+ "This is an automated email sent by Visenti Automation Framework.";

		session = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(session);

		for (int i = 0; i < sendingEmailList.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(sendingEmailList[i]));
		}
		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/plain");
	}

	public void createErrorEmailMessageForScenario(String testResult, String portal, String[] sendingEmailList,
			String error, String info) throws MessagingException {
		// String errorStackTrace =
		// ExceptionUtils.getStackTrace(error.fillInStackTrace());
		String emailSubject = "Automation Test Run - " + testResult;
		String emailBody = "Test Run " + testResult + " in " + portal + System.lineSeparator() + System.lineSeparator()
				+ "API : " + TestRailTestManagement.getAPI() + System.lineSeparator() + System.lineSeparator()
				+ "Action : " + TestRailTestManagement.getAction() + System.lineSeparator() + System.lineSeparator()
				+ error + System.lineSeparator() + System.lineSeparator() + info + System.lineSeparator()
				+ System.lineSeparator();

		session = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(session);

		for (int i = 0; i < sendingEmailList.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(sendingEmailList[i]));
		}
		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/plain");
	}

	/*
	 * public void sendEmail() throws MessagingException { Transport transport =
	 * session.getTransport("smtp");
	 * transport.connect(FileReaderManager.getInstance().getConfigReader().
	 * getEmailHost(),
	 * FileReaderManager.getInstance().getConfigReader().getEmailSender(),
	 * FileReaderManager.getInstance().getConfigReader().getEmailSenderPW());
	 * transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
	 * transport.close(); System.out.println("Email sent successfully."); }
	 */

	public static String[] getRecipientsEmailAddress(String key) {
		String emailList = ConfigFileReader.getConfigProperty(key);
		String[] recipientsAddress = emailList.split(",");
		return recipientsAddress;

	}

	public static void setMailServerPropertiesTLS() {
		emailProperties.put("mail.smtp.port", ConfigFileReader.getConfigProperty("mail.smtp.port.tls"));
		emailProperties.put("mail.smtp.starttls.enable", "true");

	}

	public static void setMailServerPropertiesSSL() {
		emailProperties.put("mail.smtp.port", ConfigFileReader.getConfigProperty("mail.smtp.port.ssl"));
		emailProperties.put("mail.smtp.ssl.enable", "true");
	}

	public static void attachZipFileAndSendMail() throws IOException {

		emailProperties = System.getProperties();
		String host = ConfigFileReader.getConfigProperty("mail.smtp.host");
		emailProperties.put("mail.smtp.host", host);
		String transportStrategy = ConfigFileReader.getConfigProperty("mail.smtp.transport.strategy");


		if (transportStrategy.equalsIgnoreCase("ssl")) {
			setMailServerPropertiesSSL();
		} else if (transportStrategy.equalsIgnoreCase("tls")) {
			setMailServerPropertiesTLS();
		}
		//Set the parameter to true in case password is being used
		emailProperties.put("mail.smtp.auth", "false");
		//String password = ConfigFileReader.getConfigProperty("mail.sender.password");
		String[] recipientsAddressList = getRecipientsEmailAddress("mail.recipients.address");

		String username = ConfigFileReader.getConfigProperty("mail.sender.username");
		Session session = Session.getInstance(emailProperties, null
		//This can be used in case a password is being used
		/*new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		}*/
		);

		// Composing message

		try {
			List<Scenario> failedScenarioList = ServiceHooks.failedScenarioList;
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));

			if(failedScenarioList.size()==1){
				String[] hmRecipientsAddressList = getRecipientsEmailAddress("mail.hm.recipients.address");
				for (int i = 0; i < hmRecipientsAddressList.length; i++) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(hmRecipientsAddressList[i]));
				}
			} else {
				// Adding multiple Recipients to the Message
				for (int i = 0; i < recipientsAddressList.length; i++) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientsAddressList[i]));
				}
			}



			for (Scenario failedScenario : failedScenarioList) {
				if (failedScenario.getSourceTagNames().contains("@report")) {
					// Setting the mail Subject
					message.setSubject("Report Details : "
							+ ReportingHelper.getExtentReportsDateFolder());

					// 3) create MimeBodyPart object and set your message text
					BodyPart messageBodyPart1 = new MimeBodyPart();

					messageBodyPart1.setContent(generateMessageBodyHTMLCodeForDailyReport(), FrameworkConstants.TEXT_HTML_MIME_TYPE);
					// 4) create new MimeBodyPart object and set DataHandler object to this object

					// Adding the zip file to the MimeBodyPart
					MimeBodyPart messageBodyPart2 = new MimeBodyPart();

					String filePath = ReportingHelper.createZippedFileOfTheGeneratedReportsFolderAndReturnPath();
					DataSource source = new FileDataSource(filePath);
					messageBodyPart2.setDataHandler(new DataHandler(source));
					// Setting the fileName in the attached Mail
					messageBodyPart2.setFileName(
							ReportingHelper.getExtentReportsDateFolder() + "." + FrameworkConstants.ZIPPED_FILE_FORMAT);

					// 5) create Multipart object and add MimeBodyPart objects to this object
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					multipart.addBodyPart(messageBodyPart2);

					// 6) set the multipart object to the message object
					message.setContent(multipart);
					break;
				} else {
					// Setting the mail Subject
					message.setSubject("Automation Execution Report :" + ReportingHelper.getExtentReportsDateFolder());

					// 3) create MimeBodyPart object and set your message text
					BodyPart messageBodyPart1 = new MimeBodyPart();

					messageBodyPart1.setContent(generateMessageBodyHTMLCode(), FrameworkConstants.TEXT_HTML_MIME_TYPE);
					// 4) create new MimeBodyPart object and set DataHandler object to this object

					// Adding the zip file to the MimeBodyPart
					MimeBodyPart messageBodyPart2 = new MimeBodyPart();

					String filePath = ReportingHelper.createZippedFileOfTheGeneratedReportsFolderAndReturnPath();
					DataSource source = new FileDataSource(filePath);
					messageBodyPart2.setDataHandler(new DataHandler(source));
					// Setting the fileName in the attached Mail
					messageBodyPart2.setFileName(
							ReportingHelper.getExtentReportsDateFolder() + "." + FrameworkConstants.ZIPPED_FILE_FORMAT);

					// 5) create Multipart object and add MimeBodyPart objects to this object
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					multipart.addBodyPart(messageBodyPart2);

					// 6) set the multipart object to the message object
					message.setContent(multipart);
				}
			}

			// 7) send message
			Transport.send(message);
			Log.info("Email sent successfully");

			// System.out.println("message sent....");
		} catch (MessagingException ex) {
			Log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void setMimeMessageBody(MimeMessage message) throws Exception {
		MimeBodyPart messageBodyPart1 = new MimeBodyPart();

		messageBodyPart1.setContent(generateMessageBodyHTMLCodeForDailyReport(), FrameworkConstants.TEXT_HTML_MIME_TYPE);
		// 4) create new MimeBodyPart object and set DataHandler object to this object

		// Adding the zip file to the MimeBodyPart
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();

		String filePath = ReportingHelper.createZippedFileOfTheGeneratedReportsFolderAndReturnPath();
		DataSource source = new FileDataSource(filePath);
		messageBodyPart2.setDataHandler(new DataHandler(source));
		// Setting the fileName in the attached Mail
		messageBodyPart2.setFileName(
				ReportingHelper.getExtentReportsDateFolder() + "." + FrameworkConstants.ZIPPED_FILE_FORMAT);

		// 5) create Multipart object and add MimeBodyPart objects to this object
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart1);
		multipart.addBodyPart(messageBodyPart2);

		// 6) set the multipart object to the message object
		message.setContent(multipart);
	}

	/**
	 * @return
	 * @throws IOException This method generates the HTML code to be embedded in
	 *                     Email message body
	 */
	public static String generateMessageBodyHTMLCode() throws IOException {
		StringBuffer buffer = new StringBuffer();
		List<Scenario> failedScenarioList = ServiceHooks.failedScenarioList;
		int totalCount = ServiceHooks.totalCount;
		int failCount=0;
		
		for (Scenario failedScenario : failedScenarioList) {
			failCount++;
			Collection<String> scenarioTags = failedScenario.getSourceTagNames();
			String module = "N.A.";
			for (String scenarioTag : scenarioTags) {
				if (scenarioTag.toLowerCase().contains("module")) {
										
					module=CommonUtils.replaceAllOccurencesOfAWordWithEmptyStringRegardlessOfCase(scenarioTag, "module");
					//Replacing all special characters with empty space
					module=module.replaceAll("[-:*_|@]","").toUpperCase();
				}
			}
			/*if(failedScenario.getStatus().toLowerCase().contains("fail"))
			{*/
			buffer.append("<tr><td>").append(failCount).append("</td><td>").append(module).append("</td><td>")
					.append(failedScenario.getName()).append("</td><td>");
			Collection<String> tagNames = failedScenario.getSourceTagNames();
			if (tagNames.contains("@web") || tagNames.contains("@Web")) {
				buffer.append("WEB").append("</td><td");
			} else if (tagNames.contains("@api") || tagNames.contains("@Api")) {
				buffer.append("API").append("</td><td");
			}

			/*if (scenario.getStatus().toLowerCase().contains("pass")) {
				buffer.append(" style=\"background-color:green;\">").append("Pass").append("</td><tr>");
				passCount++;
			}*/ /*if (scenario.getStatus().toLowerCase().contains("fail")) {*/
				buffer.append(" style=\"background-color:red;\">").append("Fail").append("</td><tr>");
				//failCount++;

			//} /*else {
				//buffer.append(" style=\"background-color:yellow;\">").append("Skip").append("</td><tr>");

			}

		
		int passCount=totalCount-failCount;
		Log.info("Pass count " + passCount);
		Log.info("Fail count" + failCount);
		Log.info("Total count " + totalCount);

		String htmlTemplate = CommonUtils
				.generateStringFromAFile(FrameworkConstants.RESOURCES_FOLDER + "HtmlEmail_Template.txt");
		String messageBodyHtml = htmlTemplate.replace("{tableBody}", buffer.toString())
				.replace("{countTotal}", String.valueOf(totalCount)).replace("{countPass}", String.valueOf(passCount))
				.replace("{countFail}", String.valueOf(failCount))
				.replace("{userName}", System.getProperty("user.name"))
				.replace("{executionEnvironment}", RuntimeConfigSingleton.getInstance().getExecutionEnvironment())
				.replace("{customerName}", RuntimeConfigSingleton.getInstance().getCustomerName());
		Log.info(messageBodyHtml);
		return messageBodyHtml;

	}

	/**
	 * @return
	 * @throws IOException This method generates the HTML code to be embedded in
	 *                     Email message body for data status and AIMS
	 */
	public static String generateMessageBodyHTMLCodeForDailyReport() throws IOException {
		StringBuffer buffer;
		buffer = getReportTables();

		String htmlTemplate = CommonUtils
				.generateStringFromAFile(FrameworkConstants.RESOURCES_FOLDER + "DailyReportEmail_Template.txt");
		String messageBodyHtml= htmlTemplate
				.replace("{tableBody}", buffer.toString())
				.replace("{executionEnvironment}", RuntimeConfigSingleton.getInstance().getExecutionEnvironment().toUpperCase())
				.replace("{customerName}", RuntimeConfigSingleton.getInstance().getCustomerName().toUpperCase());

		Log.info(messageBodyHtml);
		return messageBodyHtml;
	}

	/**
	 * @return
	 * @throws IOException This method generates the HTML code of table to be embedded in
	 *                     Email message body for data status
	 */
	public static StringBuffer getReportTables(){
		StringBuffer buffer = new StringBuffer();
		List<Map> dataStatusFailedList = DataLoadHealthAPI.dataStatusFailedList;
		if(!dataStatusFailedList.isEmpty()){
			int failCount=0;
			buffer.append("<p>Below is the sensor's online percentage for the vendors less than the threshold value:</p>")
					.append("<table>")
					.append("<caption>").append("Data Availability Summary")
					.append("</caption>")
					.append("<tr><th>").append("S.No")
					.append("</th><th>").append("Vendor")
					.append("</th><th>").append("Sensor Type")
					.append("</th><th>").append("Online Sensor Percentage")
					.append("</th><th>").append("Threshold")
					.append("</th><th>").append("Last Updated")
					.append("</th></tr>");
			for (Map map : dataStatusFailedList) {
				Map dataStatus = dataStatusFailedList.get(failCount);
				String vendor = dataStatus.get(DATA_STATUS_SOURCE_KEY).toString();
				String sensor = dataStatus.get(DATA_STATUS_SENSOR_TYPE_KEY).toString();
				String count  = "(" + dataStatus.get(DATA_STATUS_ONLINE_STATIONS_COUNT_KEY).toString() + "/" +
						dataStatus.get(DATA_STATUS_TOTAL_STATIONS_COUNT_KEY).toString() + ") " +
						dataStatus.get(DATA_STATUS_ONLINE_PERCENTAGE_KEY).toString() + "%";
				String thresholdValue = RuntimeConfigSingleton.getInstance().getCustomerName()+"_"+vendor+"_"+sensor;
				thresholdValue = ConfigFileReader.getDataStatusConfigProperty(thresholdValue.replace("/","_").toUpperCase());
				String lastUpdated = CommonUtils.convertTimeInMillisecondsToUTCDateFormat(
						Long.parseLong(dataStatus.get(DATA_STATUS_LAST_MODIFIED_KEY).toString()), "dd/MM/YYYY HH:mm:ss");
				failCount++;
				buffer.append("<tr><td>").append(failCount)
						.append("</td><td>").append(vendor)
						.append("</td><td>").append(sensor)
						.append("</td><td>").append(count)
						.append("</td><td>").append(thresholdValue)
						.append("</td><td>").append(lastUpdated)
						.append("</td></tr>");
			}
			buffer.append("</table>");
			buffer = getAimsReportTable(buffer);
		}
		else{
			buffer = getAimsReportTable(buffer);
			//buffer.append("There are no Vendor's data less than the threshold");
		}
		return buffer;
	}

	/**
	 * @return
	 * @throws IOException This method generates the HTML code of table to be embedded in
	 *                     Email message body for aims incidents
	 */
	public static StringBuffer getAimsReportTable(StringBuffer buffer){
		List<Scenario> failedScenarioList = ServiceHooks.failedScenarioList;
		int aimsFailCount=0;
		for(int i=0; i<failedScenarioList.size();i++){
			if(failedScenarioList.get(i).getSourceTagNames().contains("@module-aims")) {
				buffer.append("<p>Below is a brief of aims incidents summary for different incident type:</p>")
						.append("<table>")
						.append("<caption>").append("Aims Incidents Summary")
						.append("</caption>")
						.append("<tr><th>").append("S.No")
						.append("</th><th>").append("Module")
						.append("</th><th>").append("Scenario")
						.append("</th><th>").append("Status")
						.append("</th></tr>");

				for (Scenario failedScenario : failedScenarioList) {
					if(failedScenario.getSourceTagNames().contains("@module-aims")) {
						aimsFailCount++;
						buffer.append("<tr><td>").append(aimsFailCount)
								.append("</td><td>").append("AIMS")
								.append("</td><td>").append(failedScenario.getName())
								.append("</td><td>").append("Fail")
								.append("</td><tr>");
					}
				}
				buffer.append("</table>");
				buffer = getDmaDataReportTable(buffer);
				break;
			} else {
				buffer = getDmaDataReportTable(buffer);
				break;
			}
		}
		return buffer;
	}

	public static StringBuffer getDmaDataReportTable(StringBuffer buffer){
		List<Scenario> failedScenarioList = ServiceHooks.failedScenarioList;
		List<String> dmaDataList = DataLoadDmaSensors.dmaDataFailedList;
		int dataFailCount = 0;
		for(int i=0; i<failedScenarioList.size();i++){
			if(failedScenarioList.get(i).getSourceTagNames().contains("@dma-data")){
				buffer.append("<p>Below is a brief of different DMA sensor data summary for all DMA's:</p>")
						.append("<table>")
						.append("<caption>").append("DMA sensor data Summary")
						.append("</caption>")
						.append("<tr><th>").append("S.No")
						.append("</th><th>").append("Module")
						.append("</th><th>").append("Scenario")
						.append("</th><th>").append("DMA")
						.append("</th></tr>");

				for (Scenario failedScenario : failedScenarioList) {
					Collection<String> scenarioTags = failedScenario.getSourceTagNames();
					if(scenarioTags.contains("@dma-data")){
						for(String value : dmaDataList) {
							dataFailCount++;
							buffer.append("<tr><td>").append(dataFailCount)
									.append("</td><td>").append("PLOTTER")
									.append("</td><td>").append(failedScenario.getName())
									.append("</td><td>").append(value)
									.append("</td><tr>");
						}
					}
				}
				buffer.append("</table>");
				break;
			}
		}

		return buffer;
	}
}
