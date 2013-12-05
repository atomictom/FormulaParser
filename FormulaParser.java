import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;

// Format of formulas: [(](shorthand|longhand)[count][)][count]
//
// shorthand is case sensitive and is the symbol notation (e.g. C for Carbon, Co for Cobalt and Hg for Lead)
// longhand is the full element name and is not case sensitive (e.g. Carbon, CARBON, CaRBoN, lead)
// If a count is left out, it's implicitly '1', else you can put a count after a shorthand or longhand element
// You can group element with parenthesis and apply counts to the groups. (e.g. C2(HO)2)
// Any other symbols are stripped from the formula (e.g. -, +). Leaving them in does no harm
//
// The output is a map from the capitalized element name as a string, to it's count

public class FormulaParser {
	
	public static String symbols_map = 
		"H Hydrogen\n" +
		"He Helium\n" +
		"Li Lithium\n" +
		"Be Beryllium\n" +
		"B Boron\n" +
		"C Carbon\n" +
		"N Nitrogen\n" +
		"O Oxygen\n" +
		"F Fluorine\n" +
		"Ne Neon\n" +
		"Na Sodium\n" +
		"Mg Magnesium\n" +
		"Al Aluminum\n" +
		"Si Silicon\n" +
		"P Phosphorus\n" +
		"S Sulfur\n" +
		"Cl Chlorine\n" +
		"Ar Argon\n" +
		"K Potassium\n" +
		"Ca Calcium\n" +
		"Sc Scandium\n" +
		"Ti Titanium\n" +
		"V Vanadium\n" +
		"Cr Chromium\n" +
		"Mn Manganese\n" +
		"Fe Iron\n" +
		"Co Cobalt\n" +
		"Ni Nickel\n" +
		"Cu Copper\n" +
		"Zn Zinc\n" +
		"Ga Gallium\n" +
		"Ge Germanium\n" +
		"As Arsenic\n" +
		"Se Selenium\n" +
		"Br Bromine\n" +
		"Kr Krypton\n" +
		"Rb Rubidium\n" +
		"Sr Strontium\n" +
		"Y Yttrium\n" +
		"Zr Zirconium\n" +
		"Nb Niobium\n" +
		"Mo Molybdenum\n" +
		"Tc Technetium\n" +
		"Ru Ruthenium\n" +
		"Rh Rhodium\n" +
		"Pd Palladium\n" +
		"Ag Silver\n" +
		"Cd Cadmium\n" +
		"In Indium\n" +
		"Sn Tin\n" +
		"Sb Antimony\n" +
		"Te Tellurium\n" +
		"I Iodine\n" +
		"Xe Xenon\n" +
		"Cs Cesium\n" +
		"Ba Barium\n" +
		"La Lanthanum\n" +
		"Ce Cerium\n" +
		"Pr Praseodymium\n" +
		"Nd Neodymium\n" +
		"Pm Promethium\n" +
		"Sm Samarium\n" +
		"Eu Europium\n" +
		"Gd Gadolinium\n" +
		"Tb Terbium\n" +
		"Dy Dysprosium\n" +
		"Ho Holmium\n" +
		"Er Erbium\n" +
		"Tm Thulium\n" +
		"Yb Ytterbium\n" +
		"Lu Lutetium\n" +
		"Hf Hafnium\n" +
		"Ta Tantalum\n" +
		"W Tungsten\n" +
		"Re Rhenium\n" +
		"Os Osmium\n" +
		"Ir Iridium\n" +
		"Pt Platinum\n" +
		"Au Gold\n" +
		"Hg Mercury\n" +
		"Tl Thallium\n" +
		"Pb Lead\n" +
		"Bi Bismuth\n" +
		"Po Polonium\n" +
		"At Astatine\n" +
		"Rn Radon\n" +
		"Fr Francium\n" +
		"Ra Radium\n" +
		"Ac Actinium\n" +
		"Th Thorium\n" +
		"Pa Protactinium\n" +
		"U Uranium\n" +
		"Np Neptunium\n" +
		"Pu Plutonium\n" +
		"Am Americium\n" +
		"Cm Curium\n" +
		"Bk Berkelium\n" +
		"Cf Californium\n" +
		"Es Einsteinium\n" +
		"Fm Fermium\n" +
		"Md Mendelevium\n" +
		"No Nobelium\n" +
		"Lr Lawrencium\n" +
		"Rf Rutherfordium\n" +
		"Db Dubnium\n" +
		"Sg Seaborgium\n" +
		"Bh Bohrium\n" +
		"Hs Hassium\n" +
		"Mt Meitnerium\n" +
		"Ds Darmstadtium\n" +
		"Rg Roentgenium\n" +
		"Cn Copernicium\n" +
		"Uut Ununtrium\n" +
		"Fl Flerovium\n" +
		"Uup Ununpentium\n" +
		"Lv Livermorium\n" +
		"Uus Ununseptium\n" +
		"Uuo Ununoctium";

	public static Map<String,String> getSymbolMap(){
		Map<String, String> element_map = new HashMap<>();

		Scanner in = new Scanner(symbols_map);

		while(in.hasNext()){
			String shortform = in.next();
			String longform = in.next();
			element_map.put(shortform, longform);
			element_map.put(longform.toUpperCase(), longform);
		}
		return element_map;
	}


	public static Map<String,Integer> parseFormulaString(String formula, Map<String,String> element_map){
		Map<String,Integer> element_counts = new HashMap<>();
		/* formula = formula.toUpperCase(); */
		System.out.println(formula);
		int len = formula.length();
		for(int i = 0; i < len;){
			boolean is_group = false;
			if(formula.charAt(i) == '('){
				i++;
				is_group = true;
			}
			int repeat_count = 1;
			Map<String,Integer> atoms_in_group = new HashMap<>();
			do{
				int start = i;
				int restore_i = 0;
				String element = null;
				String restore_element = null;
				while(i < len && Character.isLetter(formula.charAt(i))){
					i++;
					element = formula.substring(start, i);
					/* System.out.println( element); */
					String element_from_map = element_map.get(element);
					if(element_from_map == null)
						element_from_map = element_map.get(element.toUpperCase());
					if(element_from_map != null){
						/* System.out.println("element from map: " + element_from_map); */
						restore_i = i;
						restore_element = element_from_map;
					}
				}
				if(restore_element != null){
					i = restore_i;
					element = restore_element;
				}

				if(element == null || element == ""){
					System.out.println("Parse error: could not detect an element where one was expected in formula string.");
					System.out.println("Remaining formula to parse: " + formula.substring(i));
					System.exit(1);
				}

				start = i;
				while(i < len && Character.isDigit(formula.charAt(i))){
					i++;
				}
				int count;
				try{
					count = Integer.parseInt(formula.substring(start, i));
				}catch(NumberFormatException e){
					count = 1;
				}
				/* System.out.println("element: "+element); */
				atoms_in_group.put(element, count);
				if(i < len && formula.charAt(i) == ')'){
					if(!is_group)
						System.out.println("Parse error: unmatched parenthesis detected...");
					
					i++;
					is_group = false;

					start = i;
					while(i < len && Character.isDigit(formula.charAt(i)))
						i++;

					try{
						repeat_count = Integer.parseInt(formula.substring(start, i));
					}catch(NumberFormatException e){
						repeat_count = 1;
					}
				}
			}while(is_group == true);
			for(String atom_type : atoms_in_group.keySet()){
				int current_value = 0;
				if(element_counts.containsKey(atom_type))
					current_value = element_counts.get(atom_type);
				element_counts.put(atom_type, current_value + atoms_in_group.get(atom_type) * repeat_count);			
			}
		}
		for(Map.Entry<String,Integer> entry : element_counts.entrySet()){
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		return element_counts;
	}

	public static final void main(String args[]) {
		Map<String,String> element_map = getSymbolMap();

		Scanner in = new Scanner(System.in);
		System.out.print("Enter Chemical Formula: ");
		String formula = in.nextLine();
		formula = formula.replaceAll("[^a-zA-Z0-9()]", "");
		if(formula.length() == 0)
			formula = "CH4";
		Map<String,Integer> element_counts = parseFormulaString(formula, element_map);
	}
}
