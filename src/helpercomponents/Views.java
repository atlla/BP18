package helpercomponents;

import organizationalunits.BoardType;
import organizationalunits.CommiteeType;
import organizationalunits.OrganizationalUnitType;
import organizationalunits.PositionType;

public abstract class Views {

	public static final String ACTIONVARIABLETYPE = "Views/DptTabActionVariable.fxml";
	public static final String ANALYSISNEEDTYPE = "Views/DptTabAnalysisNeedType.fxml";
	public static final String ANALYSISNEEDSATISFACTIONRELATIONTYPE = "Views/DptTabAnalysisNeedSatisfactionRelationType.fxml";
	public static final String BOARDTYPE = "Views/DptTabBoardType.fxml";
	public static final String CHOOSEDPTTOEDIT = "Views/ChooseDptToEdit.fxml";
	public static final String COMMITTEETYPE = "Views/DptTabCommitteeType.fxml";
	public static final String DECISIONGOALTYPE = "Views/DptTabDecisionGoalType.fxml";	
	public static final String ENGAGEMENTGOALTYPE = "Views/DptTabEngagementGoalType.fxml";	
	public static final String ENVIRONMENTALFACTORTYPE = "Views/DptTabEnvironmentalFactorType.fxml";	
	public static final String EXISTINGINFORMATIONVIEW = "Views/ExistingInformation.fxml";
	public static final String INFORMATIONSYSTEMTYPE = "Views/InformationSystemType.fxml";
	public static final String MSDECPROCTYPETAB = "Views/MS_dptTab.fxml";
	public static final String MSDECPROCINSTTAB = "Views/MS_dpiTab.fxml";
	public static final String MAINSCREEN = "Views/MainScreen.fxml";
	public static final String NEWINFORMATIONTYPE = "Views/NewInformationType.fxml";
	public static final String PARTICIPATIONRELATIONTYPE = "Views/DptTabParticipationrelationType.fxml";
	public static final String PRESUMEDINFLUENCERELATIONTYPE = "Views/DptTabPresInfluRelationType.fxml";	
	public static final String POSITIONTYPE = "Views/DptTabPositionType.fxml";
	public static final String RELEVANCERELATIONTYPE = "Views/DptTabRelevanceRelationType.fxml";
	public static final String RRJUSTIFICATION = "Views/DptTabRRJustification.fxml";
	public static final String ROLETYPE = "Views/DptTabRoleType.fxml";
	public static final String STIMULUSTYPE = "Views/DptTabStimulusType.fxml";
	public static final String STIMULUSINSTANCE = "Views/DpiTabStimulusInstance.fxml";
	public static final String SYMBOLICGOALTYPE = "Views/DptTabSymbolicGoalType.fxml";
	
	public static String getOrgStructTypeView(OrganizationalUnitType org) {
		
		if (org instanceof BoardType) {
			
			return BOARDTYPE;
		
		} else if (org instanceof CommiteeType) {
			
			return COMMITTEETYPE;
		
		} else if (org instanceof PositionType) {
			
			return POSITIONTYPE;
			
		} else {
			
			return ROLETYPE;
		}
	}
}
