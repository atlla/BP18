package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import decisionpremise.AbstractGoalType;
import decisionpremise.CoherenceRationaleSpec;
import decisionpremise.ConsensusRationaleSpec;
import decisionpremise.CorrespondenceRationaleSpec;
import decisionpremise.RationaleSpec;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainScreenController;

public class JustificationRelevRelTypeController implements Initializable, IControllerTypeLevel {

	private AbstractGoalType agt;
	private RationaleSpec rs;
	private Stage stage;

	@FXML
	private TextArea ta_ConRsPrelAffirm;

	@FXML
	private Label lb_CohRsDiscussion;

	@FXML
	private Label lb_CohRsSource;

	@FXML
	private TextArea ta_description;

	@FXML
	private Label lb_ConRsProposition;

	@FXML
	private Label lb_CorRsSource;

	@FXML
	private TextArea ta_CorRsSource;

	@FXML
	private TextField tf_CorRsValidity;

	@FXML
	private TextArea ta_CohRsDiscussion;

	@FXML
	private Label lb_ConRsPrelAffirm;

	@FXML
	private TextArea ta_CohRsSource;

	@FXML
	private Label lb_CorRsValidity;

	@FXML
	private TextArea ta_ConRsProp;

	@FXML
	private TextField tf_CorRsReliability;

	@FXML
	private Label lb_CorRsReliability;

	@FXML
	private ChoiceBox<String> cb_RatSpec;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		checkFields();
		cb_RatSpec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue.equals("Coherence rationale spec")) {

				System.out.println("Coherence");
				CoherenceRationaleSpec cohRs = new CoherenceRationaleSpec();
				cohRs.descriptionProperty().bind(ta_description.textProperty());
				cohRs.discussionProperty().bind(ta_CohRsDiscussion.textProperty());
				cohRs.sourceProperty().bind(ta_CohRsSource.textProperty());
				rs = cohRs;
				setVisibilityCohRsElements(true);
				bindProperties();
			} else if (newValue.equals("Consensus rationale spec")) {

				System.out.println("Consensus");
				ConsensusRationaleSpec conRs = new ConsensusRationaleSpec();
				conRs.descriptionProperty().bind(ta_description.textProperty());
				rs = conRs;
				setVisibilityConRsElements(true);
				bindProperties();
			} else if (newValue.equals("Correspondence rationale spec")) {

				CorrespondenceRationaleSpec corrRs = new CorrespondenceRationaleSpec();
				System.out.println("Correspondence");
				setVisibilityCorRsElements(true);
				rs = corrRs;
				bindProperties();
			}
		});
	}

	private void checkFields() {

		if (rs != null) {

			cb_RatSpec.setDisable(true);
			if (rs instanceof CoherenceRationaleSpec) {

				setVisibilityCohRsElements(true);
				ta_description.setText(rs.getDescription());
				ta_CohRsDiscussion.setText(((CoherenceRationaleSpec) rs).getDiscussion());
				ta_CohRsSource.setText(((CoherenceRationaleSpec) rs).getSource());
				cb_RatSpec.getSelectionModel().select("Coherence rationale spec");
				bindProperties();
			} else if (rs instanceof ConsensusRationaleSpec) {

				setVisibilityConRsElements(true);
				ta_description.setText(rs.getDescription());
				ta_ConRsPrelAffirm.setText(((ConsensusRationaleSpec) rs).getPreliminaryAffirmation());
				ta_ConRsProp.setText(((ConsensusRationaleSpec) rs).getProposition());
				cb_RatSpec.getSelectionModel().select("Consensus rationale spec");
				bindProperties();
			} else if (rs instanceof CorrespondenceRationaleSpec) {

				setVisibilityCorRsElements(true);
				ta_description.setText(rs.getDescription());
				ta_CorRsSource.setText(((CoherenceRationaleSpec) rs).getSource());
				tf_CorRsReliability.setText(((CorrespondenceRationaleSpec) rs).getReliability().getValue());
				tf_CorRsValidity.setText(((CorrespondenceRationaleSpec) rs).getValidity().getValue());
				cb_RatSpec.getSelectionModel().select("Correspondence rationale spec");
				bindProperties();
			}
		}
	}

	private void bindProperties() {

		if (rs instanceof CoherenceRationaleSpec) {

			rs.descriptionProperty().bind(ta_description.textProperty());
			((CoherenceRationaleSpec) rs).discussionProperty().bind(ta_CohRsDiscussion.textProperty());
			((CoherenceRationaleSpec) rs).sourceProperty().bind(ta_CohRsSource.textProperty());
		} else if (rs instanceof ConsensusRationaleSpec) {

			rs.descriptionProperty().bind(ta_description.textProperty());
			((ConsensusRationaleSpec) rs).preliminaryAffirmationProperty().bind(ta_ConRsPrelAffirm.textProperty());
			((ConsensusRationaleSpec) rs).propositionProperty().bind(ta_ConRsProp.textProperty());
		} else if (rs instanceof CorrespondenceRationaleSpec) {

			rs.descriptionProperty().bind(ta_description.textProperty());
			((CorrespondenceRationaleSpec) rs).sourceProperty().bind(ta_CorRsSource.textProperty());
			((CorrespondenceRationaleSpec) rs).getValidity().valueProperty().bind(tf_CorRsValidity.textProperty());
			((CorrespondenceRationaleSpec) rs).getReliability().valueProperty()
					.bind(tf_CorRsReliability.textProperty());
		}
	}

	private void unbindProperties() {

		rs.descriptionProperty().unbind();

		if (rs instanceof CoherenceRationaleSpec) {

			((CoherenceRationaleSpec) rs).discussionProperty().unbind();
			((CoherenceRationaleSpec) rs).sourceProperty().unbind();
		} else if (rs instanceof ConsensusRationaleSpec) {

			((ConsensusRationaleSpec) rs).preliminaryAffirmationProperty().unbind();
			((ConsensusRationaleSpec) rs).propositionProperty().unbind();
		} else if (rs instanceof CorrespondenceRationaleSpec) {

			((CorrespondenceRationaleSpec) rs).sourceProperty().unbind();
			((CorrespondenceRationaleSpec) rs).getValidity().valueProperty().unbind();
			((CorrespondenceRationaleSpec) rs).getReliability().valueProperty().unbind();
		}

	}

	@FXML
	void btn_saveAction(ActionEvent event) {

		if (cb_RatSpec.getSelectionModel().getSelectedItem() != null) {

			if (!agt.getJustification().contains(rs)) {

				agt.getJustification().add(rs);
				unbindProperties();
				stage.close();
			} else {

				unbindProperties();
				stage.close();
			}
		}
	}

	public void setVisibilityCohRsElements(boolean b) {

		if (b) {
			setVisibilityConRsElements(false);
			setVisibilityCorRsElements(false);
		}

		lb_CohRsDiscussion.setVisible(b);
		lb_CohRsSource.setVisible(b);
		ta_CohRsDiscussion.setVisible(b);
		ta_CohRsSource.setVisible(b);

	}

	public void setVisibilityConRsElements(boolean b) {

		if (b) {
			setVisibilityCohRsElements(false);
			setVisibilityCorRsElements(false);
		}

		lb_ConRsProposition.setVisible(b);
		lb_ConRsPrelAffirm.setVisible(b);
		ta_ConRsProp.setVisible(b);
		ta_ConRsPrelAffirm.setVisible(b);

	}

	public void setVisibilityCorRsElements(boolean b) {

		if (b) {
			setVisibilityCohRsElements(false);
			setVisibilityConRsElements(false);
		}

		lb_CorRsReliability.setVisible(b);
		lb_CorRsSource.setVisible(b);
		lb_CorRsValidity.setVisible(b);
		ta_CorRsSource.setVisible(b);
		tf_CorRsReliability.setVisible(b);
		tf_CorRsValidity.setVisible(b);
	}

	public void setAgt(AbstractGoalType agt) {

		this.agt = agt;
	}

	public void setRs(RationaleSpec rs) {

		this.rs = rs;
	}

	@Override
	public void setStage(Stage stage) {

		this.stage = stage;
	}

	@Override
	public void setEntityManager(EntityManager em) {

	}

	@Override
	public void setMsc(MainScreenController msc) {
		// TODO Auto-generated method stub
		
	}

}
