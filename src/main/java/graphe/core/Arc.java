package main.java.graphe.core;

public class Arc {
	private String source,destination;
	private Integer valuation;
	
	public Arc(String source, String destination, Integer valuation){
		this.source = source;
		this.destination = destination;
		this.valuation = valuation;
	}
	
	public Arc(String source) {
		this(source,"",0);
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public Integer getValuation() {
		return valuation;
	}
	
	public boolean HasDest() {
		return !this.destination.equals("");
	}

	@Override
	public boolean equals(Object arc) {
		if(arc == this) return true; //comparaison de références, si arc est this sont la même référence alors arc et this sont identiques

		if(!(arc instanceof Arc)) return false; //arc n'est pas de type Arc

		Arc arc2 = (Arc) arc; //on change le type de arc (Object en Arc)

		return this.source.compareTo(arc2.getSource()) == 0 && this.destination.compareTo(arc2.getDestination())==0;
	}
	
	public String toString() {
		String s ="";
		s += source;
		if(HasDest()) {
			s+="-"+ destination + "(" + valuation + ")";
		}
		else
			s+=":";
		return s;
	}	
}
