package com.chetaru.tribe365_new.API.retrofit;

public class ConstantAPI {

    /******************************Login Api ***************************/
    public static final String api_userLogin="userLogin";
    public static final String api_userSetPassword ="userSetPassword";
    public static final String api_forgotPassword="forgotPassword";

    /*********************HPTM APi List *****************************/
    public static final String getLearningCheckList="getLearningCheckList";
    public static final String getPrinciplesList="getPrinciplesList";
    public static final String changeReadStatusOfUserChecklist="changeReadStatusOfUserChecklist";
    public static final String sendTeamFeedbackToSelectedUsers="sendTeamFeedbackToSelectedUsers";
    public static final String getIndividualQuestionnaireList ="getIndividualQuestionnaireList";
    public static final String addIndividualQuestionnaireAnswers="addIndividualQuestionnaireAnswers";
    public static final String teamFeedbackUserAnswerStatus="teamFeedbackUserAnswerStatus";
    public static final String teamFeedbackUserStatus="teamFeedbackUserStatus";
    /*************************************************************************/


    /**************************** Reflaction APi *****************************/
    public static final String postHPTMReflection ="postHPTMReflection";
    public static final String getHptmReflectionDetail="getHptmReflectionDetail";
    public static final String getReflectionChatMessages="getReflectionChatMessages";
    public static final String sendReflectionChatMessage="sendReflectionChatMessage";
    /****************************************************************************/

    /************************ Home Api *****************************/
    public static final String api_addHappyIndex="addHappyIndex";
    public static final String api_getCurrentVersionOfApp="getCurrentVersionOfApp";
    public static final String api_dotDetail="dotDetail";
    public static final String getHomePageDetails ="getHomePageDetails";
    public static final String getDepartmentuserList="getDepartmentUserList";
    public static final String addDOTBubbleRatingsToMultiDepartment ="addDOTBubbleRatingsToMultiDepartment";
    public static final String addHappyIndex ="addHappyIndex";
    public static final String getCurrentVersionOfApp="getCurrentVersionOfApp";
    public static final String api_getBubbleUnReadNotifications="getBubbleUnReadNotifications";
    public static final String getHomePageKudosCount="getHomePageKudosCount";
    public static final String addKudosAward="addKudosAward";
    public static final String addDotValueKudosAward="addDotValueKudosAward";
    public static final String kudoAwardDetail="kudoAwardDetail";
    public static final String dotValuekudoAwardDetail="dotValuekudoAwardDetail";
    /************************************************************************/

    /****************************************** Absent Module *************************/
    //0: present, 1: absent
    public static final String api_userApplyLeave = "userApplyLeave";
    public static final String api_userChangeLeaveStatus = "userChangeLeaveStatus";
    /****************************** HomePage Basic Version ******************/
    public static final String api_getBasicVersionHomeDetails="getBasicVersionHomeDetails";
    /************************************************************************/

    /*************************** DOT Api ************************************/

    public static final String api_addbubbleRatings="addbubbleRatings";
    public static final String api_getBubbleRatingList="getBubbleRatingList";
    public static final String api_getBubbleFromUserRating="getBubbleFromUserRating";
              //not user in app
    public static final String api_getDOTValuesList="getDOTValuesList";
    /************************************************************************/

    /***************************** Action APi *******************************/
    public static final String api_add_rating="ratingsToDotValues";
    public static final String api_actionTierList="actionTierList";
    public static final String getActionDetail="getActionDetail";

    /************************ Know Org ************************************/
    public static final String getAllOfficenDepartments="getAllOfficenDepartments";
    public static final String getDepartmentList="getDepartmentList";
    public static final String getKnowOrganisationDetails ="getKnowOrganisationDetails";
    //for report section
    public static final String getIndividualReport ="getIndividualReport";


    public static final String api_getUserDashboardReport="getUserDashboardReport";
      public static final String api_getHappyIndexMonthCount="getHappyIndexMonthCount";
      public static final String api_getHappyIndexWeeksCount="getHappyIndexWeeksCount";
      public static final String api_getHappyIndexDaysCount="getHappyIndexDaysCount";
    public static String getKudosAward="getKudosAward";
    /****************************************************************************/

    /*************************************** Know Member ************************/
    public static final String getUserByType="getUserByType";
    public static final String getKnowMemberDetails ="getKnowMemberDetails";
    /************************************************************************/

    /***************************** Offloading *******************************/
    public static final String getFeedbackDetail ="getFeedbackDetail";
    public static final String postFeedback="postFeedback";
    public static final String getChatMessages="getChatMessages";
    public static final String iotSendMsg="iotSendMsg";
    public static final String getInboxChatList="getInboxChatList";
    /*********************************************************************/

    /********************************* Notification ********************/
    public static final String getBubbleRatingUnReadNotificationList="getBubbleRatingUnReadNotificationList";
    public static final String getBubbleRatingNotificationList="getBubbleRatingNotificationList";
    public static final String changeNotificationStatus="changeNotificationStatus";
    public static final String readAllNotification="readAllNotification";
    /**********************************************************************/
    /***************************** support **********************************/
    public static final String getSupportHistory="getSupportHistory";
    public static final String addCustomerSupport="addCustomerSupport";
    public static final String getUserChatMessages="getUserChatMessages";
    public static final String sendChatMessage="sendChatMessage";
    /*******************************************************************/

    /********************************* UserProfile **********************/
    public static final String userProfile="userProfile";
    public static final String userInteractionOnApp="userInteractionOnApp";
    public static final String userLogout="userLogout";
    public static final String updateUserProfile="updateUserProfile";
    public static final String updatePasswordWithCurrentPassword="updatePasswordWithCurrentPassword";
    /*******************************************************************************************/


    /********************************* Action **********************************/
    public static final String getActionList="getActionList";
    public static final String deleteAction="deleteAction";
    public static final String updateStatus="updateStatus";
    public static final String getThemeList="getThemeList";
    public static final String updateAction="updateAction";
    public static final String addAction ="addAction";
    public static final String addComment="addComment";
    public static final String listComment="listComment";
    /*********************************************************************************/

    /************************************ Org Studies ******************************/
    public static final String getOrgDashboardReportWithFilter="getOrgDashboardReportWithFilter";
    /*********************************************************************************/

    public static final String freeVersionHomeDetails ="getFreeVersionHomeDetails";


    /*******************************  Questions and Update Api *************************/
    public static final String isCOTanswerDone="isCOTanswerDone";
    public static final String addCOTAnswer="addCOTAnswer"; //team Role Answers
    public static final String api_addCOTfunctionalLensAnswer="addCOTfunctionalLensAnswer"; //personality type Answers
    public static final String api_sot_addSOTanswers="addSOTanswers"; //culture structure Answers
    public static final String api_sot_addSOTQusAns="getSOTquestionAnswers"; //get supper chargaring questions
    public static final String api_sot_updateSOTquestionAnswer="updateSOTquestionAnswer"; // update supper chaeging questions
    public static final String api_addSOTmotivationAnswer="addSOTmotivationAnswer"; //motivatinal Answers
         /*                             Questions Api                 */
    public static final String api_sot_quest="getSotQuestionList"; //culture Structure Questions
    public static final String api_getSOTmotivationUserList="getSOTmotivationUserList";
    public static final String api_getSOTmotivationQuestions="getSOTmotivationQuestions"; // Motivational Questions
    public static final String api_fubction_qusList="getCOTfunctionalLensQuestionsList"; //personality type
    public static final String api_getCOTMapperSummary="getCOTMapperSummary"; //desctiption
    public static final String api_functionallensdetail="getCOTFunctionalLensDetail"; //personality type result
    public static final String getCOTindividualSummary="getCOTindividualSummary";
    public static final String api_get_COTQuestions="getCOTQuestions"; //Team Role Questions
    public static final String api_getCOTfuncLensCompletedAnswers="getCOTfuncLensCompletedAnswers"; //personality type all answers
    public static final String api_getSOTmotivationCompletedAnswer="getSOTmotivationCompletedAnswer"; //Motivational all Answers
            /*                            update Api                  */
    public static final String api_updateCOTteamRoleMapAnswers="updateCOTteamRoleMapAnswers"; //Team role update answers
    public static final String api_updateCOTfunLensAnswers="updateCOTfunLensAnswers";
    public static final String api_updateSOTmotivationAnswers="updateSOTmotivationAnswers"; // motivations Update answers
           /*                         get Graph                      */
    public static final String api_getTribeometerSubGraph="getTribeometerSubGraph";
    public static final String api_getTribeometerMainSubGraph="getTribeometerMainSubGraph";
    /**************************************************************************************/

    /*********************************** Culture Structure ******************************/

    /*************************************************************************************/

    /*************************************

    /*************************************** Personality Type *****************************/

    public static final String api_getPersonalityTypeReport="getPersonalityTypeReport";
    public static final String api_getPersonalityTypeReportSubGraph="getPersonalityTypeReportSubGraph";
    public static final String api_getPersonalityTypeReportUserSubGraph="getPersonalityTypeReportUserSubGraph";
    public static final String api_getPersonalityTypeQuestionList="getPersonalityTypeQuestionList";
    public static final String api_getPersonalityTypeCompletedAnswers="getPersonalityTypeCompletedAnswers";
    public static final String api_updatePersonalityTypeAnswers="updatePersonalityTypeAnswers";
    public static final String api_addPersonalityTypeAnswers="addPersonalityTypeAnswers";
    /***************************************************************************************/

    /*********************************************** Risk Module *****************************/
    public static final String api_riskRegisterList="riskRegisterList";
    public static final String api_riskDetail="riskDetail";
    /**************************************** update Notification push ***********************/
    public static final String api_updatePushNotificationStatus= "updatePushNotificationStatus";



}
