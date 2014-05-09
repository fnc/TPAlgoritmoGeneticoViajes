
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class viajeFF extends FitnessFunction {
	
	   private final int m_targetAmount;

	    /**
	     * Constructs this MinimizingMakeChangeFitnessFunction with the desired
	     * amount of change to make.
	     *
	     * @param a_targetAmount The desired amount of change, in cents. This
	     *                       value must be between 1 and 99 cents.
	     */
	    public viajeFF( int a_targetAmount )
	    {
//	        if( a_targetAmount < 1 || a_targetAmount > 99 )
//	        {
//	            throw new IllegalArgumentException(
//	                "Change amount must be between 1 and 99 cents." );
//	        }

	        m_targetAmount = a_targetAmount;
	    }


	    /**
	     * Determine the fitness of the given Chromosome instance. The higher the
	     * return value, the more fit the instance. This method should always
	     * return the same fitness value for two equivalent Chromosome instances.
	     *
	     * @param a_subject: The Chromosome instance to evaluate.
	     *
	     * @return A positive integer reflecting the fitness rating of the given
	     *         Chromosome.
	     */
	    public double evaluate( IChromosome a_subject )
	    {
	    	double fitness = 0;
//	        // The fitness value measures both how close the value is to the
//	        // target amount supplied by the user and the total number of coins
//	        // represented by the solution. We do this in two steps: first,
//	        // we consider only the represented amount of change vs. the target
//	        // amount of change and calculate higher fitness values for amounts
//	        // closer to the target, and lower fitness values for amounts further
//	        // away from the target. If the amount equals the target, then we go
//	        // to step 2, which adjusts the fitness to a higher value for
//	        // solutions representing fewer total coins, and lower fitness
//	        // values for solutions representing a larger total number of coins.
//	        // ------------------------------------------------------------------
//	        int changeAmount = amountOfChange( a_subject );
//	        int totalCoins = getTotalNumberOfCoins( a_subject );
//	        int changeDifference = Math.abs( m_targetAmount - changeAmount );
//
//	        // Step 1: Determine the distance of the amount represented by the
//	        // solution from the target amount. Since we know the maximum amount
//	        // of change is 99 cents, we'll subtract from that the difference
//	        // between the solution amount and the target amount. That will give
//	        // the desired effect of returning higher values for amounts close
//	        // to the target amount and lower values for amounts further away
//	        // from the target amount.
//	        // ------------------------------------------------------------------
//	        fitness = ( 99 - changeDifference );
//
//	        // Step 2: If the solution amount equals the target amount, then
//	        // we add additional fitness points for solutions representing fewer
//	        // total coins.
//	        // -----------------------------------------------------------------
//	        if( changeAmount == m_targetAmount )
//	        {
//	            fitness += 100 - ( 10 * totalCoins );
//	        }

	        return fitness;
	    }


	    public static int evaluarUbicacion( IChromosome unaMuestra )
	    {
	    	//La posicion 0 corresponde a la ubicacion
	        int ubicacion = obtenerValorGen( unaMuestra, 0 );
	        int puntos = 0;
	        
	        switch (ubicacion){
	        case 1:{
	        	//INVALIDA
	        	break;
	        }
	        case 2:{
	        	//Almagro
	        	puntos += 7;
	        	break;
	        }
	        case 3:{
	        	//Balvanera
	        	puntos += 5;
	        	break;
	        }
	        case 4:{
	        	//Caballito
	        	puntos += 8;
	        	break;
	        }
	        case 5:{
	        	//Belgrano
	        	puntos += 10;
	        	break;
	        }
	        case 6:{
	        	//Parque Patricios
	        	puntos += 1;
	        	break;
	        }
	        case 7:{
	        	//INVALIDA
	        	break;
	        }
	        default:{
	        	break;
	        }
	        }
	        return puntos;
	    }

	    public static int evaluarDistanciaEstacion( IChromosome unaMuestra )
	    {
	    	//La posicion 1 corresponde a la distancia a una estacion
	        int distancia = obtenerValorGen( unaMuestra, 1 );
	        int puntos = 0;
	        
	        switch (distancia){

	        case 2:{
	        	//Hasta 100m
	        	puntos += 10;
	        	break;
	        }
	        case 3:{
	        	//100-200m
	        	puntos += 8;
	        	break;
	        }
	        case 4:{
	        	//200-300m
	        	puntos += 5;
	        	break;
	        }
	        case 5:{
	        	//300-400m
	        	puntos += 3;
	        	break;
	        }
	        case 6:{
	        	//Mas de 400m
	        	puntos += 1;
	        	break;
	        }

	        default:{
	        	//INVALIDO
	        	break;
	        }
	        }
	        return puntos;
	    }

	    public static int evaluarAntiguedad( IChromosome unaMuestra )
	    {
	    	//La posicion 2 corresponde a la antiguedad
	        int antiguedad = obtenerValorGen( unaMuestra, 2 );
	        int puntos = 0;
	        
	        switch (antiguedad){

	        case 0:{
	        	//A estrenar
	        	puntos += 10;
	        	break;
	        }
	        case 1:{
	        	//Hasta 10 años
	        	puntos += 7;
	        	break;
	        }
	        case 3:{
	        	//hasta 20 años
	        	puntos += 4;
	        	break;
	        }
	        case 2:{
	        	//30 años o mas
	        	puntos += 1;
	        	break;
	        }
	        default:{
	        	//INVALIDO
	        	break;
	        }
	        }
	        return puntos;
	    }

	    public static int evaluarPrecioAlquiler( IChromosome unaMuestra )
	    {
	    	//La posicion 3 corresponde al precio del alquiler
	        int precio = obtenerValorGen( unaMuestra, 3 );
	        int puntos = 0;
	        
	        switch (precio){


	        case 1:{
	        	//Hasta 3000
	        	puntos += 10;
	        	break;
	        }
	        case 2:{
	        	//3000 a 3500
	        	puntos += 8;
	        	break;
	        }
	        case 3:{
	        	//3500 a 4000
	        	puntos += 6;
	        	break;
	        }
	        case 4:{
	        	//4000 a 5500
	        	puntos += 4;
	        	break;
	        }
	        case 5:{
	        	//mayor a 5500
	        	puntos += 1;
	        	break;
	        }
	        default:{
	        	//INVALIDO
	        	break;
	        }
	        }
	        return puntos;
	    }

	    public static int evaluarCantidadAmbientes( IChromosome unaMuestra )
	    {
	    	//La posicion 4 corresponde a la cantidad de ambientes
	        int ambientes = obtenerValorGen( unaMuestra, 4 );
	        int puntos = 0;
	        
	        switch (ambientes){

	        case 0:{
	        	//1 ambiente
	        	puntos += 0;
	        	break;
	        }
	        case 1:{
	        	//2 ambientes
	        	puntos += 5;
	        	break;
	        }
	        case 2:{
	        	//3 ambientes
	        	puntos += 8;
	        	break;
	        }
	        case 3:{
	        	//4 ambientes o mas
	        	puntos += 10;
	        	break;
	        }
	        default:{
	        	//INVALIDO
	        	break;
	        }
	        }
	        return puntos;
	    }

	    /**
	     * Retrieves the number of coins represented by the given potential
	     * solution at the given gene position.
	     *
	     * @param a_potentialSolution The potential solution to evaluate.
	     * @param a_position The gene position to evaluate.
	     * @return the number of coins represented by the potential solution
	     *         at the given gene position.
	     */
	    public static int obtenerValorGen( IChromosome unaMuestra, int posicion )
	    {
	        Integer valorGen = (Integer) unaMuestra.getGene(posicion).getAllele();

	        return valorGen.intValue();
	    }


//	    /**
//	     * Returns the total number of coins represented by all of the genes in
//	     * the given chromosome.
//	     *
//	     * @param a_potentialsolution The potential solution to evaluate.
//	     * @return The total number of coins represented by the given Chromosome.
//	     */
//	    public static int getTotalNumberOfCoins( IChromosome a_potentialsolution )
//	    {
//	        int totalCoins = 0;
//
//	        int numberOfGenes = a_potentialsolution.size();
//	        for( int i = 0; i < numberOfGenes; i++ )
//	        {
//	            totalCoins += getNumberOfCoinsAtGene( a_potentialsolution, i );
//	        }
//
//	        return totalCoins;
//	    }
	    
	    public static void main(String[] args) throws Exception {
	    	  // Start with a DefaultConfiguration, which comes setup with the
	    	  // most common settings.
	    	  // -------------------------------------------------------------
	    	
	    	  Configuration conf = new DefaultConfiguration();

	    	  // Set the fitness function we want to use, which is our
	    	  // MinimizingMakeChangeFitnessFunction that we created earlier.
	    	  // We construct it with the target amount of change provided
	    	  // by the user.
	    	  // ------------------------------------------------------------
	    	  int targetAmount = Integer.parseInt(args[0]);
	    	  FitnessFunction myFunc =  new  viajeFF(targetAmount);

	    	  conf.setFitnessFunction( myFunc );

	    	  // Now we need to tell the Configuration object how we want our
	    	  // Chromosomes to be setup. We do that by actually creating a
	    	  // sample Chromosome and then setting it on the Configuration
	    	  // object. As mentioned earlier, we want our Chromosomes to
	    	  // each have four genes, one for each of the coin types. We
	    	  // want the values of those genes to be integers, which represent
	    	  // how many coins of that type we have. We therefore use the
	    	  // IntegerGene class to represent each of the genes. That class
	    	  // also lets us specify a lower and upper bound, which we set
	    	  // to sensible values for each coin type.
	    	  // --------------------------------------------------------------
	    	  Gene[] sampleGenes = new Gene[ 4 ];
	    	  
	    	 

	    	  sampleGenes[0] = new IntegerGene(conf, 1, 6 );  // UBICACION
	    	  sampleGenes[1] = new IntegerGene(conf, 0, 7 );  // DISTANCIA A ESTACION CERCANA
	    	  sampleGenes[2] = new IntegerGene(conf, 0, 3 );  // ANTIGUEDAD
	    	  sampleGenes[3] = new IntegerGene(conf, 0, 7 );  // PRECIO ALQUILER
	    	  sampleGenes[4] = new IntegerGene(conf, 0, 4 );  // CANTIDAD AMBIENTES

	    	  Chromosome sampleChromosome = new Chromosome(conf, sampleGenes );

	    	  conf.setSampleChromosome( sampleChromosome );

	    	  // Finally, we need to tell the Configuration object how many
	    	  // Chromosomes we want in our population. The more Chromosomes,
	    	  // the larger the number of potential solutions (which is good
	    	  // for finding the answer), but the longer it will take to evolve
	    	  // the population each round. We'll set the population size to
	    	  // 500 here.
	    	  // --------------------------------------------------------------
	    	  conf.setPopulationSize( 500 );

	    	  
	    	  Genotype population = Genotype.randomInitialGenotype( conf );
	    	  
	    	  
	    	  
	    	  
	    	}

}
