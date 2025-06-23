package com.chetaru.tribe365_new.KotlinCode.APi


const val DEVICE_TYPE : String= "1"
const val ROLE : String= "3"

//const val BASE_URL : String = "https://tribe365demo.chetaru.co.uk/api/" //new  production
const val BASE_URL_API: String = "https://console.tribe365.co/api/";  // live URL


/****************************** Login Api  */
const val api_userLogin: String = "userLogin"
const val api_userSetPassword: String = "userSetPassword"
const val api_forgotPassword: String = "forgotPassword"

/********************* HPTM APi List  */
const val getLearningCheckList = "getLearningCheckList"
const val getPrinciplesList = "getPrinciplesList"
const val changeReadStatusOfUserChecklist = "changeReadStatusOfUserChecklist"
const val sendTeamFeedbackToSelectedUsers = "sendTeamFeedbackToSelectedUsers"
const val getIndividualQuestionnaireList = "getIndividualQuestionnaireList"
const val addIndividualQuestionnaireAnswers = "addIndividualQuestionnaireAnswers"
const val teamFeedbackUserAnswerStatus = "teamFeedbackUserAnswerStatus"
const val teamFeedbackUserStatus = "teamFeedbackUserStatus"
/*************************************************************************/


/**************************** Reflaction APi  */
const val postHPTMReflection = "postHPTMReflection"
const val getHptmReflectionDetail = "getHptmReflectionDetail"
const val getReflectionChatMessages = "getReflectionChatMessages"
const val sendReflectionChatMessage = "sendReflectionChatMessage"
/****************************************************************************/


/************************ Home Api  */
const val api_addHappyIndex = "addHappyIndex"
const val api_getCurrentVersionOfApp = "getCurrentVersionOfApp"
const val api_dotDetail = "dotDetail"
const val getHomePageDetails = "getHomePageDetails"
const val getDepartmentuserList = "getDepartmentUserList"
const val addDOTBubbleRatingsToMultiDepartment = "addDOTBubbleRatingsToMultiDepartment"
const val addHappyIndex = "addHappyIndex"
const val getCurrentVersionOfApp = "getCurrentVersionOfApp"
const val api_getBubbleUnReadNotifications = "getBubbleUnReadNotifications"
const val getHomePageKudosCount = "getHomePageKudosCount"
const val addKudosAward = "addKudosAward"
const val addDotValueKudosAward = "addDotValueKudosAward"
const val kudoAwardDetail = "kudoAwardDetail"
const val dotValuekudoAwardDetail = "dotValuekudoAwardDetail"
/************************************************************************/


/****************************************** Absent Module  */ //0: present, 1: absent
const val api_userApplyLeave = "userApplyLeave"
const val api_userChangeLeaveStatus = "userChangeLeaveStatus"

/****************************** HomePage Basic Version  */
const val api_getBasicVersionHomeDetails = "getBasicVersionHomeDetails"
/************************************************************************/


/*************************** DOT Api  */
const val api_addbubbleRatings = "addbubbleRatings"
const val api_getBubbleRatingList = "getBubbleRatingList"
const val api_getBubbleFromUserRating = "getBubbleFromUserRating"

//not user in app
const val api_getDOTValuesList = "getDOTValuesList"
/************************************************************************/


/***************************** Action APi  */
const val api_add_rating = "ratingsToDotValues"
const val api_actionTierList = "actionTierList"
const val getActionDetail = "getActionDetail"

/************************ Know Org  */
const val getAllOfficenDepartments = "getAllOfficenDepartments"
const val getDepartmentList = "getDepartmentList"
const val getKnowOrganisationDetails = "getKnowOrganisationDetails"

const val api_getUserDashboardReport = "getUserDashboardReport"
const val api_getHappyIndexMonthCount = "getHappyIndexMonthCount"
const val api_getHappyIndexWeeksCount = "getHappyIndexWeeksCount"
const val api_getHappyIndexDaysCount = "getHappyIndexDaysCount"
var getKudosAward = "getKudosAward"
/****************************************************************************/


/*************************************** Know Member  */
const val getUserByType = "getUserByType"
const val getKnowMemberDetails = "getKnowMemberDetails"
/************************************************************************/


/***************************** Offloading  */
const val getFeedbackDetail = "getFeedbackDetail"
const val postFeedback = "postFeedback"
const val getChatMessages = "getChatMessages"
const val iotSendMsg = "iotSendMsg"
const val getInboxChatList = "getInboxChatList"
/*********************************************************************/


/********************************* Notification  */
const val getBubbleRatingUnReadNotificationList = "getBubbleRatingUnReadNotificationList"
const val getBubbleRatingNotificationList = "getBubbleRatingNotificationList"
const val changeNotificationStatus = "changeNotificationStatus"
const val readAllNotification = "readAllNotification"
/**********************************************************************/

/***************************** support  */
const val getSupportHistory = "getSupportHistory"
const val addCustomerSupport = "addCustomerSupport"
const val getUserChatMessages = "getUserChatMessages"
const val sendChatMessage = "sendChatMessage"
/*******************************************************************/


/********************************* UserProfile  */
const val userProfile = "userProfile"
const val userInteractionOnApp = "userInteractionOnApp"
const val userLogout = "userLogout"
const val updateUserProfile = "updateUserProfile"
const val updatePasswordWithCurrentPassword = "updatePasswordWithCurrentPassword"
/*******************************************************************************************/


/********************************* Action  */
const val getActionList = "getActionList"
const val deleteAction = "deleteAction"
const val updateStatus = "updateStatus"
const val getThemeList = "getThemeList"
const val updateAction = "updateAction"
const val addAction = "addAction"
const val addComment = "addComment"
const val listComment = "listComment"
/*********************************************************************************/


/************************************ Org Studies  */
const val getOrgDashboardReportWithFilter = "getOrgDashboardReportWithFilter"

/** Free Version */
const val freeVersionHomeDetails = "getFreeVersionHomeDetails"

/*******************************  Questions and Update Api  */
const val isCOTanswerDone = "isCOTanswerDone"
const val addCOTAnswer = "addCOTAnswer"
const val api_addCOTfunctionalLensAnswer = "addCOTfunctionalLensAnswer"
const val api_sot_addSOTanswers = "addSOTanswers"
const val api_sot_addSOTQusAns = "getSOTquestionAnswers"
const val api_sot_updateSOTquestionAnswer = "updateSOTquestionAnswer"
const val api_addSOTmotivationAnswer = "addSOTmotivationAnswer"

/**                             Questions Api                 */
const val api_sot_quest = "getSotQuestionList"
const val api_getSOTmotivationUserList = "getSOTmotivationUserList"
const val api_getSOTmotivationQuestions = "getSOTmotivationQuestions"
const val api_fubction_qusList = "getCOTfunctionalLensQuestionsList"
const val api_getCOTMapperSummary = "getCOTMapperSummary"
const val api_functionallensdetail = "getCOTFunctionalLensDetail"
const val getCOTindividualSummary = "getCOTindividualSummary"
const val api_get_COTQuestions = "getCOTQuestions"
const val api_getCOTfuncLensCompletedAnswers = "getCOTfuncLensCompletedAnswers"
const val api_getSOTmotivationCompletedAnswer = "getSOTmotivationCompletedAnswer"

/**                            update Api                  */
const val api_updateCOTteamRoleMapAnswers = "updateCOTteamRoleMapAnswers"
const val api_updateCOTfunLensAnswers = "updateCOTfunLensAnswers"
const val api_updateSOTmotivationAnswers = "updateSOTmotivationAnswers"

/**                      get Graph                      */
const val api_getTribeometerSubGraph = "getTribeometerSubGraph"
const val api_getTribeometerMainSubGraph = "getTribeometerMainSubGraph"
/**************************************************************************************/


/*************************************** Personality Type  */
const val api_getPersonalityTypeReport = "getPersonalityTypeReport"
const val api_getPersonalityTypeReportSubGraph = "getPersonalityTypeReportSubGraph"
const val api_getPersonalityTypeReportUserSubGraph = "getPersonalityTypeReportUserSubGraph"
const val api_getPersonalityTypeQuestionList = "getPersonalityTypeQuestionList"
const val api_getPersonalityTypeCompletedAnswers = "getPersonalityTypeCompletedAnswers"
const val api_updatePersonalityTypeAnswers = "updatePersonalityTypeAnswers"
const val api_addPersonalityTypeAnswers = "addPersonalityTypeAnswers"
/***************************************************************************************/


/*********************************************** Risk Module  */
const val api_riskRegisterList = "riskRegisterList"
const val api_riskDetail = "riskDetail"

/**************************************** update Notification push  */
const val api_updatePushNotificationStatus = "updatePushNotificationStatus"
