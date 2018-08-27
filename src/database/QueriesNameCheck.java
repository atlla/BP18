package database;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import decisionpremise.DecisionPremiseType;
import decisionprocess.DecisionProcessInstance;
import decisionprocess.DecisionProcessType;
import decisionprocess.StimulusType;
import helpercomponents.AlertDialogFactory;
import organizationalunits.BoardType;
import organizationalunits.CommiteeType;
import organizationalunits.OrganizationalUnitType;
import organizationalunits.PositionType;
import organizationalunits.RoleType;
import supportrequirements.InformationSystemType;
import supportrequirements.InformationType;
import supportrequirements.SupportRequirementType;

public final class QueriesNameCheck {

	public static boolean dptNameCheck(String name) {

		try {
			EntityManager em = generateEm();
			TypedQuery<DecisionProcessType> q1 = em.createQuery(
					"SELECT dpt FROM DecisionProcessType dpt WHERE dpt.name = '" + name + "'",
					DecisionProcessType.class);
			if (q1.getResultList().size() > 0) {

				return false;
			}
			em.close();
		} catch (Exception e) {

			return true;
		}

		return true;
	}
	
	public static boolean dpiNameCheck(String name) {

		try {
			EntityManager em = generateEm();
			TypedQuery<DecisionProcessInstance> q1 = em.createQuery(
					"SELECT dpi FROM DecisionProcessInstance dpi WHERE dpi.instanceName = '" + name + "'",
					DecisionProcessInstance.class);
			if (q1.getResultList().size() > 0) {

				return false;
			}
			em.close();
		} catch (Exception e) {

			return true;
		}

		return true;
	}

	public static boolean dptEditNameCheck(String oldName, String newName) {

		boolean tmp = true;
		if (!oldName.equals(newName)) {

			try {

				System.out.println("edit check");
				System.out.println(newName);
				EntityManager em = generateEm();
				EntityType<DecisionProcessType> type = em.getMetamodel().entity(DecisionProcessType.class);
				if (type != null) {

					TypedQuery<String> q1 = em.createQuery("SELECT dpt.name FROM DecisionProcessType dpt",
							String.class);
					if (q1.getResultList().size() > 0) {

						for (String dpt : q1.getResultList()) {

							if (dpt.equals(newName)) {

								tmp = false;
							}
						}
					}
				} else {

					return true;
				}

				em.close();
			} catch (Exception e) {

				e.printStackTrace();
				return true;
			}
			return tmp;

		} else {

			return true;
		}
	}
	
	public static boolean dpiEditNameCheck(String oldName, String newName) {

		boolean tmp = true;
		if (!oldName.equals(newName)) {

			try {

				System.out.println("edit check");
				System.out.println(newName);
				EntityManager em = generateEm();
				EntityType<DecisionProcessInstance> type = em.getMetamodel().entity(DecisionProcessInstance.class);
				if (type != null) {

					TypedQuery<String> q1 = em.createQuery("SELECT dpi.instanceName FROM DecisionProcessInstance dpi",
							String.class);
					if (q1.getResultList().size() > 0) {

						for (String dpt : q1.getResultList()) {

							if (dpt.equals(newName)) {

								tmp = false;
							}
						}
					}
				} else {

					return true;
				}

				em.close();
			} catch (Exception e) {

				e.printStackTrace();
				return true;
			}
			return tmp;

		} else {

			return true;
		}
	}
	public static boolean orgStructTypeNameCheck(String name) {

		try {
			EntityManager em = generateEm();
			TypedQuery<OrganizationalUnitType> q1 = em.createQuery(
					"SELECT out FROM OrganizationalUnitType out WHERE out.name = '" + name + "'",
					OrganizationalUnitType.class);
			if (q1.getResultList().size() > 0) {

				return false;
			}
			em.close();
		} catch (Exception e) {

			return true;
		}

		return true;
	}

	public static boolean orgStructTypeEditNameCheck(String oldName, String newName) {

		boolean tmp = true;
		if (!oldName.equals(newName)) {

			try {
				EntityManager em = generateEm();
				TypedQuery<OrganizationalUnitType> q1 = em.createQuery("SELECT o FROM OrganizationalUnitType o",
						OrganizationalUnitType.class);
				if (q1.getResultList().size() > 0) {

					for (OrganizationalUnitType dpt : q1.getResultList()) {

						if (dpt.getName().equals(newName)) {

							tmp = false;
						}
					}
				}
				em.close();
			} catch (Exception e) {

				e.printStackTrace();
				return true;
			}
			return tmp;

		} else {

			return true;
		}
	}

	public static boolean stimulusTypeNameCheck(String name) {

		try {
			EntityManager em = generateEm();
			TypedQuery<StimulusType> q1 = em
					.createQuery("SELECT st FROM StimulusType st WHERE st.name = '" + name + "'", StimulusType.class);
			if (q1.getResultList().size() > 0) {

				return false;
			}
			em.close();
		} catch (Exception e) {

			return true;
		}

		return true;
	}

	public static boolean stimulusTypeEditNameCheck(String oldName, String newName) {

		boolean tmp = true;
		if (!oldName.equals(newName)) {

			try {
				EntityManager em = generateEm();
				TypedQuery<StimulusType> q1 = em.createQuery("SELECT st FROM StimulusType st", StimulusType.class);
				if (q1.getResultList().size() > 0) {

					for (StimulusType st : q1.getResultList()) {

						if (st.getName().equals(newName)) {

							tmp = false;
						}
					}
				}
				em.close();
			} catch (Exception e) {

				e.printStackTrace();
				return true;
			}
			return tmp;

		} else {

			return true;
		}
	}

	public static boolean InformationSystemTypeNameCheck(String name) {

		try {
			EntityManager em = generateEm();
			TypedQuery<InformationSystemType> q1 = em.createQuery(
					"SELECT ist FROM InformationSystemType ist WHERE ist.name = '" + name + "'",
					InformationSystemType.class);
			if (q1.getResultList().size() > 0) {

				return false;
			}
			em.close();
		} catch (Exception e) {

			return true;
		}

		return true;
	}

	public static boolean InformationTypeNameCheck(String name) {

		try {
			EntityManager em = generateEm();
			TypedQuery<InformationType> q1 = em.createQuery(
					"SELECT it FROM InformationType it WHERE it.name = '" + name + "'", InformationType.class);
			if (q1.getResultList().size() > 0) {

				return false;
			}
			em.close();
		} catch (Exception e) {

			return true;
		}

		return true;
	}

	public static boolean informationTypeEditNameCheck(String oldName, String newName) {

		boolean tmp = true;
		if (!oldName.equals(newName)) {

			try {
				EntityManager em = generateEm();
				TypedQuery<InformationType> q1 = em.createQuery("SELECT it FROM InformationType it", InformationType.class);
				if (q1.getResultList().size() > 0) {

					for (InformationType st : q1.getResultList()) {

						if (st.getName().equals(newName)) {

							tmp = false;
						}
					}
				}
				em.close();
			} catch (Exception e) {

				e.printStackTrace();
				return true;
			}
			return tmp;

		} else {

			return true;
		}
	}

	public static boolean DecPremTypeNameCheck(String name) {

		try {
			EntityManager em = generateEm();
			TypedQuery<DecisionPremiseType> q1 = em.createQuery(
					"SELECT decPremT FROM DecisionPremiseType decPremT WHERE decPremT.name = '" + name + "'",
					DecisionPremiseType.class);
			if (q1.getResultList().size() > 0) {

				return false;
			}
			em.close();
		} catch (Exception e) {

			return true;
		}

		return true;
	}

	public static boolean DecPremTypeEditNameCheck(String oldName, String newName) {

		boolean tmp = true;
		if (!oldName.equals(newName)) {

			try {
				EntityManager em = generateEm();
				TypedQuery<DecisionPremiseType> q1 = em.createQuery("SELECT d FROM DecisionPremiseType d",
						DecisionPremiseType.class);
				if (q1.getResultList().size() > 0) {

					for (DecisionPremiseType dpt : q1.getResultList()) {

						if (dpt.getName().equals(newName)) {

							tmp = false;
						}
					}
				}
				em.close();
			} catch (Exception e) {

				e.printStackTrace();
				return true;
			}
			return tmp;

		} else {

			return true;
		}
	}

	public static boolean SuppReqTypeNameCheck(String name) {

		try {
			EntityManager em = generateEm();
			TypedQuery<SupportRequirementType> q1 = em.createQuery(
					"SELECT s FROM SupportRequirementType s WHERE s.name = '" + name + "'",
					SupportRequirementType.class);
			if (q1.getResultList().size() > 0) {

				return false;
			}
			em.close();
		} catch (Exception e) {

			return true;
		}

		return true;
	}

	public static boolean SuppReqTypeEditNameCheck(String oldName, String newName) {

		boolean tmp = true;
		if (!oldName.equals(newName)) {

			try {
				EntityManager em = generateEm();
				TypedQuery<SupportRequirementType> q1 = em.createQuery("SELECT s FROM SupportRequirementType s",
						SupportRequirementType.class);
				if (q1.getResultList().size() > 0) {

					for (SupportRequirementType s : q1.getResultList()) {

						if (s.getName().equals(newName)) {

							tmp = false;
						}
					}
				}
				em.close();
			} catch (Exception e) {

				e.printStackTrace();
				return true;
			}
			return tmp;

		} else {

			return true;
		}
	}

	private static EntityManager generateEm() {

		return DatabaseManager.getInstance().getEmf().createEntityManager();
	}
}
