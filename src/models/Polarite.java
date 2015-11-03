package models;

public enum Polarite {
	NEUTRE, POSITIF, NEGATIF, UNDEFINED;

	public static int valueOf(Polarite p){
		switch(p){
		case NEGATIF:
			return 0;
		case NEUTRE:
			return 2;
		case POSITIF:
			return 4;
		default:
			return 2;
		
		}


	}

	public static Polarite keyOf(int i){
		switch(i){
		case 0:
			return Polarite.NEGATIF;
		case 2:
			return Polarite.NEUTRE;
		case 4:
			return Polarite.POSITIF;
		default:
			return Polarite.UNDEFINED;
		}
	}
}
