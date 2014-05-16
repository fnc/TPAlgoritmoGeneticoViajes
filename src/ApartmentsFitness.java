/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 *
 * @author Usuario
 */
public class ApartmentsFitness extends FitnessFunction
{
    	@Override
	protected double evaluate(IChromosome cromosoma) 
        {
            double result = 0;
            
            result += this.evaluarUbicacion(cromosoma);
            result += this.evaluarDistanciaEstacion(cromosoma);
            result += this.evaluarAntiguedad(cromosoma);
            result += this.evaluarPrecioAlquiler(cromosoma);
            result += this.evaluarCantidadAmbientes(cromosoma);
            result += this.evaluarCostoPorAmbiente(cromosoma);
            result += this.evaluarProbabilidadQueExista(cromosoma);
            if(result< 0){
            	result = 0;
            }
            return result;
	}
    public static double evaluar(IChromosome cromosoma) {            
        ApartmentsFitness af = new ApartmentsFitness();
        
    	double fitness = af.evaluate(cromosoma);
    	return fitness;
	}
        
        public int evaluarUbicacion( IChromosome unaMuestra )
	    {
	    	//La posicion 0 corresponde a la ubicacion
	        int ubicacion = obtenerValorGen( unaMuestra, 0 );
	        int puntos = 0;
	        
	        switch (ubicacion){

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

	        default:{
	        	//INVALIDA
	        	puntos = -40;
	        	break;
	        }
	        }
	        return puntos;
	    }
        
         public int evaluarDistanciaEstacion( IChromosome unaMuestra )
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
	        	//INVALIDA
	        	puntos = -40;
	        	break;
	        }
	        }
	        return puntos;
	    }

         public int evaluarAntiguedad( IChromosome unaMuestra )
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
	        	//Hasta 10 anos
	        	puntos += 7;
	        	break;
	        }
	        case 3:{
	        	//hasta 20 anos
	        	puntos += 4;
	        	break;
	        }
	        case 2:{
	        	//30 anos o mas
	        	puntos += 1;
	        	break;
	        }
	        default:{
	        	//INVALIDA
	        	puntos = -40;
	        	break;
	        }
	        }
	        return puntos;
	    }
         
         public int evaluarPrecioAlquiler( IChromosome unaMuestra )
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
	        	//INVALIDA
	        	puntos = -40;
	        	break;
	        }
	        }
	        return puntos;
	    }
         
         public int evaluarCostoPorAmbiente( IChromosome unaMuestra ){
 	    	//La posicion 4 corresponde a la cantidad de ambientes ( le sumo 1 para que nunca sea 0)
 	        int ambientes = this.evaluarCantidadAmbientes(unaMuestra);
 	       int precio = this.evaluarPrecioAlquiler(unaMuestra)+1;
 	        int puntos = (int)(10*(ambientes / precio));
        	if (precio<0&&ambientes<0){
        		puntos = puntos * (-1);
        	} 
        	 return puntos;
         }
         public int evaluarProbabilidadQueExista( IChromosome unaMuestra ){

 	       int precio = this.evaluarPrecioAlquiler(unaMuestra);
 	       int ubicacion = this.evaluarUbicacion(unaMuestra)+1;
	        int puntos = 0;
	        //si el precio es muy bueno y la ubicacion tambien suma menos puntos
	        puntos =(int)(10*(precio/ubicacion)); 
        	if (precio<0&&ubicacion<0){
        		puntos = puntos * (-1);
        	}  
	        
	        return puntos;
         }
         
          public int evaluarCantidadAmbientes( IChromosome unaMuestra )
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
	        	//INVALIDA
	        	puntos = -40;
	        	break;
	        }
	        }
	        return puntos;
	    }
          
            public static int obtenerValorGen(IChromosome unaMuestra, int posicion )
	    {
	        Integer valorGen = (Integer) unaMuestra.getGene(posicion).getAllele();

	        return valorGen.intValue();
	    }

}