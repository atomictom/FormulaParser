FormulaParser
=============

A simple parser of chemical formulas in Java

How To Use
==========

By itself, you can just compile run the program to get a prompt for a formula. Type one in and it will show you how many of each type of atom are required for the formula. 

Format of formulas: `[(](shorthand|longhand)[count][)][count]`

shorthand is case sensitive and is the symbol notation (e.g. C for Carbon, Co for Cobalt and Hg for Lead)
longhand is the full element name and is not case sensitive (e.g. Carbon, CARBON, CaRBoN, lead)
If a count is left out, it's implicitly '1', else you can put a count after a shorthand or longhand element
You can group element with parenthesis and apply counts to the groups. (e.g. C2(HO)2)
Any other symbols are stripped from the formula (e.g. -, +). Leaving them in does no harm

The output is a map from the capitalized element name as a string, to it's count
