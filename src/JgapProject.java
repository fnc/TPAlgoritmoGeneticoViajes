/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.AveragingCrossoverOperator;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.ThresholdSelector;
import org.jgap.impl.WeightedRouletteSelector;

        
// * @author Usuario
// */
public class JgapProject {

    public static int CANT_EVOLUCIONES;
    public static int POBLACION;
    public static int SELECCION;
    
    public static BufferedWriter logDelAG;
    
    public static final String DATE_FORMAT_NOW = "dd-MM-yyyy";
    public static final String TIME_FORMAT_NOW = "HH-mm-ss";
    public static String pathLogFile;
    
    public static int generacionMejorInd;

    /**
     * @param args[0]Cantidad de evoluciones
     * @param args[1]Metodo de selección
     * @param args[0]Población inicial
     * @throws IOException 
     */
    public static void main(String[] args) throws InvalidConfigurationException, IOException {

        pathLogFile=System.getProperty("user.dir") + "\\Ejecucion TPAG - Grupo 5 - del dia " + dateNow() + " a las " + timeNow() + ".txt";
        
        boolean metodoDeSeleccionInvalido=false;
        
        if(args.length==3){
        	try{
        		CANT_EVOLUCIONES=Integer.parseInt(args[0]);

        		if (args[1].equals("ruleta")){
        			SELECCION = 1;
        		}
        		else if (args[1].equals("ranking")){
        			SELECCION = 2;
        		}
        		else if (args[1].equals("torneo")){
        			SELECCION = 3;
        		}
        		else{
        			metodoDeSeleccionInvalido=true;
        		}
        		
        		POBLACION=Integer.parseInt(args[2]);
        		
        	}catch(Exception e){
        		metodoDeSeleccionInvalido=true;
        	}
        }
        else{
        	metodoDeSeleccionInvalido=true;
        }
        if (metodoDeSeleccionInvalido){
        	System.out.println("Parámetros invalidos.\n El primer parámetro debe ser la cantidad de evoluciones a realizar.\n El segundo parámetro debe ser el método de selección a utilizar (Debe ser uno de los siguientes tres : ruleta, ranking, torneo).\n El tercer parámetro debe ser la cantidad de individuos de la población inicial.");
    		return;
        }
        
        logDelAG = crearLogger (pathLogFile);
        
        //Grupo 5 

        imprimirPorConsolaYALogfile(logDelAG, "-----------------------------------------------------------");
        imprimirPorConsolaYALogfile(logDelAG, "--   TP Algoritmos Geneticos Grupo 5                    --");
        imprimirPorConsolaYALogfile(logDelAG, "--   Poblacion: " + POBLACION + "                                      --");
        imprimirPorConsolaYALogfile(logDelAG, "--   Cantidad de Evoluciones: " + CANT_EVOLUCIONES + "                       --");
        imprimirPorConsolaYALogfile(logDelAG, "--   Resultado total de ejecucion: " + pathLogFile + "  --");
        imprimirPorConsolaYALogfile(logDelAG, "-----------------------------------------------------------\n");

        
    	IChromosome masApto = AG();
    	
    	
        Gene[] g = masApto.getGenes();
        
        imprimirPorConsolaYALogfile(logDelAG, "Generacion: " + String.valueOf(generacionMejorInd));
        imprimirPorConsolaYALogfile(logDelAG, "Aptitud: " + masApto.getFitnessValue());
        imprimirPorConsolaYALogfile(logDelAG, "Barrio: " + getLocationString((Integer) g[0].getAllele()));
        imprimirPorConsolaYALogfile(logDelAG, "Cercania al subte: " + getDistanceToSubwayString((Integer) g[1].getAllele()));
        imprimirPorConsolaYALogfile(logDelAG, "Antiguedad: " + getYearsString((Integer) g[2].getAllele()));
        imprimirPorConsolaYALogfile(logDelAG, "Precio: " + getPriceString((Integer) g[3].getAllele()));
        imprimirPorConsolaYALogfile(logDelAG, "Cantidad de ambientes: " + getNumberOfRoomsString((Integer) g[4].getAllele())+"\n");
        
        cerrarLog(logDelAG);

    }

    private static String dateNow() {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    	return sdf.format(cal.getTime());
    }

    private static String timeNow() {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_NOW);
    	return sdf.format(cal.getTime());
    }
    
    private static BufferedWriter crearLogger(String ruta){
    	BufferedWriter out = null;
    	try  
    	{
    	    FileWriter fstream = new FileWriter(ruta, true); //true tells to append data.
    	    out = new BufferedWriter(fstream);
    	}
    	catch (IOException e)
    	{
    	    System.err.println("Error: " + e.getMessage());
    	}
    	return out;
    }
    
    private static void imprimirEnLogFile(BufferedWriter logger, String textoALoggear){
    	try  
    	{
    		logger.write(textoALoggear);
    		logger.newLine();
    	}
    	catch (IOException e)
    	{
    	    System.err.println("Error: " + e.getMessage());
    	}
    }
    
    private static void imprimirPorConsolaYALogfile(BufferedWriter logger, String texto){
    	System.out.println(texto);
    	imprimirEnLogFile(logger, texto);
    }
    
    private static void cerrarLog(BufferedWriter logger){
    	try  
    	{
    		logger.close();
    	}
    	catch (IOException e)
    	{
    	    System.err.println("Error: " + e.getMessage());
    	}
    }
    
    private static IChromosome AG() throws InvalidConfigurationException{
    	Configuration conf = new DefaultConfiguration();

        FitnessFunction ff = new ApartmentsFitness();
        conf.setFitnessFunction(ff);

        /*
         * Cada gen representa una caracteristica a evaluar del depto:
         * 
         * 0 --> Barrio
         * 1 --> Cercania a linea de subte
         * 2 --> Antiguedad
         * 3 --> Precio
         * 4 --> Cantidad de ambientes
        
         * Cada caracteristica varia dentro de un rango posible en base al numero buscado
         */
        Gene[] genes = new Gene[5];
        genes[0] = new IntegerGene(conf, 2, 6); //  Barrio 
        genes[1] = new IntegerGene(conf, 2, 6); //  Cercania al subte
        genes[2] = new IntegerGene(conf, 0, 3); // Antiguedad
        genes[3] = new IntegerGene(conf, 1, 5); //  Precio
        genes[4] = new IntegerGene(conf, 0, 3); // Cantidad de ambientes


        Chromosome cromosoma = new Chromosome(conf, genes);

        conf.setSampleChromosome(cromosoma);
        conf.setPopulationSize(POBLACION);


        if(SELECCION == 1)//Seleccion por ruleta
        {
        	conf.addNaturalSelector(new WeightedRouletteSelector(conf), true);
        }
        else if (SELECCION == 2)//Elige los mejores - por ranking
        {
            conf.addNaturalSelector(new BestChromosomesSelector(conf), true);
        }
        else if (SELECCION == 3)//Seleccion por torneo
        {
            conf.addNaturalSelector(new ThresholdSelector(conf,1), true);
        }
        
        conf.addGeneticOperator(new AveragingCrossoverOperator(conf));

        Genotype poblacion = Genotype.randomInitialGenotype(conf);

        //imprimirPoblacion(poblacion);

        //Planteo la mutacion
        MutationOperator mutador = new MutationOperator(conf, POBLACION * 2);


        IChromosome masApto = null;
        int i = 0 ;
        for ( i = 0; i < CANT_EVOLUCIONES; i++) {

        	
        	
        	poblacion.evolve();
        	   

        	  //Si el random es masyor a 0.5 mando una mutacion
        	  Random rn = new Random();
        	  if (rn.nextFloat()>0.5) {
        	  Population pop = poblacion.getPopulation();
        	  List<Chromosome> aMutar = pop.determineFittestChromosomes(POBLACION);
        	  mutador.operate(pop, aMutar);
        	  }
        	// imprimirPoblacion(poblacion);

        	  
        	  imprimirPorConsolaYALogfile(logDelAG, "Generacion: " + String.valueOf(i+1));
        	  imprimirPoblacion(poblacion);
        	  
        	  IChromosome masAptoPob = poblacion.getFittestChromosome();
        	  double fitPob = masAptoPob.getFitnessValueDirectly();
        	  double fitTot=0;
        	  if(masApto != null){
        	  fitTot = masApto.getFitnessValueDirectly();
        	  }
        	  if(fitPob > fitTot){
        		  generacionMejorInd=i+1;
        		  masApto=masAptoPob;
        	  }
        	  
        }
        
        imprimirPorConsolaYALogfile(logDelAG, "\n--------------||Cantidad de evoluciones: " + String.valueOf(i));
        return masApto;
    }
    
    private static void imprimirPoblacion(Genotype poblacion) {

        Population pop = poblacion.getPopulation();

        List<Chromosome> chromos = pop.determineFittestChromosomes(POBLACION + 2);
        imprimirPorConsolaYALogfile(logDelAG, "poblacion: " + pop.size());

        for (Chromosome cromosoma : chromos) {
            String item = getChromosomeValue(cromosoma);
            
            imprimirPorConsolaYALogfile(logDelAG, item );
        }
        
        imprimirPorConsolaYALogfile(logDelAG, "");

    }

    public static String getChromosomeValue(Chromosome cromosoma) {
        int barrio = ApartmentsFitness.obtenerValorGen(cromosoma, 0);
        int distancia = ApartmentsFitness.obtenerValorGen(cromosoma, 1);
        int antiguedad = ApartmentsFitness.obtenerValorGen(cromosoma, 2);
        int precio = ApartmentsFitness.obtenerValorGen(cromosoma, 3);
        int ambientes = ApartmentsFitness.obtenerValorGen(cromosoma, 4);
        double fitness = ApartmentsFitness.evaluar(cromosoma);

        String item = "Barrio:" + barrio + " Distancia al subte:" + distancia + " Antiguedad:" + antiguedad + " Precio:" + precio + " Ambientes:" + ambientes+ "||Aptitud:"+fitness;

        return item;
    }

    public static String getLocationString(int locationId) {
        String location = "";

        switch (locationId) {

            case 2: {
                //Almagro
                location = "Almagro";
                break;
            }
            case 3: {
                //Balvanera
                location = "Balvanera";
                break;
            }
            case 4: {
                //Caballito
                location = "Caballito";
                break;
            }
            case 5: {
                //Belgrano
                location = "Belgrano";
                break;
            }
            case 6: {
                //Parque Patricios
                location = "Parque Patricios";
                break;
            }
            default: {
            	location = "INVALIDA";
                break;
            }
        }

        return location;
    }

    public static String getDistanceToSubwayString(int distanceId) {
        String distance = "";

        switch (distanceId) {

            case 2: {
                //Hasta 100m
                distance = "100m";
                break;
            }
            case 3: {
                //100-200m
                distance = "100-200m";
                break;
            }
            case 4: {
                //200-300m
                distance = "200-300m";
                break;
            }
            case 5: {
                //300-400m
                distance = "300-400m";
                break;
            }
            case 6: {
                //Mas de 400m
                distance = "Mas de 400m";
                break;
            }

            default: {
                //INVALIDO
            	distance = "INVALIDA";
                break;
            }
        }

        return distance;
    }

    public static String getYearsString(int yearId) {
        String distance = "";

        switch (yearId) {

            case 0: {
                //A estrenar
                distance = "A estrenar";
                break;
            }
            case 1: {
                //Hasta 10 anos
                distance = "10 anios";
                break;
            }
            case 3: {
                //hasta 20 anos
                distance = "20 anios";
                break;
            }
            case 2: {
                //30 anos o mas
                distance = "30 anios";
                break;
            }
            default: {
                //INVALIDO
            	distance = "INVALIDO";
                break;
            }
        }
        return distance;
    }

    public static String getPriceString(int priceId) {
        //La posicion 3 corresponde al precio del alquiler
        String priceString = "";

        switch (priceId) {


            case 1: {
                //Hasta 3000
                priceString = "hasta 3000 pesos";
                break;
            }
            case 2: {
                //3000 a 3500
                priceString = "3000 a 3500 pesos";
                break;
            }
            case 3: {
                //3500 a 4000
                priceString = "3500 a 4000 pesos";
                break;
            }
            case 4: {
                //4000 a 5500
                priceString = "4000 a 5500 pesos";
                break;
            }
            case 5: {
                //mayor a 5500
                priceString = "5500 pesos o mas";
                break;
            }
            default: {
                //INVALIDO
            	priceString = "INVALIDO";
                break;
            }
        }
        return priceString;
    }

    public static String getNumberOfRoomsString(int numberOfRoomsId) {
        //La posicion 4 corresponde a la cantidad de ambientes
        String numberOfRooms = "";

        switch (numberOfRoomsId) {

            case 0: {
                //1 ambiente
                numberOfRooms = "1 ambiente";
                break;
            }
            case 1: {
                //2 ambientes
                numberOfRooms = "2 ambientes";
                break;
            }
            case 2: {
                //3 ambientes
                numberOfRooms = "3 ambientes";
                break;
            }
            case 3: {
                //4 ambientes o mas
                numberOfRooms = "4 ambientes o mas";
                break;
            }
            default: {
                //INVALIDO
            	numberOfRooms = "INVALIDO";
                break;
            }
        }
        return numberOfRooms;
    }
}
