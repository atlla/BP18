package controllerandstagefactory;

import javax.persistence.EntityManager;

import controllertypelevel.ActionVariableTypeController;
import controllertypelevel.AnalysisNeedSatisfactionRelController;
import controllertypelevel.AnalysisNeedTypeController;
import controllertypelevel.BoardTypeController;
import controllertypelevel.ChooseDptEditController;
import controllertypelevel.CommitteeTypeController;
import controllertypelevel.DecisionGoalTypeController;
import controllertypelevel.DpiTabController;
import controllertypelevel.IDecisionPremiseTypeController;
import controllertypelevel.IDpiTabController;
import controllertypelevel.DptTabController;
import controllertypelevel.EngagementGoalTypeController;
import controllertypelevel.EnvironmentalFactorTypeController;
import controllertypelevel.IControllerTypeLevel;
import controllertypelevel.IDptTabController;
import controllertypelevel.IOrgStructTypeController;
import controllertypelevel.ISuppReqTypeController;
import controllertypelevel.InformationSystemTypeController;
import controllertypelevel.JustificationRelevRelTypeController;
import controllertypelevel.NewInformationTypeController;
import controllertypelevel.ParticipationRelationTypeController;
import controllertypelevel.PositionTypeController;
import controllertypelevel.PresumedInfluenceRelController;
import controllertypelevel.RelevanceRelationTypeController;
import controllertypelevel.RoleTypeController;
import controllertypelevel.StimulusTypeController;
import controllertypelevel.SuppReqHelperController;
import controllertypelevel.SymbolicGoalTypeController;
import database.DatabaseManager;
import decisionpremise.AbstractGoalType;
import decisionpremise.ActionVariableType;
import decisionpremise.DecisionGoalType;
import decisionpremise.DecisionPremiseType;
import decisionpremise.EngagementGoalType;
import decisionpremise.EnvironmentalFactorType;
import decisionpremise.FactualDecisionPremiseType;
import decisionpremise.PresumedInfluenceRelationType;
import decisionpremise.RelevanceRelationType;
import decisionpremise.SymbolicGoalType;
import decisionprocess.DecisionProcessInstance;
import decisionprocess.DecisionProcessType;
import decisionprocess.StimulusType;
import helpercomponents.Views;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import organizationalunits.BoardType;
import organizationalunits.CommiteeType;
import organizationalunits.OrganizationalUnitType;
import organizationalunits.ParticipationRelationType;
import organizationalunits.PositionType;
import supportrequirements.AnalysisNeedSatisfactionRelationType;
import supportrequirements.AnalysisNeedType;
import supportrequirements.InformationSystemType;
import supportrequirements.InformationType;
import supportrequirements.SupportRequirementType;

//Diese Klasse bietet statische Methoden an um die korrespondierenden Controller zu einer View zu erzeugen
//In Kombination mit der Klasse StageFactory lie� sich der Code zu Erzeugung von Controllern und Stages erheblich reduzieren
public final class ControllerFactory {

	private ControllerFactory() {

	}

	public static IDptTabController getDptTabController(DecisionProcessType dpt, TabPane tPane, EntityManager em) {

		IDptTabController con = new DptTabController();
		con.setDpt(dpt);
		con.setTabPane(tPane);
		con.setEntityManager(em);

		return con;
	}
	public static IDpiTabController getDpiTabController(DecisionProcessInstance dpi, TabPane tPane, EntityManager em) {
		IDpiTabController con = new DpiTabController();
		con.setDpi(dpi);
		con.setTabPane(tPane);
		con.setEntityManager(em);

		return con;
	}

	public static IControllerTypeLevel getDecPremTypeController(DecisionPremiseType decPremT, EntityManager em) {

		IDecisionPremiseTypeController con;

		if (decPremT instanceof DecisionGoalType) {

			con = new DecisionGoalTypeController();
			decPremConHelperMethod(con, decPremT, em);
			return con;

		} else if (decPremT instanceof EngagementGoalType) {

			con = new EngagementGoalTypeController();
			decPremConHelperMethod(con, decPremT, em);
			return con;

		} else if (decPremT instanceof SymbolicGoalType) {

			con = new SymbolicGoalTypeController();
			decPremConHelperMethod(con, decPremT, em);
			return con;

		} else if (decPremT instanceof ActionVariableType) {

			con = new ActionVariableTypeController();
			decPremConHelperMethod(con, decPremT, em);
			return con;

		} else {

			con = new EnvironmentalFactorTypeController();
			decPremConHelperMethod(con, decPremT, em);
			return con;

		}
	}
	
	private static void decPremConHelperMethod(IDecisionPremiseTypeController con, DecisionPremiseType decPremT, EntityManager em) {
		
		con.setEntityManager(em);
		con.setDecPremT(decPremT);
	}
	
	public static IControllerTypeLevel getJustificationController(DecisionPremiseType decPremT) {

		JustificationRelevRelTypeController con = new JustificationRelevRelTypeController();
		con.setAgt((AbstractGoalType) decPremT);
		return con;
	}

	public static IControllerTypeLevel getPresInfluRelTypeController(DecisionPremiseType decPremT,
			PresumedInfluenceRelationType pirt) {

		PresumedInfluenceRelController con = new PresumedInfluenceRelController();
		con.setFacDecPremT((FactualDecisionPremiseType) decPremT);
		con.setPirt(pirt);
		con.setEntityManager(DatabaseManager.getInstance().getEmf().createEntityManager());
		return con;
	}

	public static IControllerTypeLevel getChooseDptEditController(TabPane tPane, EntityManager em) {

		ChooseDptEditController con = new ChooseDptEditController();
		con.setEntityManager(em);
		con.settPane(tPane);
		return con;
	}

	public static IControllerTypeLevel getStimulusTypeController(DecisionProcessType decProcT, StimulusType st,
			EntityManager em) {

		StimulusTypeController con = new StimulusTypeController();
		con.setEm(em);
		con.setDpt(decProcT);
		con.setSt(st);
		return con;
	}

	public static IControllerTypeLevel getParticipationRelTypeController(ParticipationRelationType prt, DecisionProcessType dpt,
			EntityManager em) {

		ParticipationRelationTypeController con = new ParticipationRelationTypeController();
		con.setDpt(dpt);
		con.setEntityManager(em);
		con.setPrt(prt);
		return con;
	}

	public static IControllerTypeLevel getRelevanceRelTypeController(RelevanceRelationType rrt, DecisionProcessType dpt,
			EntityManager em) {

		RelevanceRelationTypeController con = new RelevanceRelationTypeController();
		con.setEm(em);
		con.setRrt(rrt);
		con.setDpt(dpt);
		return con;
	}

	public static IControllerTypeLevel getOrganizationalUnitTypeController(OrganizationalUnitType org, EntityManager em) {

		IOrgStructTypeController con;

		if (org instanceof BoardType) {

			con = new BoardTypeController();
			orgUnitTypeConHelperMethod(org, con, em);
			return con;

		} else if (org instanceof CommiteeType) {

			con = new CommitteeTypeController();
			orgUnitTypeConHelperMethod(org, con, em);
			return con;

		} else if (org instanceof PositionType) {

			con = new PositionTypeController();
			orgUnitTypeConHelperMethod(org, con, em);
			return con;

		} else {

			con = new RoleTypeController();
			orgUnitTypeConHelperMethod(org, con, em);
			return con;
		}
	}

	private static void orgUnitTypeConHelperMethod(OrganizationalUnitType org, IOrgStructTypeController con, EntityManager em) {
		
		con.setOrganizationalUnitType(org);
		con.setEntityManager(em);
	}
	
	public static IControllerTypeLevel getSuppReqTypeController(SupportRequirementType srt, DecisionPremiseType decPremT,
			EntityManager em) {

		ISuppReqTypeController con;
		if (srt instanceof AnalysisNeedType) {

			con = new AnalysisNeedTypeController();
			con.setEntityManager(em);
			con.setSuppReqType(srt);
			con.setDecPremT(decPremT);
		} else {

			con = null;
		}
		return con;
	}

	public static IControllerTypeLevel getAnalysisNeedSatisfRelController(AnalysisNeedSatisfactionRelationType ansrt,
			AnalysisNeedType ant, EntityManager em) {

		AnalysisNeedSatisfactionRelController con = new AnalysisNeedSatisfactionRelController();
		con.setAnsrt(ansrt);
		con.setAnt(ant);
		con.setEm(em);
		return con;
	}
	
	public static IControllerTypeLevel getInformationSystemTypeController(InformationSystemType ist, EntityManager em) {
		
		InformationSystemTypeController con = new InformationSystemTypeController();
		con.setEntityManager(em);
		con.setIst(ist);
		return con;
	}
	
	public static IControllerTypeLevel getNewInformationTypeController(InformationType it, EntityManager em) {
		
		NewInformationTypeController con = new NewInformationTypeController();
		con.setEntityManager(em);
		con.setIt(it);
		return con;
	}
	
	
}
