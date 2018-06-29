package decisionprocess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ListTest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
//	private List<String> list = Arrays.asList(new String("HURENSOHN"), new String("FICK DICH MAAAAN"));
//
//	public List<String> getList() {
//		return list;
//	}
	
//	()
//	private List<StimulusType> list = Arrays.asList(new StimulusType("FUCK1", "DESC1"), new StimulusType("FUCK2222", "DESC222"));

//	public List<StimulusType> getList() {
//		return list;
//	}
}
