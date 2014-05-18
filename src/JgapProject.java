/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.List;
import java.util.Random;

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

    public static int CANT_EVOLUCIONES = 10000;
    public static int POBLACION = 50;
    public static int SELECCION = 1;
    /*
    private static File file = new File("c:\\Users\\Usuario\\Documents\\NetBeansProjects\\JgapProject\\resultadoTPAG.txt");
    private static BufferedWriter buf;

    static {
        try {
            JgapProject.buf = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    /**
     * @param args
     * @throws InvalidConfigurationException 
     * @throws IOException 
     */
    public static void main(String[] args) throws InvalidConfigurationException, IOException {

        POBLACION = 21;
        CANT_EVOLUCIONES = 5;
        SELECCION = 2;
//		if (args.length == 2)
//                {
//			POBLACION = new Integer (args[0]);
//			CANT_EVOLUCIONES = new Integer (args[1]);
//		}
//                else
//                {
//			System.out.println("Ingrese los parametros: Poblacion y cantidad de evoluciones. ");
//			return;
//		}


        //Grupo 5 

        System.out.println("-----------------------------------------------------------");
        System.out.println("--   TP Algoritmos Geneticos Grupo 5                    --");
//		System.out.println("--   Numero buscado: " + resultadoBuscado + "                            --");
        System.out.println("--   Poblacion: " + POBLACION + "                                      --");
        System.out.println("--   Cantidad de Evoluciones: " + CANT_EVOLUCIONES + "                       --");
        System.out.println("--   Resultado total de ejecucion: c:\\resultadoTPAG.txt  --");
        System.out.println("-----------------------------------------------------------");

        
        
        
        
    	IChromosome masApto = AG();
    	
    	
        Gene[] g = masApto.getGenes();
        System.out.println("Aptitud: " + masApto.getFitnessValue());
        System.out.println("Barrio: " + getLocationString((Integer) g[0].getAllele()));
        System.out.println("Cercania al subte: " + getDistanceToSubwayString((Integer) g[1].getAllele()));
        System.out.println("Antiguedad: " + getYearsString((Integer) g[2].getAllele()));
        System.out.println("Precio: " + getPriceString((Integer) g[3].getAllele()));
        System.out.println("Cantidad de ambientes: " + getNumberOfRoomsString((Integer) g[4].getAllele()));

//		Float f = new Float(((ApartmentsFitness)ff).getValorCromosoma(masApto));
//		System.out.println("Mejor Valor obtenido: " + f.longValue());

        System.out.println();
        //imprimo toda la poblacion
//		imprimirPoblacion(poblacion);

//		buf.close();
    }
/*

    private static void imprimirPoblacion(Genotype poblacion) throws IOException {
        Population pop = poblacion.getPopulation();

        List<Chromosome> chromos = pop.determineFittestChromosomes(POBLACION + 2);
        buf.write("Poblacion: " + pop.size() + "\n");

        for (Chromosome cromosoma : chromos) {
            String item = getChromosomeValue(cromosoma);
            buf.write(item + "\n");
        }
        buf.write("\n\n");
    }
*/
    
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


//        conf.removeNaturalSelectors(true);
//        conf.removeNaturalSelectors(false);
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
            masApto = poblacion.getFittestChromosome();

            //Si el random es masyor a 0.5 mando una mutacion
            Random rn = new Random();
            if (rn.nextFloat()>0.5) {
                Population pop = poblacion.getPopulation();
                List<Chromosome> aMutar = pop.determineFittestChromosomes(POBLACION);
                mutador.operate(pop, aMutar);
            }
//            imprimirPoblacion(poblacion);

            imprimirPoblacionConsola(poblacion);
            //solo imprime los cambios
//			if(antfitnes != elMasMejor.getFitnessValue()){
//				Float dif = FactorsFitness.getValorCromosoma(elMasMejor) - resultadoBuscado;
//				if(dif < 0)
//					dif = dif * -1;
//
//				System.out.println("Fitnes Values " + elMasMejor.getFitnessValue() + " \t Dif: " + dif.longValue() +  " -- solucion parcial: " + FactorsFitness.getValorCromosoma(elMasMejor));
//				antfitnes = elMasMejor.getFitnessValue();
//			}
            //si encuentro el valor corto la busqueda
            double fitnessValue = masApto.getFitnessValue();
//            if(fitnessValue == 50.0)
//                        {
//				System.out.println("###############################################################################");
//				System.out.println("## R E S U L T A D O   E N C O N T R A D O !! cantidad de evoluciones: " + i);
//				System.out.println("###############################################################################");
//				break;
//			}
        }
        System.out.println("--------------||Cantidad de evoluciones: " + i);
        return masApto;
    }
    
    private static void imprimirPoblacionConsola(Genotype poblacion) {

        Population pop = poblacion.getPopulation();

        List<Chromosome> chromos = pop.determineFittestChromosomes(POBLACION + 2);
        System.out.println("poblacion: " + pop.size());

        for (Chromosome cromosoma : chromos) {
            String item = getChromosomeValue(cromosoma);
            System.out.print(item + " ");
            System.out.println();
        }
        System.out.println();

    }

    public static String getChromosomeValue(Chromosome cromosoma) {
        int barrio = ApartmentsFitness.obtenerValorGen(cromosoma, 0);
        int distancia = ApartmentsFitness.obtenerValorGen(cromosoma, 1);
        int antiguedad = ApartmentsFitness.obtenerValorGen(cromosoma, 2);
        int precio = ApartmentsFitness.obtenerValorGen(cromosoma, 3);
        int ambientes = ApartmentsFitness.obtenerValorGen(cromosoma, 4);
        double fitness = ApartmentsFitness.evaluar(cromosoma);

        String item = "Barrio: " + barrio + " Distancia al subte: " + distancia + " Antiguedad: " + antiguedad + " Precio: " + precio + " Ambientes: " + ambientes+ "||Aptitud:"+fitness;

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
